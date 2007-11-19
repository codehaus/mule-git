/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.annotations;

import org.mule.config.annotations.EndpointBinding;
import org.mule.config.annotations.InboundEndpoint;
import org.mule.config.annotations.OutboundEndpoint;
import org.mule.config.annotations.Service;
import org.mule.config.annotations.converters.PropertiesConverter;
import org.mule.config.annotations.converters.TransformerConverter;
import org.mule.config.annotations.routing.Idempotent;
import org.mule.config.annotations.routing.WireTap;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.impl.registry.RegistryMapView;
import org.mule.routing.inbound.IdempotentReceiver;
import org.mule.routing.inbound.IdempotentSecureHashReceiver;
import org.mule.routing.nested.NestedRouter;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointBuilder;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.util.PropertiesUtils;
import org.mule.util.StringUtils;
import org.mule.util.TemplateParser;
import org.mule.util.object.AbstractObjectFactory;
import org.mule.util.object.SimpleObjectFactory;
import org.mule.util.object.SingletonObjectFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/** TODO */
public class AnnotatedServiceBuilder
{
    private Class aClass;
    private UMOManagementContext context;
    private String modelName;
    private Map properties;
    private TemplateParser parser = TemplateParser.createAntStyleParser();
    private RegistryMapView regProps;

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public Map getProperties()
    {
        return properties;
    }

    public void setProperties(Map properties)
    {
        this.properties = properties;
    }

    public AnnotatedServiceBuilder(Class aClass, UMOManagementContext context)
    {
        this.aClass = aClass;
        this.context = context;
        this.regProps = new RegistryMapView(context.getRegistry());
    }

    public UMOComponent createService() throws UMOException
    {
        if (!aClass.isAnnotationPresent(Service.class))
        {
            return null;
        }
        Service service = (Service) aClass.getAnnotation(Service.class);
        UMOComponent component = new SedaComponent();
        component.setName(getValue(service.name()));

        processServiceFactory(aClass, component, service);

        processInboundEndpoint(aClass, component);

        processInboundRouters(aClass, component);

        processOutboundEndpoint(aClass, component);

        processEndpointBindings(aClass, component);

        //check for Nested bindings

        return component;
    }

    private String getValue(String key)
    {
        String value = parser.parse(regProps, key);
        return value;
    }

    protected void processServiceFactory(Class aClass, UMOComponent component, Service service) throws UMOException
    {
        AbstractObjectFactory factory;
        if (service.singleton())
        {
            factory = new SingletonObjectFactory(aClass);
        }
        else
        {
            factory = new SimpleObjectFactory(aClass);
        }
        if (getProperties() != null)
        {
            factory.setProperties(getProperties());
        }
        factory.initialise();
        component.setServiceFactory(factory);

        if (getModelName() != null)
        {
            component.setModel(context.getRegistry().lookupModel(getModelName()));
        }

    }

    protected void processInboundEndpoint(Class aClass, UMOComponent descriptor) throws UMOException
    {
        if (aClass.isAnnotationPresent(InboundEndpoint.class))
        {
            InboundEndpoint inboundEp = (InboundEndpoint) aClass.getAnnotation(InboundEndpoint.class);
            EndpointData epData = new EndpointData(inboundEp);
            descriptor.getInboundRouter().addEndpoint(processEndpoint(epData));
        }
    }



    protected void processInboundRouters(Class aClass, UMOComponent descriptor) throws UMOException
    {
        if (aClass.isAnnotationPresent(WireTap.class))
        {
            WireTap wireTap = (WireTap) aClass.getAnnotation(WireTap.class);
            EndpointData epData = new EndpointData(wireTap);
            org.mule.routing.inbound.WireTap wireTapRouter  = new org.mule.routing.inbound.WireTap();
            wireTapRouter.setEndpoint(processEndpoint(epData));
            descriptor.getInboundRouter().addRouter(wireTapRouter);
        }

        if (aClass.isAnnotationPresent(Idempotent.class))
        {
            Idempotent router = (Idempotent) aClass.getAnnotation(Idempotent.class);
            if(router.type() == Idempotent.Type.ID)
            {
                descriptor.getInboundRouter().addRouter(new IdempotentReceiver());
            }
            else if(router.type() == Idempotent.Type.ID)
            {
                descriptor.getInboundRouter().addRouter(new IdempotentSecureHashReceiver());
            }
        }
    }


    protected void processOutboundEndpoint(Class aClass, UMOComponent descriptor) throws UMOException
    {
        if (aClass.isAnnotationPresent(OutboundEndpoint.class))
        {
            OutboundEndpoint outboundEp = (OutboundEndpoint) aClass.getAnnotation(OutboundEndpoint.class);

            EndpointData epData = new EndpointData(outboundEp);
            OutboundPassThroughRouter router = new OutboundPassThroughRouter();
            router.addEndpoint(processEndpoint(epData));
            UMOImmutableEndpoint ep = processEndpoint(epData);
            descriptor.getOutboundRouter().addRouter(router);
        }
    }

    protected void processEndpointBindings(Class aClass, UMOComponent descriptor) throws UMOException
    {
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            if (field.isAnnotationPresent(EndpointBinding.class))
            {
                EndpointBinding binding = field.getAnnotation(EndpointBinding.class);
                EndpointData epData = new EndpointData(binding);

                NestedRouter router = new NestedRouter();
                router.setInterface(field.getType());
                router.setEndpoint(processEndpoint(epData));
                if (!StringUtils.isBlank(binding.interfaceMethod()))
                {
                    router.setMethod(getValue(binding.interfaceMethod()));
                }
                descriptor.getNestedRouter().addRouter(router);
            }
        }
    }

    protected UMOImmutableEndpoint processEndpoint(EndpointData epData) throws UMOException
    {
        UMOImmutableEndpoint endpoint = context.getRegistry()
                    .lookupEndpoint(getValue(epData.getEndpoint()), context);

            if(endpoint == null)
            {
                UMOEndpointBuilder endpointBuilder = context.getRegistry().lookupEndpointFactory()
                .getEndpointBuilder(getValue(epData.getEndpoint()), context);
                endpointBuilder.setManagementContext(context);
                PropertiesConverter propsConverter = new PropertiesConverter();
                if(epData.getProperties() != null)
                {
                    Properties props = propsConverter.convert(getValue(epData.getProperties()), ",");
                    endpointBuilder.setProperties(props);
                }

                TransformerConverter transConverter = new TransformerConverter();
                if(epData.getTransformers() != null)
                {
                    List trans = transConverter.convert(getValue(epData.getTransformers()), ",", context.getRegistry());
                    endpointBuilder.setTransformers(trans);
                }

                if(epData.getResponseTransformers() != null)
                {
                    List trans = transConverter.convert(getValue(epData.getResponseTransformers()), ",", context.getRegistry());
                    endpointBuilder.setResponseTransformers(trans);
                }

                if(epData.getEncoding() != null)
                {
                    endpointBuilder.setEncoding(getValue(epData.getEncoding()));
                }

                if(epData.getConnectorName() != null)
                {
                    endpointBuilder.setConnector(context.getRegistry().lookupConnector(getValue(epData.getConnectorName())));
                }
                endpointBuilder.setSynchronous(epData.isSynchronous());
                endpointBuilder.setRemoteSync(epData.isRemoteSynchronous());

                if(epData.getType().equals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER))
                {
                    endpoint = endpointBuilder.buildInboundEndpoint();
                }
                else if(epData.getType().equals(UMOEndpoint.ENDPOINT_TYPE_SENDER))
                {
                    endpoint = endpointBuilder.buildOutboundEndpoint();
                }
                else if(epData.getType().equals(UMOEndpoint.ENDPOINT_TYPE_RESPONSE))
                {
                    endpoint = endpointBuilder.buildResponseEndpoint();
                }
                else
                {
                    throw new IllegalArgumentException("Endpooint type not recognised: " + epData.getType());
                }
            }
        return endpoint;
    }

    /**
     * Provides a generic endpoint data wrapper so that we can just use a single method for processing
     * endpoints and reduce a load of duplication
     */
    private class EndpointData
    {
        private boolean synchronous = false;
        private boolean remoteSynchronous = false;
        private String encoding;
        private String properties;
        private String transformers;
        private String responseTransformers;
        private String connectorName;
        private String endpoint;
        private String type;

        public EndpointData(InboundEndpoint ep)
        {
            synchronous = ep.synchronous();
            remoteSynchronous = ep.remoteSynchronous();
            encoding = (ep.encoding().equals(StringUtils.EMPTY) ? null : ep.encoding());
            endpoint = (ep.endpoint().equals(StringUtils.EMPTY) ? null : ep.endpoint());
            properties = (ep.properties().equals(StringUtils.EMPTY) ? null : ep.properties());
            transformers = (ep.transformers().equals(StringUtils.EMPTY) ? null : ep.transformers());
            responseTransformers = (ep.responseTransformers().equals(StringUtils.EMPTY) ? null : ep.responseTransformers());
            connectorName = (ep.connectorName().equals(StringUtils.EMPTY) ? null : ep.connectorName());
            type = UMOImmutableEndpoint.ENDPOINT_TYPE_RECEIVER;
        }

        public EndpointData(OutboundEndpoint ep)
        {
            synchronous = ep.synchronous();
            remoteSynchronous = ep.remoteSynchronous();
            encoding = (ep.encoding().equals(StringUtils.EMPTY) ? null : ep.encoding());
            endpoint = (ep.endpoint().equals(StringUtils.EMPTY) ? null : ep.endpoint());
            properties = (ep.properties().equals(StringUtils.EMPTY) ? null : ep.properties());
            transformers = (ep.transformers().equals(StringUtils.EMPTY) ? null : ep.transformers());
            connectorName = (ep.connectorName().equals(StringUtils.EMPTY) ? null : ep.connectorName());
            type = UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER;

        }

        public EndpointData(EndpointBinding ep)
        {
            synchronous = ep.synchronous();
            remoteSynchronous = ep.remoteSynchronous();
            encoding = (ep.encoding().equals(StringUtils.EMPTY) ? null : ep.encoding());
            endpoint = (ep.endpoint().equals(StringUtils.EMPTY) ? null : ep.endpoint());
            properties = (ep.properties().equals(StringUtils.EMPTY) ? null : ep.properties());
            transformers = (ep.transformers().equals(StringUtils.EMPTY) ? null : ep.transformers());
            connectorName = (ep.connectorName().equals(StringUtils.EMPTY) ? null : ep.connectorName());
            type = UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER;

        }

        public EndpointData(WireTap ep)
        {
            encoding = (ep.encoding().equals(StringUtils.EMPTY) ? null : ep.encoding());
            endpoint = (ep.endpoint().equals(StringUtils.EMPTY) ? null : ep.endpoint());
            properties = (ep.properties().equals(StringUtils.EMPTY) ? null : ep.properties());
            transformers = (ep.transformers().equals(StringUtils.EMPTY) ? null : ep.transformers());
            connectorName = (ep.connectorName().equals(StringUtils.EMPTY) ? null : ep.connectorName());
            type = UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER;
        }

        public String getConnectorName()
        {
            return connectorName;
        }

        public String getEncoding()
        {
            return encoding;
        }

        public String getEndpoint()
        {
            return endpoint;
        }

        public String getProperties()
        {
            return properties;
        }

        public boolean isRemoteSynchronous()
        {
            return remoteSynchronous;
        }

        public String getResponseTransformers()
        {
            return responseTransformers;
        }

        public boolean isSynchronous()
        {
            return synchronous;
        }

        public String getTransformers()
        {
            return transformers;
        }

        public String getType()
        {
            return type;
        }
    }
}

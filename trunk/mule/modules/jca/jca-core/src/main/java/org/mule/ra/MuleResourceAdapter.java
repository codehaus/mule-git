/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ra;

import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.impl.model.ModelFactory;
import org.mule.impl.model.ModelServiceNotFoundException;
import org.mule.providers.service.TransportFactory;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.model.UMOModel;
import org.mule.util.ClassUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>MuleResourceAdapter</code> TODO
 */
public class MuleResourceAdapter implements ResourceAdapter, Serializable
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 5727648958127416509L;

    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(this.getClass());

    private transient UMOManager manager;

    private transient BootstrapContext bootstrapContext;
    private MuleConnectionRequestInfo info = new MuleConnectionRequestInfo();
    private final Map endpoints = new HashMap();
    private String defaultJcaModelName;

    public MuleResourceAdapter()
    {
        MuleManager.getConfiguration().setModelType(JcaModel.JCA_MODEL_TYPE);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
        this.logger = LogFactory.getLog(this.getClass());
        this.manager = MuleManager.getInstance();
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#start(javax.resource.spi.BootstrapContext)
     */
    public void start(BootstrapContext bootstrapContext) throws ResourceAdapterInternalException
    {
        this.bootstrapContext = bootstrapContext;
        if (info.getConfigurations() != null)
        {
            if (MuleManager.isInstanciated())
            {
                throw new ResourceAdapterInternalException(
                    "A manager is already configured, cannot configure a new one using the configurations set on the Resource Adapter");
            }
            else
            {
                ConfigurationBuilder builder = null;
                try
                {
                    builder = (ConfigurationBuilder) ClassUtils.instanciateClass(info.getConfigurationBuilder(),
                        ClassUtils.NO_ARGS);

                }
                catch (Exception e)
                {
                    throw new ResourceAdapterInternalException("Failed to instanciate configurationBuilder class: "
                                                               + info.getConfigurationBuilder(), e);
                }

                try
                {
                    manager = builder.configure(info.getConfigurations(), null);
                }
                catch (ConfigurationException e)
                {
                    logger.error(e);
                    throw new ResourceAdapterInternalException("Failed to load configurations: "
                                                               + info.getConfigurations(), e);
                }
            }
        }
        manager = MuleManager.getInstance();
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#stop()
     */
    public void stop()
    {
        manager.dispose();
        manager = null;
        bootstrapContext = null;
    }

    /**
     * @return the bootstrap context for this adapter
     */
    public BootstrapContext getBootstrapContext()
    {
        return bootstrapContext;
    }

    /**
     * @see javax.resource.spi.ResourceAdapter#endpointActivation(javax.resource.spi.endpoint.MessageEndpointFactory,
     *      javax.resource.spi.ActivationSpec)
     */
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec activationSpec)
        throws ResourceException
    {
        if (activationSpec.getResourceAdapter() != this)
        {
            throw new ResourceException("ActivationSpec not initialized with this ResourceAdapter instance");
        }

        if (activationSpec.getClass().equals(MuleActivationSpec.class))
        {
            MuleActivationSpec muleActivationSpec = (MuleActivationSpec) activationSpec;
            try
            {
                UMOEndpointURI uri = new MuleEndpointURI(muleActivationSpec.getEndpoint());
                UMOEndpoint endpoint = TransportFactory.createEndpoint(uri, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);

                // Use asynchronous endpoint as we need to dispatch to component
                // rather than send.
                endpoint.setSynchronous(false);

                // TODO manage transactions
                MessageEndpoint messageEndpoint = null;
                messageEndpoint = endpointFactory.createEndpoint(null);

                // Resolve modelName
                // JCA specification mentions activationSpec properties inheriting
                // resourceAdaptor properites, but this doesn't seem to work, at
                // least with JBOSS, so do it manually.
                String modelName = muleActivationSpec.getModelName();
                if (modelName == null)
                {
                    modelName = defaultJcaModelName;
                }
                if (modelName == null)
                {
                    throw new ResourceException(
                        "The 'modelName' property has not been configured for either the MuleResourceAdaptor or MuleActicationSpec.");
                }

                // Lookup/create JCA Model
                UMOModel model = MuleManager.getInstance().lookupModel(modelName);
                if (model == null)
                {
                    model = createJcaModel(modelName);
                }
                if (!(model instanceof JcaModel))
                {
                    throw new ResourceException("Model:-" + muleActivationSpec.getModelName()
                                                + "  is not compatible with JCA type");
                }

                JcaModel jcaModel = (JcaModel) model;
                jcaModel.setWorkManager(new DelegateWorkManager(bootstrapContext.getWorkManager()));

                String name = "JcaComponent#" + messageEndpoint.hashCode();
                MuleDescriptor descriptor = new MuleDescriptor(name);
                descriptor.getInboundRouter().addEndpoint(endpoint);

                // Set endpointFactory rather than endpoint here, so we can obtain a
                // new endpoint instance from factory for each incoming message in
                // JcaComponet as reccomended by JCA specification
                descriptor.setImplementation(endpointFactory);
                descriptor.setModelName(modelName);

                model.registerComponent(descriptor);
                MuleEndpointKey key = new MuleEndpointKey(endpointFactory, muleActivationSpec);

                endpoints.put(key, descriptor);
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
        else
        {
            throw new NotSupportedException("That type of ActicationSpec not supported: " + activationSpec.getClass());
        }

    }

    /**
     * @see javax.resource.spi.ResourceAdapter#endpointDeactivation(javax.resource.spi.endpoint.MessageEndpointFactory,
     *      javax.resource.spi.ActivationSpec)
     */
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec activationSpec)
    {

        if (activationSpec.getClass().equals(MuleActivationSpec.class))
        {
            MuleEndpointKey key = new MuleEndpointKey(endpointFactory, (MuleActivationSpec) activationSpec);
            UMODescriptor descriptor = (UMODescriptor) endpoints.get(key);
            if (descriptor == null)
            {
                logger.warn("No endpoint was registered with key: " + key);
                return;
            }
            try
            {
                UMOModel model = MuleManager.getInstance().lookupModel(
                    ((MuleActivationSpec) activationSpec).getModelName());
                model.unregisterComponent(descriptor);
            }
            catch (UMOException e)
            {
                logger.error(e.getMessage(), e);
            }

        }

    }

    /**
     * We only connect to one resource manager per ResourceAdapter instance, so any
     * ActivationSpec will return the same XAResource.
     * 
     * @see javax.resource.spi.ResourceAdapter#getXAResources(javax.resource.spi.ActivationSpec[])
     */
    public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException
    {
        return new XAResource[]{};
    }

    /**
     * @return
     */
    public String getPassword()
    {
        return info.getPassword();
    }

    /**
     * @return
     */
    public String getConfigurations()
    {
        return info.getConfigurations();
    }

    /**
     * @return
     */
    public String getUserName()
    {
        return info.getUserName();
    }

    /**
     * @param password
     */
    public void setPassword(String password)
    {
        info.setPassword(password);
    }

    /**
     * @param configurations
     */
    public void setConfigurations(String configurations)
    {
        info.setConfigurations(configurations);
    }

    /**
     * @param userid
     */
    public void setUserName(String userid)
    {
        info.setUserName(userid);
    }

    public String getConfigurationBuilder()
    {
        return info.getConfigurationBuilder();
    }

    public void setConfigurationBuilder(String configbuilder)
    {
        info.setConfigurationBuilder(configbuilder);
    }

    /**
     * @return Returns the info.
     */
    public MuleConnectionRequestInfo getInfo()
    {
        return info;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MuleResourceAdapter))
        {
            return false;
        }

        final MuleResourceAdapter muleResourceAdapter = (MuleResourceAdapter) o;

        if (info != null ? !info.equals(muleResourceAdapter.info) : muleResourceAdapter.info != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return (info != null ? info.hashCode() : 0);
    }

    private UMOModel createJcaModel(String modelName) throws ModelServiceNotFoundException, UMOException
    {
        JcaModel model = (JcaModel) ModelFactory.createModel(JcaModel.JCA_MODEL_TYPE);
        model.setName(modelName);
        manager.registerModel(model);
        return model;
    }

    public String getModelName()
    {
        return defaultJcaModelName;
    }

    public void setModelName(String modelName)
    {
        this.defaultJcaModelName = modelName;
    }

}

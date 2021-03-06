/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.endpoint;

import org.mule.api.transport.Connector;
import org.mule.config.annotations.endpoints.ChannelType;
import org.mule.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Provides a generic endpoint data wrapper so that we can just use a single method for processing
 * endpoints and reduce a load of duplication
 */
public class AnnotatedEndpointData
{
    private boolean synchronous = false;
    private String encoding;
    private Map properties = new HashMap();
    private String connectorName;
    private String transformers;
    private String address;
    private String name;
    private String filter;
    private String correlationExpression;
    private Connector connector;
    private MEP mep;
    private ChannelType type;

    public AnnotatedEndpointData(MEP mep)
    {
        this.mep = mep;
        this.type = (mep == MEP.InOnly || mep == MEP.InOptionalOut || mep == MEP.InOut ? ChannelType.Inbound : ChannelType.Outbound);
        if (this.type == ChannelType.Inbound)
        {
            synchronous = !mep.equals(MEP.InOnly);
        }
        else
        {
            synchronous = !mep.equals(MEP.OutOnly);
        }
    }

    protected String emptyToNull(String value)
    {
        return (StringUtils.EMPTY.equals(value) ? null : value);
    }


    public String getConnectorName()
    {
        return connectorName;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public String getAddress()
    {
        return address;
    }

    public Map getProperties()
    {
        return properties;
    }


    public boolean isSynchronous()
    {
        return synchronous;
    }

    public ChannelType getType()
    {
        return type;
    }

    public String getFilter()
    {
        return filter;
    }

    public String getCorrelationExpression()
    {
        return correlationExpression;
    }

    public Connector getConnector()
    {
        return connector;
    }

    public void setConnector(Connector connector)
    {
        this.connector = connector;
    }

    public String getTransformers()
    {
        return transformers;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = emptyToNull(name);
    }

    public void setEncoding(String encoding)
    {
        this.encoding = emptyToNull(encoding);
    }

    public void setProperties(Map properties)
    {
        if (properties == null)
        {
            return;
        }

        this.properties = properties;
        //Special handling of "Mule" endpoint properties
        if (properties != null)
        {
            if (properties.containsKey("connectorName"))
            {
                setConnectorName((String) properties.remove("connectorName"));
            }
        }
    }

    public void setConnectorName(String connectorName)
    {
        this.connectorName = emptyToNull(connectorName);
    }

    public void setTransformers(String transformers)
    {
        this.transformers = emptyToNull(transformers);
    }

    public void setAddress(String address)
    {
        this.address = emptyToNull(address);
    }

    public void setFilter(String filter)
    {
        this.filter = emptyToNull(filter);
    }

    public void setCorrelationExpression(String correlationExpression)
    {
        this.correlationExpression = emptyToNull(correlationExpression);
    }

    public MEP getMep()
    {
        return mep;
    }

    public void setMEPUsingMethod(Method method, boolean inbound)
    {
        if (method.getReturnType().equals(Void.TYPE))
        {
            mep = (inbound ? MEP.InOnly : MEP.OutOnly);
        }
        else
        {
            mep = (inbound ? MEP.InOut : MEP.OutIn);
        }

    }

    public static Map convert(String[] properties)
    {
        if (properties.length > 0)
        {
            Properties props = new Properties();
            for (int i = 0; i < properties.length; i++)
            {
                String property = properties[i];
                if (property.length() == 0)
                {
                    continue;
                }
                int x = property.indexOf("=");
                if (x < 1)
                {
                    throw new IllegalArgumentException("Property string is malformed: " + property);
                }
                String value = property.substring(x + 1);
                property = property.substring(0, x);
                props.setProperty(property, value);

            }
            return props;
        }
        return null;
    }
}
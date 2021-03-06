/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.rmi;

import org.mule.DefaultMuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractPollingMessageReceiver;
import org.mule.transport.ConnectException;
import org.mule.transport.rmi.i18n.RmiMessages;
import org.mule.util.ClassUtils;

import java.lang.reflect.Method;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.util.List;

import org.apache.commons.collections.MapUtils;

/**
 * Will repeatedly call a method on a Remote object. If the method takes parameters A
 * List of objects can be specified on the endpoint called
 * <code>methodArgumentTypes</code>, If this property is ommitted it is assumed
 * that the method takes no parameters
 */

public class RmiMessageReceiver extends AbstractPollingMessageReceiver
{
    protected RmiConnector connector;

    protected Remote remoteObject;

    protected Method invokeMethod;

    protected Object[] methodArguments = null;

    public RmiMessageReceiver(Connector connector,
                              Service service,
                              InboundEndpoint endpoint,
                              long frequency) throws CreateException
    {
        super(connector, service, endpoint);
        this.setFrequency(frequency);
        this.connector = (RmiConnector) connector;
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        System.setProperty("java.security.policy", connector.getSecurityPolicy());

        // Set security manager
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }

        String methodName = MapUtils.getString(endpoint.getEndpointURI().getParams(),
                MuleProperties.MULE_METHOD_PROPERTY, null);

        if (null == methodName)
        {
            methodName = (String) endpoint.getProperty(MuleProperties.MULE_METHOD_PROPERTY);

            if (null == methodName)
            {
                throw new ConnectException(RmiMessages.messageParamServiceMethodNotSet(), this);
            }
        }

        remoteObject = connector.getRemoteObject(getEndpoint());

        List args = (List) endpoint.getProperty(RmiConnector.PROPERTY_SERVICE_METHOD_PARAMS_LIST);

        Class[] argTypes = new Class[]{};

        if (args == null)
        {
            logger.info(RmiConnector.PROPERTY_SERVICE_METHOD_PARAMS_LIST
                    + " not set on endpoint, assuming method call has no arguments");
            methodArguments = ClassUtils.NO_ARGS;
        }
        else
        {
            argTypes = ClassUtils.getClassTypes(methodArguments);
            methodArguments = new Object[args.size()];
            methodArguments = args.toArray(methodArguments);
        }
        invokeMethod = remoteObject.getClass().getMethod(methodName, argTypes);
    }

    protected void doDisconnect()
    {
        invokeMethod = null;
        remoteObject = null;
    }

    public void poll()
    {
        try
        {
            Object result = invokeMethod.invoke(remoteObject, getMethodArguments());

            if (null != result)
            {
                final Object payload = connector.getMessageAdapter(result).getPayload();
                routeMessage(new DefaultMuleMessage(payload), endpoint.isSynchronous());
            }
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    /**
     * Returns the method arguments to use when invoking the method on the Remote
     * object. This method can be overloaded to enable dynamic method arguments
     *
     * @return
     */
    protected Object[] getMethodArguments()
    {
        return methodArguments;
    }
}

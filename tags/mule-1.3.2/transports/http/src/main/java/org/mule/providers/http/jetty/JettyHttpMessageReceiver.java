/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http.jetty;

import org.apache.commons.lang.StringUtils;
import org.mortbay.http.HttpContext;
import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.util.InetAddrPort;
import org.mule.MuleManager;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.config.ThreadingProfile;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.http.servlet.MuleRESTReceiverServlet;
import org.mule.providers.http.servlet.ServletConnector;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.provider.UMOConnector;

/**
 * <code>HttpMessageReceiver</code> is a simple http server that can be used to
 * listen for http requests on a particular port
 * 
 */
public class JettyHttpMessageReceiver extends AbstractMessageReceiver
{
    public static final String JETTY_SERVLET_CONNECTOR_NAME= "_jettyConnector";

    private Server httpServer;

    public JettyHttpMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
        throws InitialisationException
    {
        super(connector, component, endpoint);

        if ("rest".equals(endpoint.getEndpointURI().getScheme()))
        {
            //We need to a Servlet Connecotor pointing to our servlet so the Servlets can
            //find the listeners for incoming requests
            ServletConnector scon = (ServletConnector) MuleManager.getInstance().lookupConnector(JETTY_SERVLET_CONNECTOR_NAME);
            if(scon!=null) {
                throw new InitialisationException(new Message("http", 10), this);
            }

            scon = new ServletConnector();
            scon.setName(JETTY_SERVLET_CONNECTOR_NAME);
            scon.setServletUrl(endpoint.getEndpointURI().getAddress());
            try
            {
                MuleManager.getInstance().registerConnector(scon);
                String path = endpoint.getEndpointURI().getPath();
                if (StringUtils.isEmpty(path))
                {
                    path = "/";
                }

                UMOEndpoint ep = new MuleEndpoint("servlet://" + path.substring(1), true);
                scon.registerListener(component, ep);
            }
            catch (Exception e)
            {
                throw new InitialisationException(e, this);
            }
        }

    }

    public void doConnect() throws Exception
    {
        httpServer = new Server();
        SocketListener socketListener = new SocketListener(new InetAddrPort(endpoint.getEndpointURI()
            .getPort()));

        // apply Threading settings
        ThreadingProfile tp = connector.getReceiverThreadingProfile();
        socketListener.setMaxIdleTimeMs((int)tp.getThreadTTL());
        socketListener.setMaxThreads(tp.getMaxThreadsActive());
        socketListener.setThreadsPriority(tp.getThreadPriority());

        httpServer.addListener(socketListener);

        String path = endpoint.getEndpointURI().getPath();
        if (StringUtils.isEmpty(path))
        {
            path = "/";
        }

        if (!path.endsWith("/"))
        {
            path += "/";
        }

        HttpContext context = httpServer.getContext("/");
        context.setRequestLog(null);

        ServletHandler handler = new ServletHandler();
        if ("rest".equals(endpoint.getEndpointURI().getScheme()))
        {
            handler.addServlet("MuleRESTReceiverServlet", path + "*", MuleRESTReceiverServlet.class.getName());
        }
        else
        {
            handler.addServlet("JettyReceiverServlet", path + "*", JettyReceiverServlet.class.getName());
        }


        context.addHandler(handler);
        context.setAttribute("messageReceiver", this);

    }

    public void doDisconnect() throws Exception
    {
        // stop is automativcally called by Mule
    }



    /**
     * Template method to dispose any resources associated with this receiver. There
     * is not need to dispose the connector as this is already done by the framework
     */
    protected void doDispose()
    {
        try
        {
            httpServer.stop(false);
        }
        catch (InterruptedException e)
        {
            logger.error("Error disposing Jetty recevier on: " + endpoint.getEndpointURI().toString(), e);
        }
    }

    public void doStart() throws UMOException
    {
        try
        {
            httpServer.start();
        }
        catch (Exception e)
        {
            throw new LifecycleException(new Message(Messages.FAILED_TO_START_X, "Jetty Http Receiver"), e,
                this);
        }
    }

    public void doStop() throws UMOException
    {
        try
        {
            httpServer.stop(true);
        }
        catch (InterruptedException e)
        {
            throw new LifecycleException(new Message(Messages.FAILED_TO_STOP_X, "Jetty Http Receiver"), e,
                this);
        }
    }

}

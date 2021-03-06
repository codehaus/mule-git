/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.management.mbeans;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.RecoverableException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

import java.beans.ExceptionListener;

/**
 * @author <a href="mailto:aperepel@itci.com">Andrew Perepelytsya</a>
 * 
 * $Id$
 */
public class ConnectorService implements ConnectorServiceMBean
{
    private UMOConnector connector;

    public ConnectorService(final UMOConnector connector)
    {
        this.connector = connector;
    }

    public boolean isStarted()
    {
        return connector.isStarted();
    }

    public boolean isDisposed()
    {
        return connector.isDisposed();
    }

    public boolean isDisposing()
    {
        return connector.isDisposing();
    }

    public String getName()
    {
        return connector.getName();
    }

    public String getProtocol()
    {
        return connector.getProtocol();
    }

    public ExceptionListener getExceptionListener()
    {
        return connector.getExceptionListener();
    }

    public UMOMessageDispatcherFactory getDispatcherFactory()
    {
        return connector.getDispatcherFactory();
    }

    public void startConnector()
            throws UMOException
    {
        connector.startConnector();
    }

    public void stopConnector()
            throws UMOException
    {
        connector.stopConnector();
    }

    public void dispose()
    {
        connector.dispose();
    }

    public void initialise()
            throws InitialisationException, RecoverableException
    {
        connector.initialise();
    }
}

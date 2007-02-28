/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.agents;

import org.mule.tck.AbstractMuleTestCase;

import java.net.InetAddress;
import java.net.Socket;

public class FixedHostRmiClienSocketFactoryTestCase extends AbstractMuleTestCase
{
    private static final int TEST_PORT = 60504;

    protected void doSetUp () throws Exception
    {
        super.doSetUp();
        // let's have a Mule admin agent to hit
        getManager(false).start();
    }

    public void testHostConstructorOverride() throws Exception
    {
        final String overrideHost = "127.0.0.1";
        final FixedHostRmiClientSocketFactory factory =
                new FixedHostRmiClientSocketFactory(overrideHost);

        assertEquals(overrideHost, factory.getOverrideHost());
        final Socket clientSocket = factory.createSocket("www.example.com", TEST_PORT);
        final InetAddress address = clientSocket.getInetAddress();
        final String socketHost = address.getHostAddress()  ;
        assertEquals(overrideHost, socketHost);
    }

    /**
     * Setter property may be used to dynamically switch the client socket host.
     */
    public void testHostSetterOverride() throws Exception
    {
        final String overrideHost = "127.0.0.1";
        final FixedHostRmiClientSocketFactory factory =
                new FixedHostRmiClientSocketFactory();
        factory.setOverrideHost(overrideHost);

        assertEquals(overrideHost, factory.getOverrideHost());
        final Socket clientSocket = factory.createSocket("www.example.com", TEST_PORT);
        final InetAddress address = clientSocket.getInetAddress();
        final String socketHost = address.getHostAddress()  ;
        assertEquals(overrideHost, socketHost);
    }

}

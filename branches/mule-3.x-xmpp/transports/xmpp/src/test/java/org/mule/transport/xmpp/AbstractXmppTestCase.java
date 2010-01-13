/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp;

import java.util.Properties;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public abstract class AbstractXmppTestCase extends XmppEnableDisableTestCase
{
    private static final long STARTUP_TIMEOUT = 5000;

    private CountDownLatch jabberLatch;
    protected JabberClient jabberClient;
    protected String recipient;

    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        
        jabberLatch = new CountDownLatch(1);
        createAndConnectJabberClient();
    }
    
    @Override
    protected final String getConfigResources()
    {
        return "xmpp-connector-config.xml," + getXmppConfigResources();
    }

    /**
     * Subclasses implmement this method and return the name of their config file.
     */
    protected abstract String getXmppConfigResources();

    private void createAndConnectJabberClient() throws Exception
    {
        // do not hardcode host/user etc here, look it up from the registry so the only place
        // that this info is stored is in the config
        Properties properties = (Properties) muleContext.getRegistry().lookupObject("properties");
        String host = properties.getProperty("host");
        recipient = properties.getProperty("recipient");
        String password = properties.getProperty("recipientPassword");
        
        jabberClient = new JabberClient(host, recipient, password);
        configureJabberClient(jabberClient);
        jabberClient.connect(jabberLatch);
        
        assertTrue(jabberLatch.await(STARTUP_TIMEOUT, TimeUnit.MILLISECONDS));
    }

    protected abstract void configureJabberClient(JabberClient client);

    @Override
    protected void doTearDown() throws Exception
    {
        jabberClient.disconnect();
        super.doTearDown();
    }
}

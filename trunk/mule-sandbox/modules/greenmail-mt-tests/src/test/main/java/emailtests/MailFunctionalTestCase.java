/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests;

import com.icegreen.greenmail.util.ServerSetupTest;
import com.icegreen.greenmail.util.Servers;

import org.mule.tck.FunctionalTestCase;

/**
 * This class takes care of the setUp and tearDown of the GreenMail server, so all
 * other tests will inherit from this class.
 */
public abstract class MailFunctionalTestCase extends FunctionalTestCase
{

    protected Servers servers;
    protected int messageCount = 50;

    protected void doPreFunctionalSetUp() throws Exception
    {
        servers = new Servers(ServerSetupTest.ALL);
        servers.getSmtp().setWorkerThreadCount(5);
        servers.getSmtps().setWorkerThreadCount(5);
        servers.start();
    }

    protected void doFunctionalTearDown() throws Exception
    {
        if (null != servers)
        {
            servers.stop();
        }
    }

}

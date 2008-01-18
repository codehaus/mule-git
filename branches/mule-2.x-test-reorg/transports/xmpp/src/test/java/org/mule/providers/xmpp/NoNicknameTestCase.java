/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.xmpp;

import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOManagementContext;

public class NoNicknameTestCase extends FunctionalTestCase
{

    private boolean errorDuringStartup = false;

    protected String getConfigResources()
    {
        return "no-nickname.xml";
    }

    protected UMOManagementContext createManagementContext() throws Exception
    {
        try
        {
            return super.createManagementContext();
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            assertTrue(e.getMessage().indexOf("Attribute nickname must be given if groupChat is true") > -1);
            errorDuringStartup = true;
            return null;
        }
    }

    public void testConfig()
    {
        assertTrue(errorDuringStartup);
    }

}
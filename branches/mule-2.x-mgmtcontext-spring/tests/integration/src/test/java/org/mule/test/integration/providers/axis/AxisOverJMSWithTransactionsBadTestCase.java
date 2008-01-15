/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.axis;

import org.mule.config.builders.DefaultsConfigurationBuilder;
import org.mule.impl.DefaultMuleContextFactory;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.MuleContext;
import org.mule.umo.MuleContextFactory;

/**
 * Check that we cannot name embedded transaction factory
 */
public class AxisOverJMSWithTransactionsBadTestCase extends FunctionalTestCase
{

    protected MuleContext createMuleContext() throws Exception
    {
        try
        {
            super.createMuleContext();
            throw new IllegalStateException("Expected config to fail");
        }
        catch (Exception e)
        {
            logger.debug(e);
            if (e.toString().indexOf("Attribute name is not allowed here") > -1)
            {
                MuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
                return muleContextFactory.createMuleContext(new DefaultsConfigurationBuilder());
            }
            else
            {
                throw new IllegalStateException("Incorrect error: " + e);
            }
        }
    }

    public void testConfig()
    {
        // trigger method above
    }

    protected String getConfigResources()
    {
        return "org/mule/test/integration/providers/axis/axis-over-jms-config-bad.xml";
    }

}

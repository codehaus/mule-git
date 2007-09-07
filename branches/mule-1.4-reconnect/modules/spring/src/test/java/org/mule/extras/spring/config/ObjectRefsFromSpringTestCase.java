/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import org.mule.MuleManager;
import org.mule.impl.retry.policies.SimpleRetryPolicyFactory;
import org.mule.providers.AbstractConnector;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.testmodels.mule.TestCompressionTransformer;
import org.mule.tck.testmodels.mule.TestExceptionStrategy;
import org.mule.umo.endpoint.UMOEndpoint;

public class ObjectRefsFromSpringTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "test-refs-from-spring.xml";
    }

    public void testObjectCreation() throws Exception
    {
        UMOEndpoint ep = MuleManager.getInstance().lookupEndpoint("foo");
        assertNotNull(ep);
        assertEquals("testConnector", ep.getConnector().getName());
        assertNotNull(((AbstractConnector)ep.getConnector()).getConnectionStrategy());
        assertTrue(((AbstractConnector)ep.getConnector()).getConnectionStrategy().getPolicyFactory() instanceof SimpleRetryPolicyFactory);
        assertTrue(ep.getConnector().getExceptionListener() instanceof TestExceptionStrategy);

        assertNotNull(ep.getTransformer());
        assertEquals("testTransformer", ep.getTransformer().getName());
        assertTrue(ep.getTransformer() instanceof TestCompressionTransformer);
        assertEquals(12, ((TestCompressionTransformer)ep.getTransformer()).getBeanProperty2());

    }
}

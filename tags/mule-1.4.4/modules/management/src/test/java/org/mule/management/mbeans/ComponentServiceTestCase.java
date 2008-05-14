/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import org.mule.impl.MuleDescriptor;
import org.mule.management.AbstractMuleJmxTestCase;
import org.mule.umo.manager.UMOManager;

import java.util.Set;

import javax.management.ObjectName;

public class ComponentServiceTestCase extends AbstractMuleJmxTestCase
{
    public void testUndeploy() throws Exception
    {
        final String domainOriginal = "TEST_DOMAIN_1";

        UMOManager manager = getManager(true);
        final MuleDescriptor descriptor = new MuleDescriptor("TEST_SERVICE");
        descriptor.setImplementation(new Object());
        getDefaultModel().registerComponent(descriptor);

        manager.start();
        final ComponentService service = new ComponentService("TEST_SERVICE");
        final ObjectName name = ObjectName.getInstance(domainOriginal + ":type=TEST_SERVICE");
        mBeanServer.registerMBean(service, name);
        Set mbeans = mBeanServer.queryMBeans(ObjectName.getInstance(domainOriginal + ":*"), null);

        // Expecting following mbeans to be registered:
        // 1) org.mule.management.mbeans.ComponentService@TEST_DOMAIN_1:type=TEST_SERVICE
        // 2) org.mule.management.mbeans.ComponentStats@TEST_DOMAIN_1:type=org.mule.Statistics,component=TEST_SERVICE
        // 3) org.mule.management.mbeans.RouterStats@TEST_DOMAIN_1:type=org.mule.Statistics,component=TEST_SERVICE,router=inbound
        // 4) org.mule.management.mbeans.RouterStats@TEST_DOMAIN_1:type=org.mule.Statistics,component=TEST_SERVICE,router=outbound
        assertEquals("Unexpected number of components registered in the domain.", 4, mbeans.size());

        mBeanServer.unregisterMBean(name);

        mbeans = mBeanServer.queryMBeans(ObjectName.getInstance(domainOriginal + ":*"), null);

        assertEquals("There should be no MBeans left in the domain", 0, mbeans.size());
    }
}

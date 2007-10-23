/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.queue;

import org.mule.MuleManager;
import org.mule.config.QueueProfile;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.impl.model.seda.SedaModel;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;

public class SedaComponentQueueConfigurationTestCase extends AbstractMuleTestCase
{

    public void testQueueConfiguration() throws Exception
    {
        TransactionalQueueManager qm = new TransactionalQueueManager();
        MuleManager.getInstance().setQueueManager(qm);

        QueueProfile qp = new QueueProfile();
        qp.setMaxOutstandingMessages(42);

        MuleDescriptor descriptor = MuleTestUtils.getTestDescriptor("test", "java.lang.Object");
        descriptor.setQueueProfile(qp);

        SedaComponent component = new SedaComponent(descriptor, new SedaModel());
        component.initialise();

        assertEquals(42, qm.getQueue("test.component").config.capacity);
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model.seda;

import org.mule.MuleRuntimeException;
import org.mule.impl.MuleDescriptor;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;

public class SedaComponentTestCase extends AbstractMuleTestCase // AbstractComponentTestCase
{
    // Cannot extend AbstractComponentTestCase because of inconsistent behaviour. See
    // MULE-2843

    // protected void doSetUp() throws Exception
    // {
    // MuleManager.getInstance().setQueueManager(new TransactionalQueueManager());
    //        
    // UMODescriptor descriptor = new MuleDescriptor("direct");
    // descriptor.setImplementation(new Object());
    // SedaModel sedaModel = new SedaModel();
    // sedaModel.setQueueProfile(new QueueProfile());
    // component = sedaModel.createComponent(descriptor);
    // }
    //
    // protected void doTearDown() throws Exception
    // {
    // component = null;
    // }

    public void testSpiWorkThrowableHandling() throws Exception
    {
        try
        {
            // getTestComponent() currently already returns a SedaComponent, but
            // here we are safe-guarding for any future changes
            MuleDescriptor descriptor = MuleTestUtils.getTestDescriptor("test", "java.lang.Object");
            SedaComponent component = new SedaComponent(descriptor, new SedaModel());

            component.handleWorkException(getTestWorkEvent(), "workRejected");
        }
        catch (MuleRuntimeException mrex)
        {
            assertNotNull(mrex);
            assertTrue(mrex.getCause().getClass() == Throwable.class);
            assertEquals("testThrowable", mrex.getCause().getMessage());
        }
    }

    private WorkEvent getTestWorkEvent()
    {
        return new WorkEvent(this, // source
            WorkEvent.WORK_REJECTED, getTestWork(), new WorkException(new Throwable("testThrowable")));
    }

    private Work getTestWork()
    {
        return new Work()
        {
            public void release()
            {
                // noop
            }

            public void run()
            {
                // noop
            }
        };
    }
}

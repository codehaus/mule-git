/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.config;

import org.mule.config.ThreadingProfile;
import org.mule.tck.AbstractMuleTestCase;

import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionHandler;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;

public class ThreadingProfileTestCase extends AbstractMuleTestCase
{

    public void testCustomRejectedExecutionHandler()
    {
        RejectedExecutionHandler handler = new RejectedExecutionHandler()
        {
            public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1)
            {
                // nothing to do here
            }
        };

        ThreadingProfile profile = new ThreadingProfile();
        profile.setRejectedExecutionHandler(handler);
        ThreadPoolExecutor pool = profile.createPool("testPool");
        assertSame(handler, pool.getRejectedExecutionHandler());
    }

}

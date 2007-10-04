/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util;

import org.mule.tck.AbstractMuleTestCase;

public class ExpiringSkipListMapTestCase extends AbstractMuleTestCase
{

    private ExpiringSkipListMap expiringMap;

    // @Override
    public void doTearDown()
    {
        // make sure we kill the expiry thread
        if (expiringMap != null)
        {
            expiringMap.getExpirer().stopExpiring();
        }
    }

    public void testTimedExpiry() throws InterruptedException
    {
        // a map entry lives for 3 seconds and we purge every 1 second
        expiringMap = new ExpiringSkipListMap(3, 1);
        expiringMap.getExpirer().startExpiringIfNotStarted();

        String key = "expiringKey";
        String value = "Hello ExpiringMap!";

        expiringMap.put(key, value);

        boolean foundValue = false;
        int loops = 0;

        do
        {
            foundValue = expiringMap.containsKey(key);

            if (foundValue)
            {
                try
                {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex)
                {
                    throw ex;
                }
            }

            loops++;
        }
        while (foundValue && loops < 10);

        assertFalse("value was not expired", foundValue);
        assertTrue(expiringMap.isEmpty());
    }

}

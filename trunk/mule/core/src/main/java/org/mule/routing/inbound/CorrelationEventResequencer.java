/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.inbound;

import org.mule.umo.UMOEvent;

import java.util.Comparator;

/**
 * <code>CorrelationEventResequencer</code> is used to resequence events according
 * to their dispatch sequence in the correlation group. When the MessageSplitter
 * router splits an event it assigns a correlation sequence to the individual message
 * parts so that another router such as the <i>CorrelationEventResequencer</i> can
 * receive the parts and reorder them or merge them.
 */
public class CorrelationEventResequencer extends AbstractEventResequencer
{

    public CorrelationEventResequencer()
    {
        super();
        super.setComparator(CorrelationSequenceComparator.instance);
    }

    protected boolean shouldResequence(EventGroup events)
    {
        UMOEvent event = (UMOEvent)events.iterator().next();

        if (event == null)
        {
            // nothing to resequence
            return false;
        }

        int size = event.getMessage().getCorrelationGroupSize();
        if (size == -1)
        {
            logger.warn("Correlation Group Size not set, but CorrelationResequencer is being used.  This can cause messages to be held indefinitely");
        }

        return size == events.size();
    }

    private static class CorrelationSequenceComparator implements Comparator
    {
        public static final Comparator instance = new CorrelationSequenceComparator();

        public int compare(Object o1, Object o2)
        {
            int val1 = ((UMOEvent)o1).getMessage().getCorrelationSequence();
            int val2 = ((UMOEvent)o2).getMessage().getCorrelationSequence();
            if (val1 == val2)
            {
                return 0;
            }
            else if (val1 > val2)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }

}

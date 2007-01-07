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

import org.mule.umo.MessagingException;
import org.mule.umo.UMOEvent;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentMap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <code>AbstractEventResequencer</code> is used to receive a set of events,
 * resequence them and forward them on to their destination
 */

public abstract class AbstractEventResequencer extends SelectiveConsumer
{
    protected static final String NO_CORRELATION_ID = "no-id";

    private volatile Comparator comparator;
    private final ConcurrentMap eventGroups = new ConcurrentHashMap();

    public AbstractEventResequencer()
    {
        super();
    }

    public void setComparator(Comparator eventComparator)
    {
        this.comparator = eventComparator;
    }

    public UMOEvent[] process(UMOEvent event) throws MessagingException
    {
        if (isMatch(event))
        {
            String cId = event.getMessage().getCorrelationId();

            if (cId == null)
            {
                cId = NO_CORRELATION_ID;
            }

            // first check for an existing group
            EventGroup eg = (EventGroup)eventGroups.get(cId);

            // does the EventGroup exist?
            if (eg == null)
            {
                // ..apprently not, so create a new one
                eg = new EventGroup(cId);

                // now add the new group to our collection, but also make sure no
                // other thread outran us
                EventGroup previous = (EventGroup)eventGroups.putIfAbsent(eg.getGroupId(), eg);
                if (previous != null)
                {
                    // apparently someone was faster, so swizzle the group ptr
                    eg = previous;
                }
            }

            /*
             * The following block needs to synchonize on the EventGroup because it
             * creates a dependency on the flow in shouldResequence/resequenceEvents.
             * Without this multiple threads might add events "at once", creating a
             * situation where a Sequencer that expects a group of exactly n events
             * gets to evaluate one that already contains n+1 (or more), "missing" or
             * "skipping" the superfluous ones by accident. We *could* make it part
             * of the API that EventGroups are allowed to "overflow" this way, OR
             * make sure that EventGroup never contains more events than the
             * expectedSize - which raises the question what it means when more
             * events than expected are arriving..
             */
            synchronized (eg)
            {
                // add the incoming event to whatever group we have
                eg.addEvent(event);

                if (shouldResequenceEvents(eg))
                {
                    eventGroups.remove(eg.getGroupId());
                    return resequenceEvents(eg);
                }
            }
        }
        return null;
    }

    protected UMOEvent[] resequenceEvents(EventGroup events)
    {
        UMOEvent[] result = events.toArray();

        if (comparator != null)
        {
            Arrays.sort(result, comparator);
        }
        else
        {
            logger.warn("Event comparator is null, events were not reordered");
        }

        return result;
    }

    protected abstract boolean shouldResequenceEvents(EventGroup events);

}

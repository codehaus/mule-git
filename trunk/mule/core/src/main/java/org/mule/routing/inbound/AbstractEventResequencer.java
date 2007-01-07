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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>AbstractEventResequencer</code> is used to receive a set of events,
 * resequence them and forward them on to their destination
 */

public abstract class AbstractEventResequencer extends SelectiveConsumer
{
    protected static final String NO_CORRELATION_ID = "no-id";

    private volatile Comparator comparator;
    private final Map eventGroups = new HashMap();

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
            EventGroup eg = addEvent(event);
            if (shouldResequence(eg))
            {
                removeGroup(eg.getGroupId());
                return resequenceEvents(eg);
            }
        }
        return null;
    }

    protected EventGroup addEvent(UMOEvent event)
    {
        String cId = event.getMessage().getCorrelationId();

        if (cId == null)
        {
            cId = NO_CORRELATION_ID;
        }

        // TODO HH: not atomic
        EventGroup eg = (EventGroup)eventGroups.get(cId);
        if (eg == null)
        {
            eg = new EventGroup(cId);
            eg.addEvent(event);
            eventGroups.put(eg.getGroupId(), eg);
        }
        else
        {
            eg.addEvent(event);
        }

        return eg;
    }

    protected void removeGroup(Object id)
    {
        eventGroups.remove(id);
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

    protected abstract boolean shouldResequence(EventGroup events);

}

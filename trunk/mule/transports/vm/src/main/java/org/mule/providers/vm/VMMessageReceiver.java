/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vm;

import org.mule.impl.MuleMessage;
import org.mule.providers.PollingReceiverWorker;
import org.mule.providers.TransactedPollingMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.queue.Queue;
import org.mule.util.queue.QueueSession;

import java.util.LinkedList;
import java.util.List;

/**
 * <code>VMMessageReceiver</code> is a listener for events from a Mule component which
 * then simply passes the events on to the target component.
 */
public class VMMessageReceiver extends TransactedPollingMessageReceiver
{

    private VMConnector connector;
    private final Object lock = new Object();

    public VMMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
        throws InitialisationException
    {
        super(connector, component, endpoint);
        this.setReceiveMessagesInTransaction(endpoint.getTransactionConfig().isTransacted());
        this.connector = (VMConnector) connector;
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        if (connector.isQueueEvents())
        {
            // Ensure we can create a vm queue
            QueueSession queueSession = connector.getQueueSession();
            Queue q = queueSession.getQueue(endpoint.getEndpointURI().getAddress());
            if (logger.isDebugEnabled())
            {
                logger.debug("Current queue depth for queue: " + endpoint.getEndpointURI().getAddress()
                             + " is: " + q.size());
            }
        }
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    public void onEvent(UMOEvent event) throws UMOException
    {
        /*
         * TODO HH: review: onEvent can only be called by the VMMessageDispatcher - why is
         * this lock here and do we still need it? what can break if this receiver is run
         * concurrently by multiple dispatchers?
         */
        UMOMessage msg = new MuleMessage(event.getTransformedMessage(), event.getMessage());
        synchronized (lock)
        {
            routeMessage(msg);
        }
    }

    public Object onCall(UMOEvent event) throws UMOException
    {
        UMOMessage msg = new MuleMessage(event.getTransformedMessage(), event.getMessage());
        return routeMessage(msg, event.isSynchronous());
    }

    protected List getMessages() throws Exception
    {
        // The queue from which to pull events
        QueueSession qs = connector.getQueueSession();
        Queue queue = qs.getQueue(endpoint.getEndpointURI().getAddress());

        // The list of retrieved messages that will be returned
        List messages = new LinkedList();

        /*
         * Determine how many messages to batch in this poll: we need drain the queue
         * quickly, but not by slamming the workManager too hard. It is impossible to
         * determine this more precisely without proper load statistics/feedback or some
         * kind of "event cost estimate". Therefore we just try to use half of the
         * receiver's workManager, since it is shared with receivers for other endpoints.
         */
        int maxThreads = connector.getReceiverThreadingProfile().getMaxThreadsActive();
        // also make sure batchSize is always at least 1
        int batchSize = Math.max(1, Math.min(queue.size(), ((maxThreads / 2) - 1)));

        // try to get the first event off the queue
        UMOEvent event = (UMOEvent) queue.poll(connector.getQueueTimeout());

        if (event != null)
        {
            // keep first dequeued event
            messages.add(event);

            // keep batching if more events are available
            for (int i = 0; i < batchSize && event != null; i++)
            {
                event = (UMOEvent) queue.poll(0);
                if (event != null)
                {
                    messages.add(event);
                }
            }
        }

        // let our workManager handle the batch of events
        return messages;
    }

    protected void processMessage(Object msg) throws Exception
    {
        // getMessages() returns UMOEvents
        UMOEvent event = (UMOEvent) msg;
        UMOMessage message = new MuleMessage(event.getTransformedMessage(), event.getMessage());
        routeMessage(message);
    }

    /*
     * We create our own "polling" worker here since we need to evade the standard scheduler.
     */
    // @Override
    protected PollingReceiverWorker createWork()
    {
        return new VMReceiverWorker(this);
    }

    /*
     * Even though the VM transport is "polling" for messages, the nonexistant cost of
     * accessing the queue is a good reason to not use the regular scheduling mechanism in
     * order to both minimize latency and maximize throughput.
     */
    protected static class VMReceiverWorker extends PollingReceiverWorker
    {

        public VMReceiverWorker(VMMessageReceiver pollingMessageReceiver)
        {
            super(pollingMessageReceiver);
        }

        public void run()
        {
            /*
             * We simply run our own polling loop all the time as long as the receiver is
             * started. The blocking wait defined by VMConnector.getQueueTimeout() will
             * prevent this worker's receiver thread from busy-waiting.
             */
            while (this.getReceiver().isConnected())
            {
                super.run();
            }
        }
    }

}

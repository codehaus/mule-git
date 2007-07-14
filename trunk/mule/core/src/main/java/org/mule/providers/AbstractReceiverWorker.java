/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers;

import org.mule.umo.TransactionException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOTransaction;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.transaction.TransactionTemplate;
import org.mule.transaction.TransactionCallback;
import org.mule.transaction.TransactionCoordination;
import org.mule.impl.MuleMessage;

import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.resource.spi.work.Work;

/**
 * TODO
 */
public abstract class AbstractReceiverWorker implements Work
{
    protected List messages;
    protected UMOImmutableEndpoint endpoint;
    protected AbstractMessageReceiver receiver;
    protected OutputStream out;

    public AbstractReceiverWorker(List messages, AbstractMessageReceiver receiver)
    {
        this(messages, receiver, null);
    }


    public AbstractReceiverWorker(List messages, AbstractMessageReceiver receiver, OutputStream out)
    {
        this.messages = messages;
        this.receiver = receiver;
        this.endpoint = receiver.getEndpoint();
        this.out = out;

    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Runnable#run()
    */
    public final void run()
    {
        doRun();
        release();
    }

    protected void doRun()
    {
        TransactionTemplate tt = new TransactionTemplate(endpoint.getTransactionConfig(),
                endpoint.getConnector().getExceptionListener());

        // Receive messages and process them in a single transaction
        // Do not enable threading here, but serveral workers
        // may have been started
        TransactionCallback cb = new TransactionCallback()
        {
            public Object doInTransaction() throws Exception
            {
                UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
                if(tx!=null)
                {
                    bindTransaction(tx);
                }
                List results = new ArrayList(messages.size());

                for (Iterator iterator = messages.iterator(); iterator.hasNext();)
                {
                    Object o = iterator.next();

                    o = preProcessMessage(o);
                    if(o!=null)
                    {
                        UMOMessageAdapter adapter;
                        if(o instanceof UMOMessageAdapter)
                        {
                            adapter = (UMOMessageAdapter)o;
                        }
                        else
                        {
                            adapter = endpoint.getConnector().getMessageAdapter(o);
                        }

                        UMOMessage result = receiver.routeMessage(new MuleMessage(adapter), tx,  endpoint.isSynchronous(), out);
                        o = postProcessMessage(result);
                        results.add(o);
                    }
                }
                return results;
            }
        };

        try
        {
            List results = (List)tt.execute(cb);
            handleResults(results);
        }
        catch (Exception e)
        {
            handleException(e);
        }
        finally
        {
            messages.clear();
        }
    }

    protected abstract void bindTransaction(UMOTransaction tx) throws TransactionException;

    protected void handleException(Exception e)
    {
        endpoint.getConnector().handleException(e);
    }

    protected void handleResults(List messages) throws Exception
    {
        //no op
    }

    protected Object preProcessMessage(Object message) throws Exception
    {
        //no op
        return message;
    }

    protected UMOMessage postProcessMessage(UMOMessage message) throws Exception
    {
        //no op
        return message;
    }


    public void release()
    {
        // no op
    }
}

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

import java.util.ArrayList;
import java.io.OutputStream;

/**
 * TODO
 */
public abstract class AbstractReceiverResourceWorker extends AbstractReceiverWorker
{
    protected Object resource;

    public AbstractReceiverResourceWorker(Object resource, AbstractMessageReceiver receiver)
    {
        this(new ArrayList(), receiver, null);
    }

    public AbstractReceiverResourceWorker(Object resource, AbstractMessageReceiver receiver, OutputStream out)
    {
        super(new ArrayList(), receiver, out);
        this.resource = resource;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Runnable#run()
    */
    //@Override
    public void doRun()
    {
        try
        {
            Object message = getNextMessage(resource);
            while (message != null)
            {
                messages.add(message);
                super.doRun();
                message = getNextMessage(resource);
            }

        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    protected abstract Object getNextMessage(Object resource) throws Exception;
}

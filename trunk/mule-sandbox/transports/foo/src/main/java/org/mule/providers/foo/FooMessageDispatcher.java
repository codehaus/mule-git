/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.providers.AbstractMessageDispatcher;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * <code>FooMessageDispatcher</code> Todo document
 *
 */

public class FooMessageDispatcher extends AbstractMessageDispatcher
{
    /**
     * logger used by this class
     */
    protected  transient Log logger = LogFactory.getLog(getClass());

    public FooMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
        //If you need a reference to the specific connector for this dispatcher
        //use:
        //FooConnector cnn = (FooConnector)endpoint.getConnector();
    }


     /**
     * Makes a connection to the underlying resource. Where connections are managed by the connector this
     * method may do nothing
     */
    public void doConnect() throws Exception
    {
        //If a resource for this Dispatcher needs a connection established, then this is the
        //place to do it
    }

    /**
     * Disconnects any conections made in the connect method
     */
    public void doDisconnect() throws Exception
    {
        //If the connect method did not do anything then this methos shouldn't do anything either
    }

    public void doDispatch(UMOEvent event) throws Exception
    {
    	//Todo Write the client code here to dispatch the event over this transport
        /* IMPLEMENTATION NOTE: 
        Is invoked when the endpoint is asynchronous and should invoke the transport but not 
        return any result. If a result is returned it should be ignorred and if they underlying 
        transport does have a notion of asynchronous processing, that should be invoked. This 
        method is executed in a different thread to the request thread.
        */
        throw new UnsupportedOperationException("doDispatch");
    }

    public UMOMessage doSend(UMOEvent event) throws Exception
    {
    	//Todo Write the client code here to send the event over this transport (or to dispatch the event to a store or repository)
    	
    	/* IMPLEMENTATION NOTE: 
        Should send the event payload over the transport. If there is a response from the transport 
        it shuold be returned from this method. The sendEvent method is called when the endpoint 
        is running synchronously and any response returned will ultimately be passed back to the 
        callee. This method is executed in the same thread as the request thread.
        */
        
        //Todo Once the event has been sent, return the result (if any) wrapped in a MuleMessage object
        throw new UnsupportedOperationException("doSend");
    }

    

    public UMOMessage doReceive(long timeout) throws Exception
    {
    	/* IMPLEMENTATION NOTE: 
        Can be used to make arbitary requests to a transport resource. if the timeout is 0 the 
        method should block until an event on the endpoint is received.
        */
        
        //Todo Write the client code here to perform a request over the transport
    	throw new UnsupportedOperationException("receive");
    }

    public void doDispose()
    {
    	//Optional; does not need to be implemented. Delete if not required
    	
        /* IMPLEMENTATION NOTE: 
        Is called when the Dispatcher is being disposed and should clean up any open resources.
        */
    }
}

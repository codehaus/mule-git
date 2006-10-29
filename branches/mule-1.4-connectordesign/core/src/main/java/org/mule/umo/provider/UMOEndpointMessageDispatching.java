/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.provider;

import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

/**
 * <code>UMOMessageDispatcher</code> is the interface responsible for distpatching
 * events to a particular transport. It implements the client code necessary to write
 * data to the underlying protocol. The dispatcher also exposes a receive method that
 * allows users to make specific calls to the underlying transport to receive an
 * event.
 */
public interface UMOEndpointMessageDispatching
{
    long RECEIVE_WAIT_INDEFINITELY = 0;
    long RECEIVE_NO_WAIT = -1;

    /**
     * Dispatches an event from the endpoint to the external system
     * 
     * @param event The event to dispatch
     * @throws DispatchException if the event fails to be dispatched
     */
    void dispatch(UMOImmutableEndpoint endpoint, UMOEvent event) throws DispatchException;

    /**
     * Sends an event from the endpoint to the external system
     * 
     * @param event The event to send
     * @return event the response form the external system wrapped in a UMOEvent
     * @throws DispatchException if the event fails to be dispatched
     */
    UMOMessage send(UMOImmutableEndpoint endpoint, UMOEvent event) throws DispatchException;

    /**
     * Make a specific request to the underlying transport
     * 
     * @param endpointUri the endpoint URI to use when connecting to the resource
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     * @deprecated Use receive(UMOImmutableEndpoint endpoint, long timeout)
     */
    UMOMessage receive(UMOEndpointURI endpointUri, long timeout) throws Exception;

    /**
     * Make a specific request to the underlying transport
     * 
     * @param endpoint the endpoint to use when connecting to the resource
     * @param timeout the maximum time the operation should block before returning.
     *            The call should return immediately if there is data available. If
     *            no data becomes available before the timeout elapses, null will be
     *            returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    UMOMessage receive(UMOImmutableEndpoint endpoint, long timeout) throws Exception;

}

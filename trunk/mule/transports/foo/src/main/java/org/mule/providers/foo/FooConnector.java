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

import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.umo.lifecycle.InitialisationException;

/**
 * <code>FooConnector</code> Todo document
 *
 */
public class FooConnector extends AbstractServiceEnabledConnector
{
    /* IMPLEMENTATION NOTE:
    All configuaration for the transport should be set on the Connector object, this is the object
    that gets configured in MuleXml
    */

    public void doInitialise() throws InitialisationException
    {
    	//remember to call super first
        super.doInitialise();

        //Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE:
        Is called once all bean properties have been set on the connector and can be used to
        validate and initialise the connectors state.
        */
    }

    /**
     * Makes a connection to the underlying resource. Where connections are managed at the receiver/dispatcher
     * level, this method may do nothing
     */
    public void doConnect() throws Exception
    {
        //If a resource for this connector needs a connection established, then this is the
        //place to do it
    }

    /**
     * Disconnects any conections made in the connect method
     */
    public void doDisconnect() throws Exception
    {
        //If the connect method did not do anything then this methos shouldn't do anything either
    }

    public void doStart() throws org.mule.umo.UMOException
    {
    	//Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE:
        If there is a single server instance or connection associated with the connector i.e.
        AxisServer or a Jms Connection or Jdbc Connection, this method should put the resource in
        a started state here.
        */
    }

    public void doStop() throws org.mule.umo.UMOException
    {
    	//Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE:
        Should put any associated resources into a stopped state. Mule will automatically call
        the stop() method.
        */
    }

    public void doDispose()
    {
    	//Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE:
        Should clean up any open resources associated with the connector.
        */
    }

    public String getProtocol()
    {
        return "foo";
    }


}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.rvd;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvCmListener;
import com.tibco.tibrv.TibrvCmTransport;
import com.tibco.tibrv.TibrvDispatcher;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;

/**
 * Receives regular and certified messages from rendezvous.  See RVDConnector
 * for more info
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: 1.3 $
 */
public class RVDReceiver extends AbstractMessageReceiver
    implements TibrvMsgCallback
{
    private TibrvListener listener = null;
    private TibrvCmListener cmListener = null;
    private TibrvCmTransport cmTransport = null;
    private TibrvDispatcher tibrvDispatcher = null;



    public RVDReceiver( UMOConnector connector, UMOComponent component,
                     UMOEndpoint endpoint ) throws InitialisationException
    {
        super(connector, component, endpoint);
        logger.debug( "receiver created" );
    }

    /** sets up the listener to and starts the dispatching thread */
    public void doConnect() throws Exception
    {
        TibrvTransport transport = ((RVDConnector)connector).transport;
        String subject = endpoint.getEndpointURI().getAddress();
        String cmname = endpoint.getEndpointURI().getParams().getProperty
            ( "cmname" );

        logger.info( "Connecting on subject: " + subject );
        if( cmname != null )
        {
            logger.debug( "Using Certified Messagiging with cmname: " + cmname);
            cmTransport = new TibrvCmTransport
                ( (TibrvRvdTransport)transport, cmname, true );
            cmListener = new TibrvCmListener( Tibrv.defaultQueue(), this,
                                              cmTransport, subject, null );
        }
        else
        {
        listener = new TibrvListener
            ( Tibrv.defaultQueue(), this, transport, subject, null );
        }

        tibrvDispatcher = new TibrvDispatcher( "Bus", Tibrv.defaultQueue());
    }

    /** Frees up the rendezvous resources */
    public void doDisconnect() throws Exception
    {
        if( listener != null )
            listener.destroy();
        if( cmListener != null )
            cmListener.destroy();
        if( cmTransport != null )
            cmTransport.destroy();
        tibrvDispatcher.destroy();
        listener = null;
        tibrvDispatcher = null;
        cmListener = null;
        cmTransport = null;
    }


    /** forwards the rendezvous messages to the umo */
    public void onMsg( TibrvListener rvListener, TibrvMsg msg )
    {
        try{
            logger.info( "message received on: " + rvListener.getSubject() );
            logger.debug( msg );

            UMOMessageAdapter adapter = connector.getMessageAdapter( msg );
            routeMessage( new MuleMessage( adapter ));
        }catch( Exception e ){ handleException( e );}
    }

}

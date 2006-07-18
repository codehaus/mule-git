// 	Receiver.java

// 	Ross Paul, ross.paul@mlb.com, 22 Jun 2006
// 	Time-stamp: <2006-07-17 16:22:49 rpaul>
package org.mule.providers.rvd;

import org.mule.providers.*;
import org.mule.impl.MuleMessage;
import org.mule.umo.provider.*;
import org.mule.umo.*;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.*;

import com.tibco.tibrv.*;

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

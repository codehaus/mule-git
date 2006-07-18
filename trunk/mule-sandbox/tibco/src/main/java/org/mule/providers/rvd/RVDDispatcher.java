package org.mule.providers.rvd;

import org.mule.providers.AbstractMessageDispatcher;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

import com.tibco.tibrv.TibrvCmTransport;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;


/**
 * Used for sending out transformed umo output over rendezvous.  By convention,
 * if the output is not a map, it will be keyed with RVDConnector.CONTENT_FIELD
 * (content).  See RVDConnector for info on how to use certified messaging
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: 1.4 $
 */
public class RVDDispatcher extends AbstractMessageDispatcher
{
    private TibrvCmTransport cmTransport = null;
    private RVDConnector connector;

    public RVDDispatcher(UMOImmutableEndpoint endpoint) {
        super(endpoint);
        this.connector = (RVDConnector) endpoint.getConnector();
    }

    /** Clear TibrvCmTransport */
    public void doDispose()
    {
        if( cmTransport != null )
        {
            cmTransport.destroy();
            cmTransport = null;
        }
    }

    protected void doConnect(UMOImmutableEndpoint endpoint) throws Exception {
        // TODO
    }

    protected void doDisconnect() throws Exception {
        // TODO
    }

    /** grabs the transformed message and sends it on its way */
    public void doDispatch( UMOEvent event ) throws Exception
    {
        try
        {
            //what we're sending and where to
            Object message = event.getTransformedMessage();
            String subject = event.getEndpoint().getEndpointURI().getAddress();
            String cmname = event.getEndpoint().getEndpointURI().getParams()
                .getProperty( "cmname" );

            TibrvMsg msg = null;
            if( !( message instanceof TibrvMsg ))
            {
                logger.warn( "Message isn't a TibrvMessage.  Converting it" );
                msg = new TibrvMsg();
                msg.add( RVDConnector.CONTENT_FIELD, message );
            }
            else
            {
                msg = (TibrvMsg)message;
            }

            msg.setSendSubject( subject );

            if( logger.isDebugEnabled() )
            {
                logger.debug( "Sending to: " + subject + " - " + message );
            }


            if( cmname == null )
                ((RVDConnector)connector).transport.send( msg );
            else
            {
                //fist send we'll need to set up the cmTransport
                if( cmTransport == null )
                {
                    logger.debug( "first message, initializing cmTransport" );
                    cmTransport = new TibrvCmTransport
                        ( (TibrvRvdTransport)((RVDConnector)connector)
                          .transport, cmname, true );
                }

                logger.debug( "sending certified, over: " + cmname );
                cmTransport.send( msg );
            }
        }catch( Exception e ){  getConnector().handleException( e ); }
    }

    public UMOMessage doSend( UMOEvent event ) throws Exception
    {
        doDispatch( event );
        return event.getMessage();
    }

    protected UMOMessage doReceive(UMOImmutableEndpoint endpoint, long timeout) throws Exception {
        throw new UnsupportedOperationException("Receive not implemented until I figure out how it is used" );
    }

    public Object getDelegateSession() throws UMOException
    {
        return null;
    }
}

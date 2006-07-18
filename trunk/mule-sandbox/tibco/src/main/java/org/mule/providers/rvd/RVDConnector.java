// 	RVDConnector.java

// 	Ross Paul, ross.paul@mlb.com, 21 Jun 2006
// 	Time-stamp: <2006-07-17 16:28:38 rpaul>
package org.mule.providers.rvd;

import com.tibco.tibrv.*;
import org.mule.providers.*;
import org.mule.umo.*;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.config.i18n.*;


/**
 * Allows mule to communicate over rendevoooooos!  Endpoint may be specified 
 * with: rvd://subjectName.  Or to use certified messaging, specify endpoints
 * with: rvd://subjectName?cmname=yourCMName.
 * The connector is configured by specifiying the service, network, &&|| daemon
 * as properties
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: 1.3 $
 */
public class RVDConnector extends AbstractServiceEnabledConnector
{
    public static final String CONTENT_FIELD = "content";

    //tibco connectino params
    private String service;
    private String network;
    private String daemon;
    
    TibrvTransport transport = null;


    /** @return "rvd" */
    public String getProtocol()
    {
        return "rvd";
    }

    /** Connects to the rvd and initializes a transport */
    public void doStart() throws UMOException
    {
        try{
            if( transport == null )
            {
                Tibrv.open( Tibrv.IMPL_NATIVE );
                if( logger.isDebugEnabled() )
                {
                    logger.debug( "Service: " + service );
                    logger.debug( "Network: " + network );
                    logger.debug( "Daemon: " + daemon );
                }
                transport = new TibrvRvdTransport( service, network, daemon );
            }
        }catch( Exception e ){
            logger.error( e, e );
            throw new LifecycleException
                (new Message(Messages.FAILED_TO_START_X, "rvd Connection"), e);
        }
    }

    /** Destroys transport and closes tibrv xs*/
    public void doDispose()
    {
        super.doDispose();
        try{
            transport.destroy();
            transport = null;
            Tibrv.close();
        }catch( Exception e ){ logger.error( e, e ); }

    }

    //mmmmm.... beans.......
    public void setService( String service )
    {
        this.service = service;
    }

    public void setNetwork( String network )
    {
        this.network = network;
    }

    public void setDaemon( String daemon )
    {
        this.daemon = daemon;
    }

    public String getService()
    {
        return service;
    }

    public String getNetwork()
    {
        return network;
    } 

    public String getDaemon()
    {
        return daemon;
    }
}

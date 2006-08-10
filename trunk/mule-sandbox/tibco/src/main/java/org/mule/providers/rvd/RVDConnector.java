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

import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.LifecycleException;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;


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

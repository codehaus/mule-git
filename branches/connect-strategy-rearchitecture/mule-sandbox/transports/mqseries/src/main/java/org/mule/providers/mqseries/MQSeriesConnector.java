/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/MQSeriesConnector.java,v 1.4 2006/02/22 14:40:46 rossmason Exp $
* $Revision: 1.4 $
* $Date: 2006/02/22 14:40:46 $
* ------------------------------------------------------------------------------------------------------
* 
* Copyright (c) SymphonySoft Limited. All rights reserved.
* http://www.symphonysoft.com
* 
* The software in this package is published under the terms of the BSD
* style license a copy of which has been included with this distribution in
* the LICENSE.txt file. 
*
*/
package org.mule.providers.mqseries;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQTopicConnectionFactory;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.ConnectException;
import org.mule.providers.jms.JmsConnector;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.NamingException;
import java.net.InetAddress;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.4 $
 */
public class MQSeriesConnector extends JmsConnector {

    /**
     * The name of the QueueManager to use
     */
    protected String queueManager = null;

    /**
     * The hostname  of the QueueManager to use
     */
    protected String hostname;

    /**
     * The port of the QueueManager to use
     */
    protected int port = INT_VALUE_NOT_SET;

    /**
     * The temporary destination model to use when creating temporary
     * destinations from this connector
     */
    protected String temporaryModel;

    /**
     * MQSeries CCS Id
     */
    protected int cCSId = INT_VALUE_NOT_SET;

    /**
     * Whether to use a local binding or client/server tcp binding
     */
    protected int transportType = INT_VALUE_NOT_SET;

    /**
     * The name of the channel used to communicate with the QeueuMAnager
     */
    protected String channel;

    protected boolean propagateMQEvents = false;

    /**
     * The classname of a class that implements one or more of MQSecurityExit,
     * MQSendExit or MQReceiveExit.  The exit handler will regiester with the
     * Connection factory
     */
    protected String receiveExitHandler = null;
    protected String receiveExitHandlerInit = null;
    protected String sendExitHandler = null;
    protected String sendExitHandlerInit = null;
    protected String securityExitHandler = null;
    protected String securityExitHandlerInit = null;

    //MQ series uses JMSReplyTo when using remote Queue definitions, this does not work well with
    //Mule as mule tries to receive on these destinations, so you can disable Mule replyTo handling
    protected boolean disableReplyTo = false;


    public MQSeriesConnector() {
        super();
        registerSupportedProtocol("mqs");
        registerSupportedProtocol("jms");
    }

    /**
     * @return true if the protocol is supported by this connector.
     */
    public boolean supportsProtocol(String protocol) {
        return super.supportsProtocol(protocol) || getProtocol().equalsIgnoreCase(protocol);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol() {
        return "mqs";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.UMOConnector#create(java.util.HashMap)
     */
    public void doInitialise() throws InitialisationException {
        setJmsSupport(new MQSeriesJmsSupport(this, null, false, false));
        super.doInitialise();
        try {
            if(propagateMQEvents) {
                receiveExitHandler = DefaultExitHandler.class.getName();
                sendExitHandler = DefaultExitHandler.class.getName();
                securityExitHandler = DefaultExitHandler.class.getName();
            }
            if(transportType== INT_VALUE_NOT_SET) {
                if((hostname==null || InetAddress.getByName(hostname).isAnyLocalAddress())) {
                    transportType = JMSC.MQJMS_TP_BINDINGS_MQ;
                } else {
                    transportType = JMSC.MQJMS_TP_CLIENT_MQ_TCPIP;
                }
            }
        } catch (Exception e) {
            throw new InitialisationException(new Message(Messages.FAILED_TO_CREATE_X, "Jms Connector"), e, this);
        }
    }

    public void doConnect() throws ConnectException {
        try {
            createConnectionFactory();
        } catch (Exception e) {
            throw new ConnectException(e, this);
        }
        super.doConnect();
    }

    protected ConnectionFactory createConnectionFactory() throws InitialisationException, NamingException {
        MQConnectionFactory factory = (MQConnectionFactory)getConnectionFactory();
        if(factory==null) {
            factory = new MQQueueConnectionFactory();
        }
        try {
            if(queueManager!=null) {
                factory.setQueueManager(queueManager);
            }
            if(hostname!=null) {
                factory.setHostName(hostname);
            }
            if(port > -1) {
                factory.setPort(port);
            }
            if(cCSId != -1) {
                factory.setCCSID(cCSId);
            }
            if(channel!=null) {
                factory.setChannel(channel);
            }

            if(temporaryModel!=null && factory instanceof MQQueueConnectionFactory) {
                ((MQQueueConnectionFactory)factory).setTemporaryModel(temporaryModel);
            }
            factory.setTransportType(transportType);
        } catch (JMSException e) {
            throw new InitialisationException(e, this);
        }

        if(getClientId()!=null) {
            factory.setClientId(getClientId());
        }

        if(receiveExitHandler!=null) {
            factory.setReceiveExit(receiveExitHandler);
        }
        if(receiveExitHandlerInit!=null) {
            factory.setReceiveExitInit(receiveExitHandlerInit);
        }

        if(sendExitHandler!=null) {
            factory.setSendExit(sendExitHandler);
        }

        if(sendExitHandlerInit!=null) {
            factory.setSendExitInit(sendExitHandlerInit);
        }

        if(securityExitHandler!=null) {
            factory.setSecurityExit(securityExitHandler);
        }
        if(receiveExitHandlerInit!=null) {
            factory.setSecurityExitInit(securityExitHandlerInit);
        }
        setConnectionFactory(factory);
        return factory;
    }

    public void initialiseFromUrl(UMOEndpointURI endpointUri) throws InitialisationException {
        //Initialising from the URI will cause the hostname, port, user info and queueManager name
        //to be set on this connector
        super.initialiseFromUrl(endpointUri);

        if(hostname!=null && (hostname.equals(queueManager) || hostname.equals(endpointUri.getAddress()))) {
            hostname=null;
        }
        //The MQSeries Java interface does not support XA
        if(getConnectionFactory()==null) {
            if (endpointUri.getResourceInfo()!=null && endpointUri.getResourceInfo().indexOf("topic") > -1) {
                setConnectionFactory(new MQTopicConnectionFactory());
            } else {
                setConnectionFactory(new MQQueueConnectionFactory());
            }
        }
    }

    public String getQueueManager() {
        return queueManager;
    }

    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTemporaryModel() {
        return temporaryModel;
    }

    public void setTemporaryModel(String temporaryModel) {
        this.temporaryModel = temporaryModel;
    }

    public int getcCSId() {
        return cCSId;
    }

    public void setcCSId(int cCSId) {
        this.cCSId = cCSId;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setReceiveExitHandler(String receiveExitHandler) {
        this.receiveExitHandler = receiveExitHandler;
    }

    public String getReceiveExitHandler() {
        return receiveExitHandler;
    }

    public String getReceiveExitHandlerInit() {
        return receiveExitHandlerInit;
    }

    public void setReceiveExitHandlerInit(String receiveExitHandlerInit) {
        this.receiveExitHandlerInit = receiveExitHandlerInit;
    }

    public String getSendExitHandler() {
        return sendExitHandler;
    }

    public void setSendExitHandler(String sendExitHandler) {
        this.sendExitHandler = sendExitHandler;
    }

    public String getSendExitHandlerInit() {
        return sendExitHandlerInit;
    }

    public void setSendExitHandlerInit(String sendExitHandlerInit) {
        this.sendExitHandlerInit = sendExitHandlerInit;
    }

    public String getSecurityExitHandler() {
        return securityExitHandler;
    }

    public void setSecurityExitHandler(String securityExitHandler) {
        this.securityExitHandler = securityExitHandler;
    }

    public String getSecurityExitHandlerInit() {
        return securityExitHandlerInit;
    }

    public void setSecurityExitHandlerInit(String securityExitHandlerInit) {
        this.securityExitHandlerInit = securityExitHandlerInit;
    }

    public boolean isPropagateMQEvents() {
        return propagateMQEvents;
    }

    public void setPropagateMQEvents(boolean propagateMQEvents) {
        this.propagateMQEvents = propagateMQEvents;
    }

    public boolean isDisableReplyTo() {
        return disableReplyTo;
    }

    public void setDisableReplyTo(boolean disableReplyTo) {
        this.disableReplyTo = disableReplyTo;
    }
}

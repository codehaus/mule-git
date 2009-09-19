/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp;

import org.mule.api.MuleException;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.transport.AbstractConnector;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * <code>XmppConnector</code> represents a connection to a Jabber server.
 */
public class XmppConnector extends AbstractConnector
{
    public static final String XMPP = "xmpp";
    public static final String XMPP_RESOURCE = "resource";
    public static final String XMPP_SUBJECT = "subject";
    public static final String XMPP_THREAD = "thread";
    public static final String XMPP_TO = "to";
    public static final String XMPP_FROM = "from";
    public static final String XMPP_GROUP_CHAT = "groupChat";
    public static final String XMPP_NICKNAME = "nickname";
    public static final String XMPP_RECIPIENT = "recipient";
    public static final String XMPP_TYPE = "type";
    
    public static final String CONVERSATION_TYPE_MESSAGE = "MESSAGE";
    public static final String CONVERSATION_TYPE_CHAT = "CHAT";
    public static final String CONVERSATION_TYPE_MULTI_USER_CHAT = "MULTI_USER_CHAT";

    private String host;
    private int port = 5222; // default jabber port
    private String user;
    private String password;
    private String resource;
    private boolean createAccount = false;
    
    private XMPPConnection connection;
    private XmppConversationFactory conversationFactory = new XmppConversationFactory();
    
    protected static String getRecipient(ImmutableEndpoint endpoint)
    {
        // the path begins with a '/'
        return endpoint.getEndpointURI().getPath().substring(1);
    }
    
    protected void doInitialise() throws InitialisationException
    {
        try
        {
            createXmppConnection();
        }
        catch (XMPPException ex)
        {
            throw new InitialisationException(ex, this);
        }
    }

    protected void doDispose()
    {
        connection = null;
    }

    protected void doConnect() throws Exception
    {
        connectToJabberServer();
    }

    protected void doDisconnect() throws Exception
    {
        if (connection.isConnected())
        {
            connection.disconnect();
        }
    }

    protected void doStart() throws MuleException
    {
        // template method
    }

    protected void doStop() throws MuleException
    {
        // template method
    }

    public String getProtocol()
    {
        return XMPP;
    }

    protected void createXmppConnection() throws XMPPException
    {
        ConnectionConfiguration connectionConfig = new ConnectionConfiguration(host, port);

        // no need to load the roster (this is not an interactive app)
        connectionConfig.setRosterLoadedAtLogin(false);

        connection = new XMPPConnection(connectionConfig);
    }

    protected void connectToJabberServer() throws XMPPException
    {
        connection.connect();
        
        if (createAccount)
        {
            createAccount();
        }
     
        if (resource != null)
        {
            connection.login(user, password, resource);
        }
        else
        {
            connection.login(user, password);
        }
    }

    private void createAccount()
    {
        try
        {
            AccountManager accountManager = new AccountManager(connection);
            accountManager.createAccount(user, password);
        }
        catch (XMPPException ex)
        {
            // User probably already exists, throw away...
            logger.warn("Account (" + user + ") already exists");
        }
    }

    @Deprecated
    // TODO xmpp: remove me
    public XMPPConnection createXmppConnection(EndpointURI endpointURI) throws XMPPException
    {
        logger.info("Trying to find XMPP connection for uri: " + endpointURI);

        String username = endpointURI.getUser();
        String hostname = endpointURI.getHost();
        String password = endpointURI.getPassword();
        String resource = (String)endpointURI.getParams().get("resource");

        XMPPConnection xmppConnection = this.doCreateXmppConnection(endpointURI);

        if (!xmppConnection.isAuthenticated())
        {
            // Make sure we have an account. If we don't, make one.
            try
            {
                AccountManager accManager = new AccountManager(xmppConnection);
                accManager.createAccount(username, password);
            }
            catch (XMPPException ex)
            {
                // User probably already exists, throw away...
                logger.info("*** account (" + username + ") already exists ***");
            }

            if (logger.isDebugEnabled())
            {
                logger.debug("Logging in as: " + username);
                logger.debug("pw is        : " + password);
                logger.debug("server       : " + hostname);
                logger.debug("resource     : " + resource);
            }

            if (resource == null)
            {
                xmppConnection.login(username, password);
            }
            else
            {
                xmppConnection.login(username, password, resource);
            }
        }
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Already authenticated on this connection, no need to log in again.");
            }
        }
        return xmppConnection;
    }

    /**
     * This method creates and returns the {@link XMPPConnection} object that's uses to talk to
     * the Jabber server.
     * <p/>
     * Subclasses can override this method to create a more specialized {@link XMPPConnection}.
     * 
     * @param endpointURI
     * @throws XMPPException
     */
    protected XMPPConnection doCreateXmppConnection(EndpointURI endpointURI) throws XMPPException
    {
        XMPPConnection xmppConnection = null;
        
        if (endpointURI.getPort() != -1)
        {
            ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
                endpointURI.getHost(), endpointURI.getPort());
            xmppConnection = new XMPPConnection(connectionConfig);
        }
        else
        {
            xmppConnection = new XMPPConnection(endpointURI.getHost());
        }
        
        return xmppConnection;
    }

    public boolean isResponseEnabled()
    {
        return true;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public boolean isCreateAccount()
    {
        return createAccount;
    }

    public void setCreateAccount(boolean createAccount)
    {
        this.createAccount = createAccount;
    }

    public XmppConversationFactory getConversationFactory()
    {
        return conversationFactory;
    }

    public void setConversationFactory(XmppConversationFactory conversationFactory)
    {
        this.conversationFactory = conversationFactory;
    }
    
    protected XMPPConnection getXmppConnection()
    {
        return connection;
    }
}

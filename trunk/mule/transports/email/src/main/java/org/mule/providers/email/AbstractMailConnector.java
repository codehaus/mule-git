/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractConnector;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.URLName;

import org.apache.commons.lang.StringUtils;

/**
 * Abstract superclass for mail connectors. Provides Mule with an Authenticator
 * object and other shared functionality like e.g. Session creation.
 */
public abstract class AbstractMailConnector extends AbstractConnector
{

    private int defaultPort;

    /**
     * A custom authenticator to be used on any mail sessions created with this
     * connector. This will only be used if user name credendials are set on the
     * endpoint.
     */
    private Authenticator authenticator = null;

    public AbstractMailConnector(int defaultPort)
    {
        super();
        this.defaultPort = defaultPort;
    }

    public int getDefaultPort()
    {
        return defaultPort;
    }

    public Authenticator getAuthenticator()
    {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator)
    {
        this.authenticator = authenticator;
    }

    /**
     * Creates a new javax.mail Session based on a URL. If a password is set on the
     * URL it also adds an SMTP authenticator.
     * 
     * @param args a javax.mail.URLName providing properties of the required Session
     *            (host, port etc.)
     */
    public Session getMailSession(URLName url)
    {
        if (url == null)
        {
            throw new IllegalArgumentException(new org.mule.config.i18n.Message(Messages.X_IS_NULL, "URL")
            .toString());
        }

        String protocol = getProtocol().toLowerCase();
        boolean secure = false;

        if (protocol.equals("smtps"))
        {
            protocol = "smtp";
            secure = true;
        }
        else if (protocol.equals("pop3s"))
        {
            protocol = "pop3";
            secure = true;
        }
        else if (protocol.equals("imaps"))
        {
            protocol = "imap";
            secure = true;
        }

        Properties props = System.getProperties();
        Session session;

        // make sure we do not mess with authentication set via system properties
        synchronized (props)
        {
            props.put("mail." + protocol + ".host", url.getHost());

            int port = url.getPort();
            if (port == -1)
            {
                port = this.getDefaultPort();
            }

            props.put("mail." + protocol + ".port", String.valueOf(port));

            if (secure)
            {
                System.setProperty("mail." + protocol + ".socketFactory.port", String.valueOf(port));
            }

            props.setProperty("mail." + protocol + ".rsetbeforequit", "true");

            if (StringUtils.isNotBlank(url.getPassword()))
            {
                props.put("mail." + protocol + ".auth", "true");
                Authenticator auth = this.getAuthenticator();
                if (auth == null)
                {
                    auth = new DefaultAuthenticator(url.getUsername(), url.getPassword());
                    logger.debug("No Authenticator set on connector: " + this.getName() + "; using default.");
                }

                session = Session.getInstance(props, auth);
            }
            else
            {
                // reset authentication property so smtp is not affected (MULE-464)
                props.put("mail." + protocol + ".auth", "false");
                session = Session.getInstance(props, null);
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Creating mail session: host = " + url.getHost() + ", port = " + url.getPort()
                + ", user = " + url.getUsername() + ", pass = " + url.getPassword());
        }

        return session;
    }

    // supply these here because sub-classes are very simple

    protected void doInitialise() throws InitialisationException
    {
        // template method, nothing to do
    }

    protected void doDispose()
    {
        // template method, nothing to do
    }

    protected void doConnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doDisconnect() throws Exception
    {
        // template method, nothing to do
    }

    protected void doStart() throws UMOException
    {
        // template method, nothing to do
    }

    protected void doStop() throws UMOException
    {
        // template method, nothing to do
    }

}

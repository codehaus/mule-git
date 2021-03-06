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

import org.mule.umo.lifecycle.InitialisationException;

/**
 * Creates a secure SMTP connection
 */
public class SmtpsConnector extends SmtpConnector
{

    public static final String DEFAULT_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private String socketFactory = DEFAULT_SOCKET_FACTORY;
    private String socketFactoryFallback = "false";
    private String trustStore = null;
    private String trustStorePassword = null;

    public static final int DEFAULT_SMTPS_PORT = 465;

    public SmtpsConnector() throws InitialisationException
    {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol()
    {
        return "smtps";
    }

    public int getDefaultPort()
    {
        return DEFAULT_SMTPS_PORT;
    }

    public void doInitialise() throws InitialisationException
    {
        super.doInitialise();
        System.setProperty("mail.smtps.ssl", "true");
        System.setProperty("mail.smtps.socketFactory.class", getSocketFactory());
        System.setProperty("mail.smtps.socketFactory.fallback", getSocketFactoryFallback());

        /*
         * These Properties need to be set, but if set on the System properties They
         * will ovverwrite SMTP properties, thus effectively only letting eiter SMTP
         * or SMTPs endpoints in 1 config. These Veriables will be set in the
         * MailUtils, createMailSession so they will only effrect the smtps Session.
         * System.setProperty("mail.smtp.ssl", "true");
         * System.setProperty("mail.smtp.socketFactory.class", getSocketFactory());
         * System.setProperty("mail.smtp.socketFactory.fallback",
         * getSocketFactoryFallback());
         */

        if (getTrustStore() != null)
        {
            System.setProperty("javax.net.ssl.trustStore", getTrustStore());
            if (getTrustStorePassword() != null)
            {
                System.setProperty("javax.net.ssl.trustStorePassword", getTrustStorePassword());
            }
        }
    }

    public String getSocketFactory()
    {
        return socketFactory;
    }

    public void setSocketFactory(String sslSocketFactory)
    {
        this.socketFactory = sslSocketFactory;
    }

    public String getSocketFactoryFallback()
    {
        return socketFactoryFallback;
    }

    public void setSocketFactoryFallback(String socketFactoryFallback)
    {
        this.socketFactoryFallback = socketFactoryFallback;
    }

    public String getTrustStore()
    {
        return trustStore;
    }

    public void setTrustStore(String trustStore)
    {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword()
    {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword)
    {
        this.trustStorePassword = trustStorePassword;
    }
}

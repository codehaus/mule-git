/*
 * $Id: SmtpConnector.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore.utils;

import java.util.Properties;

import javax.mail.URLName;

/**
 * This class just sets some extra SMTP properties so it works with GMail.
 */
public class SmtpConnector extends org.mule.transport.email.SmtpsConnector
{

    @Override
    protected void extendPropertiesForSession(Properties global, Properties local, URLName url) {
        super.extendPropertiesForSession(global, local, url);

        local.setProperty("mail.smtp.starttls.enable", "true");
        local.setProperty("mail.smtp.auth", "true");
        local.setProperty("mail.smtps.starttls.enable", "true");
        local.setProperty("mail.smtps.auth", "true");
    }
}

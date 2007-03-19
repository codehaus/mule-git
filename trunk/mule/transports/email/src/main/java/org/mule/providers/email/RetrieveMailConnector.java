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

import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOMessageReceiver;

/**
 * <code>Pop3Connector</code> is used to connect and receive mail from a POP3
 * mailbox.
 */
public abstract class RetrieveMailConnector extends AbstractMailConnector
{
    public static final String MAILBOX = "INBOX";
    public static final int DEFAULT_CHECK_FREQUENCY = 60000;

    /**
     * Holds the time in milliseconds that the endpoint should wait before checking a
     * mailbox
     */
    private volatile long checkFrequency = DEFAULT_CHECK_FREQUENCY;

    /**
     * holds a path where messages should be backed up to
     */
    private volatile String backupFolder = null;

    private String mailboxFolder = MAILBOX;

    /**
     * Once a message has been read, should it be deleted
     */
    private volatile boolean deleteReadMessages = true;

    protected RetrieveMailConnector(int defaultPort)
    {
        super(defaultPort);
    }

    /**
     * @return the milliseconds between checking the folder for messages
     */
    public long getCheckFrequency()
    {
        return checkFrequency;
    }

    /**
     * @param l
     */
    public void setCheckFrequency(long l)
    {
        if (l < 1)
        {
            l = DEFAULT_CHECK_FREQUENCY;
        }
        checkFrequency = l;
    }

    public String getMailboxFolder()
    {
        return mailboxFolder;
    }

    public void setMailboxFolder(String mailboxFolder)
    {
        this.mailboxFolder = mailboxFolder;
    }
    
    /**
     * @return a relative or absolute path to a directory on the file system
     */
    public String getBackupFolder()
    {
        return backupFolder;
    }

    /**
     * @param string
     */
    public void setBackupFolder(String string)
    {
        backupFolder = string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.providers.UMOConnector#registerListener(javax.jms.MessageListener,
     *      java.lang.String)
     */
    public UMOMessageReceiver createReceiver(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
        Object[] args = {new Long(checkFrequency), backupFolder};
        return serviceDescriptor.createMessageReceiver(this, component, endpoint, args);
    }

    public boolean isDeleteReadMessages()
    {
        return deleteReadMessages;
    }

    public void setDeleteReadMessages(boolean deleteReadMessages)
    {
        this.deleteReadMessages = deleteReadMessages;
    }
    
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests.messages;

import javax.activation.DataHandler;

public class EmailWithAttachmentMessage
{

    private String textMessage;
    private DataHandler[] attachments;

    public EmailWithAttachmentMessage()
    {
        super();
    }

    /**
     * @param textMessage
     * @param attachments
     */
    public EmailWithAttachmentMessage(String textMessage, DataHandler[] attachments)
    {
        super();
        this.textMessage = textMessage;
        this.attachments = attachments;
    }

    /**
     * @return the attachments
     */
    public DataHandler[] getAttachments()
    {
        return this.attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(DataHandler[] attachments)
    {
        this.attachments = attachments;
    }

    /**
     * @return the textMessage
     */
    public String getTextMessage()
    {
        return this.textMessage;
    }

    /**
     * @param textMessage the textMessage to set
     */
    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }

}


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

/**
 * 
 */
package emailtests.components;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import emailtests.messages.EmailWithAttachmentMessage;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 *
 */
public class EmailWithAttachment {
	
	public Object process (String message)
	{
		EmailWithAttachmentMessage emailMessage = new EmailWithAttachmentMessage();
		String[] strings=message.split(";");		
		emailMessage.setTextMessage(strings[0]);
		DataHandler[] attachments=new DataHandler[strings.length-1];
		
		for(int i=0;i<attachments.length;i++)
		{
			attachments[i]=new DataHandler(new FileDataSource("./src/test/resources/"+strings[i+1]));
		}
		emailMessage.setAttachments(attachments);
		return emailMessage;
	}
}

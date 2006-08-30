/**
 * 
 */
package emailtests;

import javax.mail.Message;

import org.mule.extras.client.MuleClient;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 * 
 * Test sending email with Smtp
 *
 */
public class SmtpTestCase extends MailFunctionalTestCase{

	public void testPlainMessage() throws Exception
	{
		MuleClient client=new MuleClient();
		Boolean[] received = new Boolean[messageCount];
        //Set 1
		for(int i=0;i<messageCount;i++)
		{
			client.dispatch("vm://smtpinbound","Message : "+i,null);
			received[i]=false;
		}
		
		//wait for max 5s for 1 email to arrive
	    assertTrue(servers.waitForIncomingEmail(500000, messageCount)); 
	    
	    //Retrieve using GreenMail API
	    Message[] messages = servers.getReceivedMessages();
	    //assertEquals(messageCount, messages.length);
	    for(int i=0;i<messageCount;i++)
	    {
	    	String message=servers.util().getBody(messages[i]).trim();
	    	int messageNumber=Integer.parseInt(message.substring(message.lastIndexOf(" ")+1));
	    	assertTrue(received[messageNumber]==false);
	    	received[messageNumber]=true;
	    }
	    for(int i=0;i<messageCount;i++)
	    {
	    	assertTrue(received[i]);
	    }
        
        //Set2        
        for(int i=0;i<messageCount;i++)
        {
            client.dispatch("vm://smtpinbound","Message : "+(i+messageCount),null);
            received[i]=false;
        }
        
        //wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(500000, messageCount*2)); 
        
        //Retrieve using GreenMail API
        messages = servers.getReceivedMessages();
        assertEquals(messageCount*2, messages.length);
        for(int i=messageCount;i<messageCount*2;i++)
        {
            String message=servers.util().getBody(messages[i]).trim();
            int messageNumber=Integer.parseInt(message.substring(message.lastIndexOf(" ")+1));
            assertTrue(received[messageNumber-messageCount]==false);
            received[messageNumber-messageCount]=true;
        }
        for(int i=0;i<messageCount;i++)
        {
            assertTrue(received[i]);
        }
	}
	
	@Override
	protected String getConfigResources() {
		return "mule-smtp-config.xml";
	}

}

/**
 * 
 */
package emailtests;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 * 
 * send email using Smtps
 *
 */
public class SmtpsTestCase extends SmtpTestCase{

	protected void doPreFunctionalSetUp() throws Exception {
		super.doPreFunctionalSetUp();
		servers.setUser("email@address.com", "login", "password");
		
    }
	
	@Override
	protected String getConfigResources() {
		return "mule-smtps-config.xml";
	}

}

/**
 * 
 */
package emailtests;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 *
 */
public class ImapTestCase extends Pop3TestCase{
	
	@Override
	protected String getConfigResources() {
		return "mule-imapendpoint-test-config.xml";
	}

}

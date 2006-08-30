/**
 * 
 */
package emailtests;

/**
 * @author <a href="mailto:stephen.fenech@symphonysoft.com">Stephen Fenech</a>
 *
 */
public class ImapsTestCase extends Pop3TestCase{
	
	@Override
	protected String getConfigResources() {
		return "mule-imapsendpoint-test-config.xml";
	}

}

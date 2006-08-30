
package emailtests;

/**
 * Retrieve messages with Pop3s
 */
public class Pop3sTestCase extends Pop3TestCase
{

    // @Override
    protected String getConfigResources()
    {
        return "mule-pop3sendpoint-test-config.xml";
    }

}

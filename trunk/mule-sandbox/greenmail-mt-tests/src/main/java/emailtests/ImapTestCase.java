
package emailtests;

public class ImapTestCase extends Pop3TestCase
{

    // @Override
    protected String getConfigResources()
    {
        return "mule-imapendpoint-test-config.xml";
    }

}

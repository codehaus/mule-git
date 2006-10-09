
package emailtests;

public class ImapsTestCase extends Pop3TestCase
{

    // @Override
    protected String getConfigResources()
    {
        return "mule-imapsendpoint-test-config.xml";
    }

}

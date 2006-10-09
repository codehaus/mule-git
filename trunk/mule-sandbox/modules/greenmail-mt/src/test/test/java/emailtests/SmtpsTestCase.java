
package emailtests;

/**
 * Send email using Smtps
 */
public class SmtpsTestCase extends SmtpTestCase
{

    protected void doPreFunctionalSetUp() throws Exception
    {
        super.doPreFunctionalSetUp();
        servers.setUser("email@address.com", "login", "password");
    }

    // @Override
    protected String getConfigResources()
    {
        return "mule-smtps-config.xml";
    }

}

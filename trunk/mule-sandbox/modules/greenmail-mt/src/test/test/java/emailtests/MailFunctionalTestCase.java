
package emailtests;

import com.icegreen.greenmail.util.ServerSetupTest;
import com.icegreen.greenmail.util.Servers;

import org.mule.tck.FunctionalTestCase;

/**
 * This class takes care of the setUp and tearDown of the GreenMail server, so all
 * other tests will inherit from this class.
 */
public abstract class MailFunctionalTestCase extends FunctionalTestCase
{

    protected Servers servers;
    protected int messageCount = 50;

    protected void doPreFunctionalSetUp() throws Exception
    {
        servers = new Servers(ServerSetupTest.ALL);
        servers.getSmtp().setWorkerThreadCount(5);
        servers.getSmtps().setWorkerThreadCount(5);
        servers.start();
    }

    protected void doFunctionalTearDown() throws Exception
    {
        if (null != servers)
        {
            servers.stop();
        }
    }

}

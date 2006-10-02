package $package;

import org.mule.config.ConfigurationException;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.umo.manager.UMOManager;

public class ServerNode
{
    /**
     * Main class to fire Mule's server node with the default mule-config.xml as the
     * configuration file
     * 
     * @param args ignored
     */
    public static void main(String[] args)
    {
        // Do some userful stuff here...
        try {
            MuleXmlConfigurationBuilder builder = new MuleXmlConfigurationBuilder();
            // If you need to play with the manager hook it up here, othrewise...
            builder.configure("mule-config.xml");
            // Remember that configure also start the manager
        }
        catch (ConfigurationException cfge)
        {
           throw new RuntimeException(cfge);
        }
        // Let us play the game...
    }

}


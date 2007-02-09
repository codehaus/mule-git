package org.mule.providers.jms.weblogic;

import org.mule.providers.jms.DefaultJmsTopicResolver;
import org.mule.providers.jms.JmsConnector;

/**
 * Weblogic-specific JMS topic resolver. Will use reflection and
 * a vendor API to detect topics.
 */
public class WeblogicJmsTopicResolver extends DefaultJmsTopicResolver
{
    /**
     * Create an instance of the resolver.
     *
     * @param connector owning connector
     */
    public WeblogicJmsTopicResolver (final JmsConnector connector)
    {
        super(connector);
    }
}

package org.mule.providers.email;

import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOConnector;
import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;

import java.util.Map;

/**
 * Manage a set of session details, mapping them by endpoint.  This assumes that a single
 * manager instance is associated with each connector.
 */
public class MultipleEndpointSessionManager implements SessionManager
{

    private Map sessions = new ConcurrentHashMap();

    public SessionDetails getSession(UMOImmutableEndpoint endpoint, UMOConnector connector)
    {
        return (SessionDetails) sessions.get(endpoint);
    }

    public void setSession(UMOImmutableEndpoint endpoint, UMOConnector connector, SessionDetails session)
    {
        sessions.put(endpoint, session);
    }

}

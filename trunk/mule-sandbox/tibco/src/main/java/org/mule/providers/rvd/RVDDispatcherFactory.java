package org.mule.providers.rvd;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;


/**
 * Simply spins out a dispatcher
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revsion: $
 */
public class RVDDispatcherFactory implements UMOMessageDispatcherFactory
{
    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException {
        return new RVDDispatcher(endpoint);
    }
}

package org.mule.providers.bpm;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

/**
 * Creates a WorkflowMessageDispatcher instance.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessMessageDispatcherFactory implements UMOMessageDispatcherFactory
 {
    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException {
        return new ProcessMessageDispatcher(endpoint);
    }
}

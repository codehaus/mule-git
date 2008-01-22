/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.endpoint;

import org.mule.api.MuleContext;
import org.mule.api.UMOFilter;
import org.mule.api.UMOTransactionConfig;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.security.UMOEndpointSecurityFilter;
import org.mule.api.transformer.UMOTransformer;
import org.mule.api.transport.ConnectionStrategy;
import org.mule.api.transport.UMOConnector;
import org.mule.impl.endpoint.URIBuilder;

import java.util.List;
import java.util.Map;

/**
 * Constructs endpoints. Transport specific endpoints can easily resolve the UMOEndpoint implementation to be
 * uses, for generic endpoints we can either resolve the transport from uri string or use a default
 * implementation.
 */
public interface UMOEndpointBuilder extends MuleContextAware, Cloneable
{

    /**
     * Constructs inbound endpoints
     * 
     * @return
     * @throws EndpointException
     * @throws InitialisationException
     */
    UMOImmutableEndpoint buildInboundEndpoint() throws EndpointException, InitialisationException;

    /**
     * Constructs outbound endpoints
     * 
     * @return
     * @throws EndpointException
     * @throws InitialisationException
     */
    UMOImmutableEndpoint buildOutboundEndpoint() throws EndpointException, InitialisationException;

    void setConnector(UMOConnector connector);

    void addTransformer(UMOTransformer transformer);

    void setTransformers(List transformers);

    void setResponseTransformers(List responseTransformer);

    void setName(String name);

    void setProperty(Object key, Object value);
    
    void setProperties(Map properties);

    void setTransactionConfig(UMOTransactionConfig transactionConfig);

    void setFilter(UMOFilter filter);

    void setDeleteUnacceptedMessages(boolean deleteUnacceptedMessages);

    void setSecurityFilter(UMOEndpointSecurityFilter securityFilter);

    void setSynchronous(boolean synchronous);

    void setRemoteSync(boolean remoteSync);

    void setRemoteSyncTimeout(int remoteSyncTimeout);

    void setInitialState(String initialState);

    void setEncoding(String encoding);

    void setRegistryId(String registryId);

    void setMuleContext(MuleContext muleContext);

    void setConnectionStrategy(ConnectionStrategy connectionStrategy);

    void setURIBuilder(URIBuilder URIBuilder);

    Object clone() throws CloneNotSupportedException;

}

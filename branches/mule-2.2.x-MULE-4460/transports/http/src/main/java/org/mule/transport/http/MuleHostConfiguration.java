/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * Subclass of httpclient's {@link HostConfiguration} that retains its {@link Protocol} when
 * a new host is set via the URI.
 * 
 * It looks like we're not the only ones who stumbled over the HostConfiguration behaviour, see
 * http://issues.apache.org/jira/browse/HTTPCLIENT-634
 */
public class MuleHostConfiguration extends HostConfiguration
{
    
    public MuleHostConfiguration()
    {
        super();
    }
    
    public MuleHostConfiguration(HostConfiguration hostConfig)
    {
        super(hostConfig);
    }

    @Override
    public synchronized void setHost(URI uri)
    {
        Protocol originalProtocol = getProtocol();
        Protocol newProtocol = new Protocol(uri.getScheme(), originalProtocol.getSocketFactory(),
            originalProtocol.getDefaultPort());

        try
        {
            super.setHost(uri.getHost(), uri.getPort(), newProtocol);
        }
        catch (URIException uriException)
        {
            throw new IllegalArgumentException(uriException);
        }
    }

    @Override
    public Object clone()
    {
        return new MuleHostConfiguration(this);
    }

}



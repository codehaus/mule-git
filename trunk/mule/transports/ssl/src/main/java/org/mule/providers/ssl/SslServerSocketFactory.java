/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ssl;

import org.apache.commons.lang.StringUtils;
import org.mule.umo.security.tls.TlsConfiguration;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class SslServerSocketFactory
{

    private TlsConfiguration tls;

    SslServerSocketFactory(TlsConfiguration tls)
    {
        this.tls = tls;
    }

    protected ServerSocket createServerSocket(URI uri, int backlog)
        throws IOException, NoSuchAlgorithmException, KeyManagementException
    {
        String host = StringUtils.defaultIfEmpty(uri.getHost(), "localhost");
        InetAddress inetAddress = InetAddress.getByName(host);

        if (inetAddress.equals(InetAddress.getLocalHost())
                || inetAddress.isLoopbackAddress()
                || host.trim().equals("localhost"))
        {
            return createServerSocket(uri.getPort(), backlog);
        }
        else
        {
            return createServerSocket(uri.getPort(), backlog, inetAddress);
        }
    }

    protected ServerSocket createServerSocket(int port, int backlog, InetAddress address)
            throws IOException, NoSuchAlgorithmException, KeyManagementException
    {
        ServerSocketFactory ssf = tls.getServerSocketFactory();
        return ssf.createServerSocket(port, backlog, address);
    }

    protected ServerSocket createServerSocket(int port, int backlog)
            throws IOException, NoSuchAlgorithmException, KeyManagementException
    {
        ServerSocketFactory ssf = tls.getServerSocketFactory();
        return ssf.createServerSocket(port, backlog);
    }

}

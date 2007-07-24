/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp;

import org.mule.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.SocketException;

public class TcpServerSocketFactory implements SimpleServerSocketFactory
{

    public ServerSocket createServerSocket(URI uri, int backlog, Boolean reuse) throws IOException
    {
        String host = StringUtils.defaultIfEmpty(uri.getHost(), "localhost");
        InetAddress inetAddress = InetAddress.getByName(host);

        if (inetAddress.equals(InetAddress.getLocalHost())
                || inetAddress.isLoopbackAddress()
                || host.trim().equals("localhost"))
        {
            return createServerSocket(uri.getPort(), backlog, reuse);
        }
        else
        {
            return createServerSocket(inetAddress, uri.getPort(), backlog, reuse);
        }
    }

    public ServerSocket createServerSocket(InetAddress address, int port, int backlog, Boolean reuse) throws IOException
    {
        ServerSocket socket = new ServerSocket(port, backlog, address);
        setReuseAddress(socket, reuse);
        return socket;
    }

    public ServerSocket createServerSocket(int port, int backlog, Boolean reuse) throws IOException
    {
        ServerSocket socket = new ServerSocket(port, backlog);
        setReuseAddress(socket, reuse);
        return socket;
    }

    protected void setReuseAddress(ServerSocket socket, Boolean reuse) throws SocketException
    {
        if (null != reuse && reuse.booleanValue() != socket.getReuseAddress())
        {
            socket.setReuseAddress(reuse.booleanValue());
        }
    }

}

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

import org.mule.tck.AbstractMuleTestCase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

public class MuleHostConfigurationTestCase extends AbstractMuleTestCase
{
    
    public void testSetHostViaUri() throws Exception
    {
        HostConfiguration hostConfig = createHostConfiguration();
        
        URI uri = new URI("http://www.mulesource.org:8080", false);
        hostConfig.setHost(uri);
        assertNewHostValues(hostConfig);
    }

    public void testSetHostViaHttpHost()
    {
        HostConfiguration hostConfig = createHostConfiguration();
        
        HttpHost host = new HttpHost("www.mulesource.org", 8080);
        hostConfig.setHost(host);
        assertNewHostValues(hostConfig);
    }

    public void testSetHostViaHostAndPortAndProtocolName()
    {
        HostConfiguration hostConfig = createHostConfiguration();
        
        hostConfig.setHost("www.mulesource.org", 8080, "http");
        assertNewHostValues(hostConfig);
    }
        
    @SuppressWarnings("deprecation")
    public void testSetHostViaHostAndVirtualHostAndPortAndProtocol()
    {
        HostConfiguration hostConfig = createHostConfiguration();

        Protocol protocol = Protocol.getProtocol("http");
        hostConfig.setHost("www.mulesource.org", "www.mulesource.com", 8080, protocol);
        assertNewHostValues(hostConfig);
        assertEquals("www.mulesource.com", hostConfig.getVirtualHost());
    }
    
    public void testSetHostViaHostAndPort()
    {
        HostConfiguration hostConfig = createHostConfiguration();

        hostConfig.setHost("www.mulesource.org", 8080);
        assertNewHostValues(hostConfig);
    }

    public void testSetHostViaHost()
    {
        HostConfiguration hostConfig = createHostConfiguration();
        
        hostConfig.setHost("www.mulesource.org");
        assertEquals("www.mulesource.org", hostConfig.getHost());
        assertSocketFactory(hostConfig);
    }

    public void testClone()
    {
        HostConfiguration hostConfig = createHostConfiguration();
        HostConfiguration clone = (HostConfiguration) hostConfig.clone();
        assertSocketFactory(clone);
    }
    
    public void testSetDifferentProtocol()
    {
        HostConfiguration hostConfig = createHostConfiguration();

        ProtocolSocketFactory factory = new SSLProtocolSocketFactory();
        Protocol differentProtocol = new Protocol("httpx", factory, 81);
        hostConfig.setHost("www.mulesource.org", 8181, differentProtocol);
        
        assertEquals("www.mulesource.org", hostConfig.getHost());
        assertEquals(8181, hostConfig.getPort());
        assertEquals("httpx", hostConfig.getProtocol().getScheme());
        assertFalse(hostConfig.getProtocol().getSocketFactory() instanceof MockSecureProtocolFactory);
    }

    private MuleHostConfiguration createHostConfiguration()
    {
        MuleHostConfiguration hostConfig = new MuleHostConfiguration();
        ProtocolSocketFactory socketFactory = new MockSecureProtocolFactory();
        Protocol protocol = new Protocol("http", socketFactory, 80);
        hostConfig.setHost("localhost", 80, protocol);
        
        // since we're using a setHost variant here, too let's assert that it actually worked
        assertSocketFactory(hostConfig);
        
        return hostConfig;
    }
    
    private void assertSocketFactory(HostConfiguration hostConfig)
    {
        assertTrue(hostConfig.getProtocol().getSocketFactory() instanceof MockSecureProtocolFactory);
    }
    
    private void assertNewHostValues(HostConfiguration hostConfig)
    {
        assertSocketFactory(hostConfig);
        assertEquals("www.mulesource.org", hostConfig.getHost());
        assertEquals(8080, hostConfig.getPort());
    }

    private static class MockSecureProtocolFactory implements SecureProtocolSocketFactory
    {
        public MockSecureProtocolFactory()
        {
            super();
        }
        
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
            throws IOException, UnknownHostException
        {
            throw new UnsupportedOperationException();
        }

        public Socket createSocket(String host, int port) throws IOException, UnknownHostException
        {
            throw new UnsupportedOperationException();
        }

        public Socket createSocket(String host, int port, InetAddress localAddress, int localPort)
            throws IOException, UnknownHostException
        {
            throw new UnsupportedOperationException();
        }

        public Socket createSocket(String host, int port, InetAddress localAddress, int localPort,
            HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException
        {
            throw new UnsupportedOperationException();
        }
    }
    
}



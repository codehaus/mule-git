/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.tcp.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.tcp.TcpConnector;
import org.mule.transport.tcp.TcpProtocol;
import org.mule.transport.tcp.protocols.DirectProtocol;
import org.mule.transport.tcp.protocols.EOFProtocol;
import org.mule.transport.tcp.protocols.LengthProtocol;
import org.mule.transport.tcp.protocols.MuleMessageDirectProtocol;
import org.mule.transport.tcp.protocols.MuleMessageEOFProtocol;
import org.mule.transport.tcp.protocols.MuleMessageLengthProtocol;
import org.mule.transport.tcp.protocols.MuleMessageSafeProtocol;
import org.mule.transport.tcp.protocols.SafeProtocol;
import org.mule.transport.tcp.protocols.StreamingProtocol;
import org.mule.transport.tcp.protocols.XmlMessageEOFProtocol;
import org.mule.transport.tcp.protocols.XmlMessageProtocol;

/**
 * Registers a Bean Definition Parser for handling <code><tcp:connector></code> elements.
 *
 */
public class TcpNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerStandardTransportEndpoints(TcpConnector.TCP, URIBuilder.SOCKET_ATTRIBUTES);
        registerConnectorDefinitionParser(TcpConnector.class);
        registerBeanDefinitionParser("custom-protocol", new ChildDefinitionParser("tcpProtocol", null, TcpProtocol.class, true));
        registerBeanDefinitionParser("xml-protocol", new ChildDefinitionParser("tcpProtocol", XmlMessageProtocol.class));
        registerBeanDefinitionParser("xml-eof-protocol", new ChildDefinitionParser("tcpProtocol", XmlMessageEOFProtocol.class));
        registerBeanDefinitionParser("safe-protocol", new ByteOrMessageProtocolDefinitionParser(SafeProtocol.class, MuleMessageSafeProtocol.class));
        registerBeanDefinitionParser("length-protocol", new ByteOrMessageProtocolDefinitionParser(LengthProtocol.class, MuleMessageLengthProtocol.class));
        registerBeanDefinitionParser("eof-protocol", new ByteOrMessageProtocolDefinitionParser(EOFProtocol.class, MuleMessageEOFProtocol.class));
        registerBeanDefinitionParser("direct-protocol", new ByteOrMessageProtocolDefinitionParser(DirectProtocol.class, MuleMessageDirectProtocol.class));
        registerBeanDefinitionParser("streaming-protocol", new ByteOrMessageProtocolDefinitionParser(StreamingProtocol.class, MuleMessageDirectProtocol.class));
    }

}
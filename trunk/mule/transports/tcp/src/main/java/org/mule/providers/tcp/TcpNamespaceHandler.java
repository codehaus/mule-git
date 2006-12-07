/*
 * $Header: /opt/cvsroot/mule2/transports/tcp/src/main/java/org/mule/providers/tcp/TcpNamespaceHandler.java,v 1.1 2006/02/01 19:42:03 rossmason Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/01 19:42:03 $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.tcp;

import org.w3c.dom.Element;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;

/**
 * todo document
 *
 */
public class TcpNamespaceHandler extends NamespaceHandlerSupport
{


    public void init()
    {
        registerBeanDefinitionParser("connector", new ConnectorDefinitionParser());
    }


    public static class ConnectorDefinitionParser extends AbstractSingleBeanDefinitionParser
    {
        protected Class getBeanClass(Element element) {
            return TcpConnector.class;
        }
    }
}
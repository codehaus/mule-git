/*
 * $Header: /opt/cvsroot/mule2/mule-core/src/main/java/org/mule/spring/config/handlers/AbstractConnectorNamespaceHandler.java,v 1.1 2006/02/01 19:42:11 rossmason Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/01 19:42:11 $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.extras.spring.config.handlers;

import org.mule.config.ConfigurationException;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractConnectorNamespaceHandler extends AbstractNamespaceHandler {

    public AbstractConnectorNamespaceHandler(ClassLoader classLoader) throws ConfigurationException {
        super(classLoader);
//        registerBeanDefinitionParser("receiver-connection-strategy", new MuleNamespaceHandler.ConnectionStrategyDefinitionParser("receivererConnectionStrategy"));
//        registerBeanDefinitionParser("dispatcher-connection-strategy", new MuleNamespaceHandler.ConnectionStrategyDefinitionParser("dispatcherConnectionStrategy"));
//        registerBeanDefinitionParser("receiver-threading-profile", new MuleNamespaceHandler.ThreadingProfileDefinitionParser("receiverThreadingProfile"));
//        registerBeanDefinitionParser("dispatcher-threading-profile", new MuleNamespaceHandler.ThreadingProfileDefinitionParser("dispatcherThreadingProfile"));
//        registerBeanDefinitionParser("service-overrides", new MuleNamespaceHandler.ServiceOverridesDefinitionParser());
//        registerBeanDefinitionParser("exception-strategy", new MuleNamespaceHandler.ExceptionStrategyDefinitionParser());
    }


}

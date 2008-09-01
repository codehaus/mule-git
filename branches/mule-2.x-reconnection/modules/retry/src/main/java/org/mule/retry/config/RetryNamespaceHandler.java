/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */
package org.mule.retry.config;

import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.retry.notifiers.ConnectNotifier;
import org.mule.retry.policies.RetryForeverPolicyTemplate;
import org.mule.retry.policies.SimpleRetryPolicyTemplate;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class RetryNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("simple-policy", new RetryPolicyDefinitionParser(SimpleRetryPolicyTemplate.class));
        registerBeanDefinitionParser("forever-policy", new RetryPolicyDefinitionParser(RetryForeverPolicyTemplate.class));
        registerBeanDefinitionParser("custom-policy", new RetryPolicyDefinitionParser());
        
        registerBeanDefinitionParser("connect-notifier", new RetryNotifierDefinitionParser(ConnectNotifier.class));
        registerBeanDefinitionParser("custom-notifier", new RetryNotifierDefinitionParser());
    }
    
    class RetryPolicyDefinitionParser extends ChildDefinitionParser
    {
        RetryPolicyDefinitionParser()
        {
            super("retryPolicyTemplate");
        }

        RetryPolicyDefinitionParser(Class clazz)
        {
            super("retryPolicyTemplate", clazz);
        }
    };

    class RetryNotifierDefinitionParser extends ChildDefinitionParser
    {
        RetryNotifierDefinitionParser()
        {
            super("notifier");
        }

        RetryNotifierDefinitionParser(Class clazz)
        {
            super("notifier", clazz);
        }
    };
}

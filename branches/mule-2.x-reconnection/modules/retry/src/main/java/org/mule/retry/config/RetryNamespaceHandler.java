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
import org.mule.retry.policies.RetryForeverPolicyFactory;
import org.mule.retry.policies.SimpleRetryPolicyFactory;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class RetryNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("simple-policy", new ChildDefinitionParser("retryPolicyFactory", SimpleRetryPolicyFactory.class));
        registerBeanDefinitionParser("forever-policy", new ChildDefinitionParser("retryPolicyFactory", RetryForeverPolicyFactory.class));
        registerBeanDefinitionParser("custom-policy", new ChildDefinitionParser("retryPolicyFactory"));
        //registerBeanDefinitionParser("connect-notifier", new ChildDefinitionParser("retryPolicyFactory", RetryForeverPolicyFactory.class));
    }
}

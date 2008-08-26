/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.spring.parsers.specific;

import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.retry.DefaultRetryTemplate;

/**
 * Handles the parsing of <code><mule:retry-policy>, <mule:dispatcher-retry-policy>,
 * <mule:receiver-retry-policy></code> elements in Mule Xml configuration.
 */
public class RetryPolicyDefinitionParser extends ChildDefinitionParser
{

    public static final Class DEFAULT_RETRY_POLICY = DefaultRetryTemplate.class;

    public RetryPolicyDefinitionParser(String propertyName)
    {
        super(propertyName, DEFAULT_RETRY_POLICY, DefaultRetryTemplate.class, true);
    }

    /**
     * Default connection strategies are available in the registry, but have n0 parent
     */
    public RetryPolicyDefinitionParser()
    {
        super(null, DEFAULT_RETRY_POLICY, DefaultRetryTemplate.class, true);
    }

}

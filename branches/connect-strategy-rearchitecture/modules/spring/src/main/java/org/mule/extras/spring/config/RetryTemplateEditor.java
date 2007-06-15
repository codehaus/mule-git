/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import org.mule.impl.retry.RetryTemplate;
import org.mule.umo.retry.UMOPolicyFactory;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>RetryTemplateEditor</code> wraps a Retry policy in a RetryTemplate
 */
public class RetryTemplateEditor extends PropertyEditorSupport
{
    /**
     * logger used by this class
     */
    protected static final Log logger = LogFactory.getLog(RetryTemplateEditor.class);

    //@java.lang.Override
    public void setValue(Object o)
    {
        super.setValue(new RetryTemplate((UMOPolicyFactory)o));
    }
}
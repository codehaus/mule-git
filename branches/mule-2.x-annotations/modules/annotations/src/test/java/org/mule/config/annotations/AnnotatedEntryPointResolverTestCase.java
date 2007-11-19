/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.annotations;

import org.mule.components.simple.EchoComponent;
import org.mule.impl.model.resolvers.AnnotatedEntryPointResolver;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.model.InvocationResult;

public class AnnotatedEntryPointResolverTestCase extends AbstractMuleTestCase
{
    public void testAnnotatedMethod() throws Exception
    {
        AnnotatedEntryPointResolver resolver = new AnnotatedEntryPointResolver();
        AnnotatedComponent component = new AnnotatedComponent();
        InvocationResult result = resolver.invoke(component, getTestEventContext("blah"));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_SUCESSFUL);
    }

    public void testBadlyAnnotatedMethod() throws Exception
    {
        AnnotatedEntryPointResolver resolver = new AnnotatedEntryPointResolver();
        BadlyAnnotatedComponent component = new BadlyAnnotatedComponent();
        InvocationResult result = resolver.invoke(component, getTestEventContext("blah"));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKED_FAILED);
    }

    public void testNonAnnotatedMethod() throws Exception
    {
        AnnotatedEntryPointResolver resolver = new AnnotatedEntryPointResolver();
        InvocationResult result = resolver.invoke(new EchoComponent(), getTestEventContext("blah"));
        assertEquals(result.getState(), InvocationResult.STATE_INVOKE_NOT_SUPPORTED);
    }

}


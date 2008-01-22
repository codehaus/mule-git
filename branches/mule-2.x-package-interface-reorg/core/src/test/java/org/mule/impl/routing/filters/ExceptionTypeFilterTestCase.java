/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing.filters;

import org.mule.api.MuleMessage;
import org.mule.impl.DefaultMuleMessage;
import org.mule.impl.message.DefaultExceptionPayload;
import org.mule.impl.routing.filters.ExceptionTypeFilter;
import org.mule.tck.AbstractMuleTestCase;

import java.io.IOException;

public class ExceptionTypeFilterTestCase extends AbstractMuleTestCase
{

    public void testExceptionTypeFilter()
    {
        ExceptionTypeFilter filter = new ExceptionTypeFilter();
        assertNull(filter.getExpectedType());
        MuleMessage m = new DefaultMuleMessage("test");
        assertTrue(!filter.accept(m));
        m.setExceptionPayload(new DefaultExceptionPayload(new IllegalArgumentException("test")));
        assertTrue(filter.accept(m));

        filter = new ExceptionTypeFilter(IOException.class);
        assertTrue(!filter.accept(m));
        m.setExceptionPayload(new DefaultExceptionPayload(new IOException("test")));
        assertTrue(filter.accept(m));
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.config;

import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.tck.AbstractMuleTestCase;

import java.util.MissingResourceException;

// TODO DO: adapt test case to new message handling code
public class MessagesTestCase extends AbstractMuleTestCase
{

    public void testMessageLoading() throws Exception
    {
        Message message = CoreMessages.authFailedForUser("Fred");
        assertEquals("Authentication failed for principal Fred", message.getMessage());
        assertEquals(Messages.DEFAULT_BUNDLE, message.getBundle());
        assertEquals(Messages.AUTH_FAILED_FOR_USER_X, message.getCode());
    }

    public void testBadBundle()
    {
        try
        {
            new Message("blah", 1);
            fail("should throw resource bundle not found exception");
        }
        catch (MissingResourceException e)
        {
            assertTrue(e.getMessage().startsWith("Can't find bundle"));
        }
    }

    public void testGoodBundle()
    {
        Message message = new Message("test", 1, "one", "two", "three");
        assertEquals("Testing, Testing, one, two, three", message.getMessage());
        assertEquals("test", message.getBundle());
        assertEquals(1, message.getCode());
    }
}

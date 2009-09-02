/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.transport;

import org.mule.api.MuleMessage;

/**
 * <code>MuleMessageFactory</code> is a helper to create a {@link MuleMessage} from a transport's
 * native message format (e.g. JMS message).
 */
public interface MuleMessageFactory
{
    /**
     * Creates a {@link MuleMessage} instance from <code>transportMessage</code> by copying
     * its payload to the new {@link MuleMessage} and, if available, any relevant message properties
     * and attachments.
     */
    MuleMessage create(Object transportMessage) throws Exception;
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.jms.integration.activemq;

import org.mule.transport.jms.integration.JmsDurableTopicSingleTxTestCase;

public class ActiveMQDurableTopicSingleTxTestCase extends JmsDurableTopicSingleTxTestCase
{
    public ActiveMQDurableTopicSingleTxTestCase()
    {
        super(new ActiveMQJmsConfiguration());
    }
}

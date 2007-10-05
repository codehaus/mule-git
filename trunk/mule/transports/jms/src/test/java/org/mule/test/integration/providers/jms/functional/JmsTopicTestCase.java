/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.integration.providers.jms.functional;

/**
 * Message is put to topic with two subscribers
 */
public class JmsTopicTestCase extends AbstractJmsFunctionalTestCase
{

    public void testJmsTopic() throws Exception
    {
        dispatchMessage();
        recieveMessage();
        recieveMessage();

    }

    protected String getConfigResources()
    {
        return "providers/activemq/jms-topic.xml";
    }
}

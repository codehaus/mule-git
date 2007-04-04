/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests;

/**
 * Retrieve messages with Pop3s
 */
public class Pop3sTestCase extends Pop3TestCase
{

    // @Override
    protected String getConfigResources()
    {
        return "mule-pop3sendpoint-test-config.xml";
    }

}

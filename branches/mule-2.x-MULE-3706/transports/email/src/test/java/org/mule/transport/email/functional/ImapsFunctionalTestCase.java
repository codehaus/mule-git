/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.email.functional;

public class ImapsFunctionalTestCase extends AbstractEmailFunctionalTestCase
{

    public ImapsFunctionalTestCase()
    {
        super(65444, STRING_MESSAGE, "imaps");
    }

    public void testRequest() throws Exception
    {
        doRequest();
    }

}
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests.components;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.mule.impl.MuleMessage;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.Callable;

public class EmailWithAttachmentAPI implements Callable
{

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.lifecycle.Callable#onCall(org.mule.umo.UMOEventContext)
     */
    public Object onCall(UMOEventContext context) throws Exception
    {
        UMOMessage msg;
        msg = context.getMessage();
        String payload = (String)msg.getPayload();
        String[] strings = payload.split(";");

        msg = new MuleMessage(strings[0]);

        for (int i = 1; i < strings.length; i++)
        {
            msg.addAttachment(strings[i], new DataHandler(new FileDataSource("./src/test/resources/"
                                                                             + strings[i])));
        }

        return msg;
    }
}

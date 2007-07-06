/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.pgp;

import org.mule.extras.client.MuleClient;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.impl.internal.notifications.ExceptionNotification;
import org.mule.impl.internal.notifications.ExceptionNotificationListener;
import org.mule.impl.internal.notifications.SecurityNotificationListener;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOExceptionPayload;
import org.mule.umo.UMOMessage;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.security.UnauthorisedException;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PGPSecurityFilterTestCase extends FunctionalTestCase
{
    
    protected static final String TARGET = "/encrypted.txt";
    protected static final String DIRECTORY = "output";
    protected static final String MESSAGE_EXCEPTION = "No signed message found. Message payload is of type: String";

    protected String getConfigResources()
    {
        return "test-pgp-encrypt-config.xml";
    }

    public void testAuthenticationAuthorised() throws Exception
    {
        URL url = Thread.currentThread().getContextClassLoader().getResource("./encrypted-signed.asc");
        
        
        int length = (int)new File(url.getFile()).length();
        byte[] msg = new byte[length];

        FileInputStream in = new FileInputStream(url.getFile());
        in.read(msg);
        in.close();

        Map props = new HashMap();
        props.put("TARGET_FILE", TARGET);
        MuleClient client = new MuleClient();
        UMOMessage reply = client.send("vm://echo", new String(msg), props);
        assertNull(reply.getExceptionPayload());
        
        try
        {
            //check if file exists
            FileReader outputFile = new FileReader(DIRECTORY+TARGET);
            outputFile.close();
            
            //delete file not to be confused with tests to be performed later
            File f = new File(DIRECTORY+TARGET);
            f.delete();
        }
        catch (FileNotFoundException fileNotFound)
        {
            fail("File not successfully created");
        }
    }

    public void testAuthenticationNotAuthorised() throws Exception
    {
        MuleClient client = new MuleClient();

        UMOMessage reply = client.send("vm://echo", new String("An unsigned message"), null);
        
        assertNotNull(reply.getExceptionPayload());
        UMOExceptionPayload excPayload = reply.getExceptionPayload();
        assertEquals(MESSAGE_EXCEPTION, excPayload.getMessage());

    }
}

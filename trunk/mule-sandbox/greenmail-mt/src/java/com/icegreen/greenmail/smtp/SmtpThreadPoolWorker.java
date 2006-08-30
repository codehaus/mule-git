/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.icegreen.greenmail.smtp;

import com.icegreen.greenmail.util.ThreadPoolWorker;

import java.io.IOException;
import java.net.Socket;

public class SmtpThreadPoolWorker extends ThreadPoolWorker
{
    private SmtpHandler smtpHandler;
    
    public SmtpThreadPoolWorker(SmtpHandler smtpHandler)
    {
        this.smtpHandler=smtpHandler;
    }

    public void doWork(Object o)
    {
        try
        {
            smtpHandler.handleConnection((Socket)o);
        }
        catch (IOException e)
        {
            // TODO Handle Exception
            e.printStackTrace();
        }        
    }
            
}




package com.icegreen.greenmail.smtp;

import com.icegreen.greenmail.util.ThreadPoolWorker;

import java.io.IOException;
import java.net.Socket;

public class SmtpThreadPoolWorker extends ThreadPoolWorker
{
    private SmtpHandler smtpHandler;

    public SmtpThreadPoolWorker(SmtpHandler smtpHandler)
    {
        this.smtpHandler = smtpHandler;
    }

    public void doWork(Object o)
    {
        try
        {
            smtpHandler.handleConnection((Socket)o);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}

/*
* Copyright (c) 2006 Wael Chatila / Icegreen Technologies. All Rights Reserved.
* This software is released under the LGPL which is available at http://www.gnu.org/copyleft/lesser.html
* This file has been used and modified. Original file can be found on http://foedus.sourceforge.net
*/
package com.icegreen.greenmail.smtp;

import com.icegreen.greenmail.AbstractServer;
import com.icegreen.greenmail.Managers;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ThreadPool;
import com.icegreen.greenmail.smtp.commands.SmtpCommandRegistry;
import com.icegreen.greenmail.foedus.util.InMemoryWorkspace;

import java.io.IOException;
import java.net.SocketException;

public class SmtpServer extends AbstractServer {
    private SmtpHandler smtpHandler = null;
    private ThreadPool threadPool = null;
    private int workerThreadCount = 0;
    private volatile boolean started = false;

    public SmtpServer(ServerSetup setup, Managers managers) {
        super(setup, managers);
    }
    
    public void quit() {
        if(smtpHandler!=null)
        {
            smtpHandler.quit();
        }
        if(threadPool!=null){
            threadPool.shutdown();
        }
        try {
            if (null != clientSocket) {
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (null != serverSocket) {
                serverSocket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        super.run();

        started = false;
        if(workerThreadCount>1)
        {
            createThreadPool(workerThreadCount);
            threadPool.start();
        }else
        {
            smtpHandler = new SmtpHandler(new SmtpCommandRegistry(), managers.getSmtpManager(), new InMemoryWorkspace());
        }
        try {
            serverSocket = openServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (keepOn()) {
            try {
                clientSocket = serverSocket.accept();
                if(threadPool!=null){
                    threadPool.addTaskObject(clientSocket);
                }else{
                    smtpHandler.handleConnection(clientSocket);
                }
            } catch (SocketException ignored) {
                // ignored
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private void createThreadPool(int threadBound)
    {
        SmtpThreadPoolWorker[] workers = new SmtpThreadPoolWorker[threadBound];

        for (int i = 0; i < threadBound; i++)
        {
            workers[i] = new SmtpThreadPoolWorker(new SmtpHandler(new SmtpCommandRegistry(),
                managers.getSmtpManager(), new InMemoryWorkspace()));
            workers[i].setName(this.getProtocol() + " Thread Pool Worker " + i);
        }

        threadPool = new ThreadPool(workers);
    }

    public int getWorkerThreadCount()
    {
        return this.workerThreadCount;
    }

    public void setWorkerThreadCount(int workerThreadsCount)
    {
        if (!started)
        {
            this.workerThreadCount = workerThreadsCount;
        }
        else
        {
            throw new IllegalStateException("Cannot setWorkerThreadCount after start!");
        }
    }
   
}
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.ftpserver.ConfigurableFtpServerContext;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.config.PropertiesConfiguration;
import org.apache.ftpserver.ftplet.Configuration;
import org.apache.ftpserver.ftplet.FileObject;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.interfaces.FtpServerContext;

public class FtpFunctionalTestCase extends FunctionalTestCase
{

    private String TEST_MESSAGE = "Test FTP message";
    private String FILENAME = "test-filename";
    private static AtomicInteger port = new AtomicInteger(60198);
    private String VIEW_KEY = "view-key-";
    private long TIMEOUT = 10000;
    private FtpServer server;
    private String messageReceived;
    private CountDownLatch messageReceivedCounter = new CountDownLatch(1);

    public FtpFunctionalTestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "ftp-functional-test.xml";
    }

    protected void doPostFunctionalSetUp() throws Exception
    {
        UploadView view = new UploadView();
        // start the server
        Properties properties = new Properties();
        properties.setProperty("config.listeners.default.port", Integer.toString(port.incrementAndGet()));
        properties.setProperty("config.file-system-manager.class", UploadManager.class.getName());
        properties.setProperty("config.file-system-manager.viewFromSystemProperties", VIEW_KEY + port.get());
        System.getProperties().put(VIEW_KEY + port.get(), view);
        properties.setProperty("config.connection-manager.default-idle-time", "1");
        properties.setProperty("config.connection-manager.max-login", "1000");
        properties.setProperty("config.connection-manager.max-anonymous-login", "1000");
        Configuration config = new PropertiesConfiguration(properties);
        FtpServerContext context = new ConfigurableFtpServerContext(config);
        server = new FtpServer(context);
        server.start();
        view.waitToStart(TIMEOUT); // can we do better than this?
    }

    protected void doFunctionalTearDown() throws Exception
    {
        // stop the server
        if (null != server)
        {
            server.stop();
        }
    }

    public void testSendAndReceive() throws Exception
    {
        Map properties = new HashMap();
        properties.put(FtpConnector.PROPERTY_FILENAME, FILENAME);
        MuleClient client = new MuleClient();
        client.dispatch("ftp://anonymous:email@localhost:" + port.get(), TEST_MESSAGE, properties);
        messageReceivedCounter.await(TIMEOUT, TimeUnit.MILLISECONDS);
        assertNotNull(messageReceived);
        assertEquals(TEST_MESSAGE, messageReceived);
        logger.info("received message OK!");
        UMOMessage retrieved = client.receive("ftp://anonymous:email@localhost:" + port.get(), TIMEOUT);
        assertNotNull(retrieved);
        assertEquals(retrieved.getPayloadAsString(), TEST_MESSAGE);
    }


    /**
     * These inner classes that give direct access to the test.
     *
     * An instance is supplied to the server via System properties (the UploadManager
     * bean takes the key as a parameter).  This is flakey, but it's difficult to see how
     * else to make a connection within the VM to an instance instantiated outside
     * our control.
     */
    public class UploadView implements FileSystemView
    {

        private CountDownLatch started = new CountDownLatch(1);

        public void flagStarted()
        {
            started.countDown();
        }

        public void waitToStart(long ms) throws InterruptedException
        {
            started.await(ms, TimeUnit.MILLISECONDS);
        }

        public FileObject getHomeDirectory() throws FtpException
        {
            return new Directory("/");
        }

        public FileObject getCurrentDirectory() throws FtpException
        {
            return new Directory("/");
        }

        public boolean changeDirectory(String dir) throws FtpException
        {
            return true;
        }

        public FileObject getFileObject(String file) throws FtpException
        {
            return new UploadFile(file);
        }

        public boolean isRandomAccessible() throws FtpException
        {
            return true;
        }

        public void dispose()
        {
        }

    }

    private class Directory extends NamedFileObject
    {

        public Directory(String name)
        {
            super(name);
        }

        public boolean isDirectory()
        {
            return true;
        }

        public boolean isFile()
        {
            return false;
        }

        public FileObject[] listFiles()
        {
            return new FileObject[]{new UploadFile("file")};
        }

        public OutputStream createOutputStream(long offset) throws IOException {
            return null;
        }

        public InputStream createInputStream(long offset) throws IOException {
            return null;
        }
    }

    private class UploadFile extends NamedFileObject
    {

        public UploadFile(String name)
        {
            super(name);
        }

        public boolean isDirectory()
        {
            return false;
        }

        public boolean isFile()
        {
            return true;
        }

        public FileObject[] listFiles()
        {
            return new FileObject[0];
        }

        public OutputStream createOutputStream(long offset) throws IOException
        {
            return new SignallingOutputStream();
        }

        public InputStream createInputStream(long offset) throws IOException
        {
            return new ByteArrayInputStream(messageReceived.getBytes());
        }

    }

    private class SignallingOutputStream extends OutputStream
    {

        private ByteArrayOutputStream delegate = new ByteArrayOutputStream();

        public void write(int b) throws IOException
        {
            delegate.write(b);
        }

        // @Override
        public void close() throws IOException
        {
            delegate.close();
            messageReceived = new String(delegate.toByteArray());
            messageReceivedCounter.countDown();
            super.close();
        }

    }

}

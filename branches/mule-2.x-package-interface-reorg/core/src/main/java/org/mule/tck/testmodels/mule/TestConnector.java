/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.api.MessagingException;
import org.mule.api.ThreadSafeAccess;
import org.mule.api.UMOComponent;
import org.mule.api.UMOException;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.UMOMessageAdapter;
import org.mule.api.transport.UMOMessageDispatcher;
import org.mule.api.transport.UMOMessageReceiver;
import org.mule.impl.transport.AbstractConnector;
import org.mule.impl.transport.AbstractMessageAdapter;
import org.mule.impl.transport.AbstractMessageDispatcherFactory;
import org.mule.impl.transport.AbstractMessageReceiver;

/**
 * <code>TestConnector</code> use a mock connector
 */
public class TestConnector extends AbstractConnector
{

    private String someProperty;

    public TestConnector()
    {
        super();
        setDispatcherFactory(new AbstractMessageDispatcherFactory()
        {
            public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException
            {
                return new TestMessageDispatcher(endpoint);
            }
        });
    }

    protected void doInitialise() throws InitialisationException
    {
        // template method
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doStart() throws UMOException
    {
        // template method
    }

    protected void doStop() throws UMOException
    {
        // template method
    }

    public String getProtocol()
    {
        return "test";
    }

    public UMOMessageAdapter getMessageAdapter(Object message) throws MessagingException
    {
        return new DummyMessageAdapter(message);
    }

    public String getSomeProperty()
    {
        return someProperty;
    }

    public void setSomeProperty(String someProperty)
    {
        this.someProperty = someProperty;
    }

    public UMOMessageReceiver createReceiver(UMOComponent component, UMOImmutableEndpoint endpoint) throws Exception
    {
        UMOMessageReceiver receiver = new AbstractMessageReceiver(this, component, endpoint)
        {

            protected void doInitialise() throws InitialisationException
            {
                //nothing to do
            }

            protected void doConnect() throws Exception
            {
                // nothing to do
            }

            protected void doDisconnect() throws Exception
            {
                // nothing to do
            }

            protected void doStart() throws UMOException
            {
                // nothing to do
            }

            protected void doStop() throws UMOException
            {
                // nothing to do
            }

            protected void doDispose()
            {
                // nothing to do               
            }
        };
        return receiver;
    }

    public void destroyReceiver(UMOMessageReceiver receiver, UMOImmutableEndpoint endpoint) throws Exception
    {
        // nothing to do
    }

    public class DummyMessageAdapter extends AbstractMessageAdapter
    {
        /**
         * Serial version
         */
        private static final long serialVersionUID = -2304322766342059136L;

        private Object message = new String("DummyMessage");

        public DummyMessageAdapter(Object message)
        {
            this.message = message;
        }

        public Object getPayload()
        {
            return message;
        }

        public byte[] getPayloadAsBytes() throws Exception
        {

            return message.toString().getBytes();
        }

        public String getPayloadAsString(String encoding) throws Exception
        {
            return message.toString();
        }

        public void setPayload(Object payload)
        {
            this.message = payload;
        }

        public ThreadSafeAccess newThreadCopy()
        {
            return this;
        }
    }

}

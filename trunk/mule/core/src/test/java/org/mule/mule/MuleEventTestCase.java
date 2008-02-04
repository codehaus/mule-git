/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.mule;

import org.mule.MuleManager;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.RequestContext;
import org.mule.impl.ResponseOutputStream;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.transformers.AbstractTransformer;
import org.mule.transformers.simple.ByteArrayToObject;
import org.mule.transformers.simple.SerializableToByteArray;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.security.UMOCredentials;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;

import java.io.Serializable;
import java.util.Properties;

public class MuleEventTestCase extends AbstractMuleTestCase
{

    public void testEventInitialise() throws Exception
    {
        String data = "Test Data";
        MuleDescriptor descriptor = getTestDescriptor("orange", Orange.class.getName());

        MuleEvent event = (MuleEvent)getTestEvent(data, descriptor);

        assertEquals("Event data should equal " + data, data, event.getMessage().getPayload());
        assertEquals("Event data should equal " + data, data, event.getMessageAsString(null));
        assertEquals("Event data should equal " + data, data, event.getTransformedMessage());
        assertEquals("Event data should be a byte array 9 bytes in length", 9, event
            .getTransformedMessageAsBytes().length);

        assertEquals("Event data should be a byte array 9 bytes in length", 9,
            event.getMessageAsBytes().length);
        assertEquals("Event data should equal " + data, data, event.getSource());

        assertEquals("MuleBeanPropertiesRule", event.getMessage().getProperty("MuleBeanPropertiesRule",
            "MuleBeanPropertiesRule"));
        event.getMessage().setProperty("Test", "Test1");

        assertFalse(event.getMessage().getPropertyNames().isEmpty());
        assertEquals("bla2", event.getMessage().getProperty("bla2", "bla2"));
        assertEquals("Test1", event.getMessage().getProperty("Test"));
        assertEquals("Test1", event.getMessage().getProperty("Test", "bla2"));
        assertNotNull(event.getId());
    }

    public void testEventTransformer() throws Exception
    {
        String data = "Test Data";
        UMOEndpoint endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        endpoint.setTransformer(new TestEventTransformer());
        UMOEvent event = getTestEvent(data, endpoint);

        assertEquals("Event data should equal " + data, data, event.getMessage().getPayload());
        assertEquals("Event data should equal " + data, data, event.getMessageAsString(null));
        assertEquals("Event data should equal 'Transformed Test Data'", "Transformed Test Data", event
            .getTransformedMessage());
        assertEquals("Event data should be a byte array 28 bytes in length", 21, event
            .getTransformedMessageAsBytes().length);
    }

    public void testEventRewrite() throws Exception
    {
        String data = "Test Data";
        UMOEndpoint endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        endpoint.setTransformer(new TestEventTransformer());
        MuleEvent event = new MuleEvent(new MuleMessage(data), endpoint,
            getTestSession(getTestComponent(getTestDescriptor("apple", Apple.class.getName()))), true,
            new ResponseOutputStream(System.out));

        assertNotNull(event.getId());
        assertNotNull(event.getSession());
        assertNotNull(event.getEndpoint());
        assertNotNull(event.getOutputStream());
        assertNotNull(event.getMessage());
        assertEquals(data, event.getMessageAsString(null));

        UMOEvent event2 = new MuleEvent(new MuleMessage("New Data"), event);
        assertNotNull(event2.getId());
        assertEquals(event.getId(), event2.getId());
        assertNotNull(event2.getSession());
        assertNotNull(event2.getEndpoint());
        assertNotNull(event2.getOutputStream());
        assertNotNull(event2.getMessage());
        assertEquals("New Data", event2.getMessageAsString(null));

    }

    public void testProperties() throws Exception
    {
        UMOEvent prevEvent;
        Properties props;
        UMOMessage msg;
        UMOEndpoint endpoint;
        UMOEvent event;

        // nowhere
        prevEvent = getTestEvent("payload");
        props = new Properties();
        msg = new MuleMessage("payload", props);
        endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        props = new Properties();
        endpoint.setProperties(props);
        event = new MuleEvent(msg, endpoint, prevEvent.getComponent(), prevEvent);
        assertNull(event.getMessage().getProperty("prop"));

        // in previous event => previous event
        prevEvent.getMessage().setProperty("prop", "value0");
        event = new MuleEvent(msg, endpoint, prevEvent.getComponent(), prevEvent);
        assertEquals("value0", event.getMessage().getProperty("prop"));

        // TODO check if this fragment can be removed
        // in previous event + endpoint => endpoint
        // This doesn't apply now as the previous event properties will be the same
        // as the current event props
        // props = new Properties();
        // props.put("prop", "value2");
        // endpoint.setProperties(props);
        // event = new MuleEvent(msg, endpoint, prevEvent.getComponent(), prevEvent);
        // assertEquals("value2", event.getProperty("prop"));

        // in previous event + message => message
        props = new Properties();
        props.put("prop", "value1");
        msg = new MuleMessage("payload", props);
        endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        event = new MuleEvent(msg, endpoint, prevEvent.getComponent(), prevEvent);
        assertEquals("value1", event.getMessage().getProperty("prop"));

        // in previous event + endpoint + message => message
        props = new Properties();
        props.put("prop", "value1");
        msg = new MuleMessage("payload", props);

        Properties props2 = new Properties();
        props2.put("prop", "value2");
        endpoint.setProperties(props2);
        event = new MuleEvent(msg, endpoint, prevEvent.getComponent(), prevEvent);
        assertEquals("value1", event.getMessage().getProperty("prop"));

    }

    /**
     * See http://mule.mulesource.org/jira/browse/MULE-384 for details.
     */
    public void testNoPasswordNoNullPointerException() throws Exception
    {
        UMOEndpoint endpoint = getTestEndpoint("AuthTest", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        // provide the username, but not the password, as is the case for SMTP
        // cannot set SMTP endpoint type, because the module doesn't have this
        // dependency
        endpoint.setEndpointURI(new MuleEndpointURI("test://john.doe@xyz.fr"));
        UMOEvent event = getTestEvent(new Object(), endpoint);
        UMOCredentials credentials = event.getCredentials();
        assertNull("Credentials must not be created for endpoints without a password.", credentials);
    }

    public void testEventSerialization() throws Exception
    {
        UMOTransformer trans1 = new TestEventTransformer();
        trans1.setName("OptimusPrime");

        UMOTransformer trans2 = new TestEventTransformer();
        trans2.setName("Bumblebee");

        trans1.setNextTransformer(trans2);

        MuleManager.getInstance().registerTransformer(trans1);
        MuleManager.getInstance().registerTransformer(trans2);

        UMOEndpoint endpoint = getTestEndpoint("Test", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        endpoint.setTransformer(trans1);

        UMOEvent event = RequestContext.setEvent(getTestEvent("payload", endpoint));

        Serializable serialized = (Serializable)new SerializableToByteArray().transform(event);
        assertNotNull(serialized);

        // null out the endpoint transformer since we need to verify that it is set
        // correctly when the event is recreated
        // NOTE: unused, see note below
        // endpoint.setTransformer(null);

        UMOEvent deserialized = (UMOEvent)new ByteArrayToObject().transform(serialized);
        assertNotNull(deserialized);

        // NOTE: below we SHOULD be able to re-check that the exisiting endpoint's transformer
        // is != null, but the lookup in MuleManager is broken and returns a new one :( 
        UMOTransformer deserializedTransformer = deserialized.getEndpoint().getTransformer();
        assertEquals(trans1.getName(), deserializedTransformer.getName());
        assertEquals(trans2.getName(), deserializedTransformer.getNextTransformer().getName());
    }

    public static class TestEventTransformer extends AbstractTransformer
    {
        public Object doTransform(Object src, String encoding) throws TransformerException
        {
            return "Transformed Test Data";
        }
    }

}

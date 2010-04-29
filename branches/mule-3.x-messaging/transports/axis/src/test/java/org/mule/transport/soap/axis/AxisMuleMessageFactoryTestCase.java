/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap.axis;

import org.mule.api.MuleMessage;
import org.mule.api.transport.MuleMessageFactory;
import org.mule.transport.AbstractMuleMessageFactoryTestCase;
import org.mule.transport.soap.axis.mock.MockAxisEngine;
import org.mule.transport.soap.axis.mock.MockEngineConfiguration;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;

public class AxisMuleMessageFactoryTestCase extends AbstractMuleMessageFactoryTestCase
{
    private String xml = 
          "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
        + "<soap:Header>"
        + "</soap:Header>"
        + "<soap:Body>"
        + "<urn:doGoogleSearch xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:urn=\"urn:GoogleSearch\">"
        + "<key>1</key>"
        + "<q>a</q>"
        + "<start>0</start>"
        + "<maxResults>1</maxResults>"
        + "<filter>false</filter>"
        + "<restrict>a</restrict>"
        + "<safeSearch>true</safeSearch>"
        + "<lr>a</lr>"
        + "<ie>b</ie>"
        + "<oe>c</oe>"
        + "</urn:doGoogleSearch>"
        + "</soap:Body>"
        + "</soap:Envelope>";

    
    public AxisMuleMessageFactoryTestCase()
    {
        super();
        runUnsuppoprtedTransportMessageTest = false;
    }
    
    @Override
    protected MuleMessageFactory doCreateMuleMessageFactory()
    {
        return new AxisMuleMessageFactory(muleContext);
    }

    @Override
    protected Object getValidTransportMessage() throws Exception
    {
        return new Message(xml);
    }
    
    @Override
    public void testValidPayload() throws Exception
    {
        setupAxisMessageContext();
        super.testValidPayload();
    }

    public void _testMessageContextIsNotNull() throws Exception, NoSuchFieldException
    {
        setupAxisMessageContext();
        MuleMessageFactory factory = createMuleMessageFactory();
        MuleMessage muleMessage = factory.create(getValidTransportMessage(), encoding);
        assertEquals(TEST_MESSAGE, muleMessage.getPayload());
    }

    private void setupAxisMessageContext()
    {
        EngineConfiguration configuration = new MockEngineConfiguration();
        MockAxisEngine engine = new MockAxisEngine(configuration);
        MessageContext messageContext = new MessageContext(engine);
        MockAxisEngine.setCurrentMessageContext(messageContext);
        
        Message soapMessage = new Message(xml);
        messageContext.setMessage(soapMessage);        
    }
}

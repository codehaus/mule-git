/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.test.transformers;

import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.RequestContext;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.transformers.simple.ByteArrayToSerializable;
import org.mule.transformers.simple.SerializableToByteArray;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.UMOTransformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class SerializedUMOMessageTransformersTestCase extends AbstractTransformerTestCase
{
    private UMOMessage testObject = null;

    protected void doSetUp() throws Exception {
        RequestContext.setEvent(new MuleEvent(testObject, getTestEndpoint("test", "sender"), MuleTestUtils.getTestSession(), true));
    }

    protected void doTearDown() throws Exception {
        RequestContext.clear();
    }

    public SerializedUMOMessageTransformersTestCase() {
        Map props = new HashMap();
        props.put("object", new Apple());
        props.put("number", new Integer(1));
        props.put("string", "hello");
        testObject = new MuleMessage("test", props);
    }

    public UMOTransformer getTransformer() throws Exception
    {
        SerializableToByteArray trans = new SerializableToByteArray();
        trans.setSourceType(UMOMessage.class.getName());
        return trans;
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        return new ByteArrayToSerializable();
    }

    public Object getTestData()
    {
        return testObject;
    }

    public Object getResultData()
    {
        try {
            ByteArrayOutputStream bs = null;
            ObjectOutputStream os = null;

            bs = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bs);
            os.writeObject(testObject);
            os.flush();
            os.close();
            return bs.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean compareResults(Object src, Object result)
    {
        if (src == null && result == null) {
            return true;
        }
        if (src == null || result == null) {
            return false;
        }
        return Arrays.equals((byte[]) src, (byte[]) result);
    }

    public boolean compareRoundtripResults(Object src, Object result)
    {
        if (src == null && result == null) {
            return true;
        }
        if (src == null || result == null) {
            return false;
        }
        if(src instanceof UMOMessage && result instanceof UMOMessage) {
            return ((UMOMessage)src).getPayload().equals(((UMOMessage)result).getPayload()) &&
                 ((UMOMessage)src).getProperty("object").equals(((UMOMessage)result).getProperty("object")) &&
                 ((UMOMessage)src).getProperty("string").equals(((UMOMessage)result).getProperty("string")) &&
                 ((UMOMessage)src).getIntProperty("number", -1) == ((UMOMessage)result).getIntProperty("number", -2);
        } else {
            return false;
        }
    }
}

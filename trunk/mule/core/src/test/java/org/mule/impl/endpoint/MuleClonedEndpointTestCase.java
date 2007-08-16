/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.endpoint;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.transformers.simple.StringToByteArray;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.transformer.UMOTransformer;

public class MuleClonedEndpointTestCase extends AbstractMuleTestCase
{
    public void testResponseTransformersOnClonedEndpoint() throws Exception
    {
        MuleEndpointURI u1 = new MuleEndpointURI(
            "test://mule:secret@jabber.org:6666/ross@jabber.org");
        UMOTransformer trans = new StringToByteArray();

        UMOEndpoint endpoint = new MuleEndpoint("myendpoint", u1, null, null,
            UMOEndpoint.ENDPOINT_TYPE_RECEIVER, 1, null, null);
        endpoint.setResponseTransformer(trans);
        UMOEndpoint clone = (UMOEndpoint) endpoint.clone();
        assertNotNull(clone.getResponseTransformer());
        assertTrue(clone.getResponseTransformer() instanceof StringToByteArray);
        assertNotSame("Should've not referenced the original, but rather created a copy.",
                      trans, clone.getResponseTransformer());
    }
}

/*
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.test.config;

import junit.framework.TestCase;

import org.mule.config.builders.QuickConfigurationBuilder;
import org.mule.transformers.NoActionTransformer;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;

/**
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class EndpointTransformerTestCase extends TestCase {
	
	public void testTransformerProperty() throws UMOException {
		QuickConfigurationBuilder builder = new QuickConfigurationBuilder();
        builder.getManager().registerTransformer(new NoActionTransformer());
		UMOEndpoint endpoint = 
			builder.registerEndpoint("file:///tmp?transformers=NoActionTransformer", 
									 "test", /*inbound*/false);
		assertEquals("NoActionTransformer", endpoint.getTransformer().getName());
	}
}

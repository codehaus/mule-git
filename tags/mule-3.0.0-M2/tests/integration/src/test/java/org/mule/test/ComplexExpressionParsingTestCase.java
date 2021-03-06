/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.scripting.expression.GroovyExpressionEvaluator;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.transport.jdbc.JdbcConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexExpressionParsingTestCase extends AbstractMuleTestCase
{

    public void testComplexExpressionJdbcParsing() throws Exception
    {
        muleContext.getExpressionManager().registerEvaluator(new GroovyExpressionEvaluator());

        List parsedParams = new ArrayList();
        JdbcConnector c = new JdbcConnector();
        String result = c.parseStatement("#[groovy:payload[0]] - #[groovy:payload[1].toUpperCase()]", parsedParams);

        assertEquals("Wrong number of parsed parameters for a statement", 2, parsedParams.size());
        assertEquals("#[groovy:payload[0]]", parsedParams.get(0));
        assertEquals("#[groovy:payload[1].toUpperCase()]", parsedParams.get(1));

        // it's jdbc's PreparedStatement params here
        assertEquals("? - ?", result);
    }

}

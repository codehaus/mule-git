/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.annotations.converters;

import org.mule.registry.Registry;
import org.mule.umo.manager.ObjectNotFoundException;
import org.mule.util.StringUtils;

import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/** TODO */
public class PropertiesConverter
{
    public Properties convert(String properties, String delim) throws ObjectNotFoundException
    {
        if (StringUtils.isNotBlank(properties))
        {
            Properties props = new Properties();
            StringTokenizer st = new StringTokenizer(properties, delim);
            while (st.hasMoreTokens())
            {
                String key = st.nextToken().trim();
                int i = key.indexOf("=");
                if(i < 1) {
                    throw new IllegalArgumentException("Property string is malformed: " + properties);
                }
                String value = key.substring(i+1);
                key = key.substring(0, i);
                props.setProperty(key, value);
            }
            return props;
        }
       return null;
    }
}
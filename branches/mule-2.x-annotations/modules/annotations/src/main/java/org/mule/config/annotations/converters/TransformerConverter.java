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
import org.mule.transformers.TransformerUtils;
import org.mule.umo.manager.ObjectNotFoundException;
import org.mule.umo.transformer.UMOTransformer;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/** TODO */
public class TransformerConverter
{
    public List<UMOTransformer> convert(String names, String delim, Registry registry) throws ObjectNotFoundException
    {
        if (null != names)
        {
            List<UMOTransformer> transformers = new LinkedList<UMOTransformer>();
            StringTokenizer st = new StringTokenizer(names, delim);
            while (st.hasMoreTokens())
            {
                String key = st.nextToken().trim();
                UMOTransformer transformer = registry.lookupTransformer(key);

                if (transformer == null)
                {
                    throw new ObjectNotFoundException(key);
                }
                transformers.add(transformer);
            }
            return transformers;
        }
        else
        {
            return TransformerUtils.UNDEFINED;
        }

    }
}

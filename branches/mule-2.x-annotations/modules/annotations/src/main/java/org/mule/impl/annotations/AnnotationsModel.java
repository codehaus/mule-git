/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.annotations;

import org.mule.impl.model.resolvers.AnnotatedEntryPointResolver;
import org.mule.impl.model.resolvers.DefaultEntryPointResolverSet;
import org.mule.impl.model.seda.SedaModel;

/** TODO */
public class AnnotationsModel extends SedaModel
{
    public static final String DEFAULT_NAME = "main-annotated";

    public AnnotationsModel()
    {
        super();
        //Set this entrypoint respolver by default
        DefaultEntryPointResolverSet resolverSet = new DefaultEntryPointResolverSet();
        resolverSet.addEntryPointResolver(new AnnotatedEntryPointResolver());
        setEntryPointResolverSet(resolverSet);
        setName(DEFAULT_MODEL_NAME);
    }

}

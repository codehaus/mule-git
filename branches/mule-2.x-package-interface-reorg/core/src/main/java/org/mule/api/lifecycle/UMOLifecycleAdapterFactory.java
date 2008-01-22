/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.lifecycle;

import org.mule.api.UMOComponent;
import org.mule.api.UMOException;
import org.mule.api.model.UMOEntryPointResolverSet;

/** <code>UMOLifecycleAdapterFactory</code> TODO (document class) */
public interface UMOLifecycleAdapterFactory
{
    UMOLifecycleAdapter create(Object pojoService, UMOComponent component, UMOEntryPointResolverSet resolver)
        throws UMOException;
}

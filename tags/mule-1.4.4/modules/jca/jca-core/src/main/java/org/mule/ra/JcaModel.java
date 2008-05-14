/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ra;

import org.mule.impl.MuleDescriptor;
import org.mule.impl.model.AbstractModel;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.manager.UMOWorkManager;

/**
 * Creates a model suitable for Jca execution
 */
public class JcaModel extends AbstractModel
{
    public static final String JCA_MODEL_TYPE = "jca";
    /* JCA Model should execute work instance using container provided WorkManager */
    UMOWorkManager workManager;

    protected UMOComponent createComponent(UMODescriptor descriptor)
    {
        return new JcaComponent((MuleDescriptor) descriptor, workManager);
    }

    public String getType()
    {
        return JCA_MODEL_TYPE;
    }

    public UMOWorkManager getWorkManager()
    {
        return workManager;
    }

    public void setWorkManager(UMOWorkManager workManager)
    {
        this.workManager = workManager;
    }

}

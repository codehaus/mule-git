/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.internal.core.model;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.mule.ide.core.model.IMuleModelElement;

/**
 * Abstract base class for elements in the Mule model.
 */
public abstract class MuleModelElement implements IMuleModelElement {

    /** Indicates whether the config was resolved and loaded */
    private IStatus status = Status.OK_STATUS;

    /**
     * Sets the 'status' field.
     *
     * @param status The 'status' value.
     */
    protected void setStatus(IStatus status) {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleModelElement#getStatus()
     */
    public IStatus getStatus() {
        return status;
    }
}
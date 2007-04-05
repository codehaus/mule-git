/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.model;

import org.eclipse.core.runtime.IStatus;

/**
 * Common base interface for elements in the Mule IDE model.
 */
public interface IMuleModelElement {

    /**
     * Get the model this element belongs to.
     *
     * @return the model
     */
    public IMuleModel getMuleModel();

    /**
     * Refresh the given element and return a status indicating result.
     *
     * @return a status indicator
     */
    public IStatus refresh();

    /**
     * Get the status of the element.
     *
     * @return the status
     */
    public IStatus getStatus();

    /**
     * Get the label shown in views for the element.
     *
     * @return the label
     */
    public String getLabel();
}
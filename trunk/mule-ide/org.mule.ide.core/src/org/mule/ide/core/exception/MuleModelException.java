/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.exception;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Exception thrown when there is a problem with the Mule model.
 */
public class MuleModelException extends CoreException {

	private static final long serialVersionUID = -3743356049112147557L;

	public MuleModelException(IStatus status) {
        super(status);
    }
}
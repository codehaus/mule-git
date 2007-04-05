/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.launching;

import org.eclipse.debug.core.model.Breakpoint;

public class MuleBreakpoint extends Breakpoint {

	public final static String MODEL_IDENTIFIER = "org.mule.ide.launching.breakpoint"; 
	
	public MuleBreakpoint() {
		super();
	}

	public String getModelIdentifier() {
		return MODEL_IDENTIFIER;
	}

}

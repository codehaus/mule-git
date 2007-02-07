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

public class EndpointBreakpoint extends MuleBreakpoint {
	
	private boolean breakBefore;
	private boolean breakAfter;

	/**
	 * @return Returns the breakAfter.
	 */
	public boolean isBreakAfter() {
		return breakAfter;
	}
	/**
	 * @param breakAfter The breakAfter to set.
	 */
	public void setBreakAfter(boolean breakAfter) {
		this.breakAfter = breakAfter;
	}
	/**
	 * @return Returns the breakBefore.
	 */
	public boolean isBreakBefore() {
		return breakBefore;
	}
	/**
	 * @param breakBefore The breakBefore to set.
	 */
	public void setBreakBefore(boolean breakBefore) {
		this.breakBefore = breakBefore;
	}
}

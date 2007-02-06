/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.palette;

import org.eclipse.emf.ecore.EObject;
import org.mule.ide.config.mulemodel.MuleFactory;

public class RouteItem extends PaletteItem {

	public RouteItem(String name, String type) {
		super(null);
		setName(name);
		setType(type);
	}

	public boolean mayDropOn(EObject obj) {
		return false;
	}
	
	public void performDropOn(EObject obj, MuleFactory factory) {
	}
}

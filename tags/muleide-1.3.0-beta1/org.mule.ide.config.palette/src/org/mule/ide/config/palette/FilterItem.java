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
import org.mule.ide.config.mulemodel.Endpoint;
import org.mule.ide.config.mulemodel.GenericFilter;
import org.mule.ide.config.mulemodel.MuleConfig;
import org.mule.ide.config.mulemodel.MuleFactory;

public class FilterItem extends PaletteItem {

	public FilterItem(String className) {
		super(className);
	}

	public boolean mayDropOn(EObject obj) {
		while (obj != null && !(obj instanceof MuleConfig)) obj = obj.eContainer();		
		return obj instanceof Endpoint;
	}
	
	public void performDropOn(EObject obj, MuleFactory factory) {
		while (obj != null && !(obj instanceof MuleConfig)) obj = obj.eContainer();
		assert obj instanceof MuleConfig;
		if (obj instanceof MuleConfig) {
			Endpoint endpoint = (Endpoint) obj;
			if (endpoint.getFilter() == null) {
				GenericFilter genericFilter = factory.createGenericFilter();
				endpoint.setFilter(genericFilter);
			}
			
			//GenericFilter.setName(this.getName());
			//GenericFilter.setClassName(this.getName()); // Not supported in XML
			//GenericFilter.setComment("Dropped from the Mule palette on " + new Date());
			
			//cfg.getComponents().add(genericFilter);
		}
	}
}

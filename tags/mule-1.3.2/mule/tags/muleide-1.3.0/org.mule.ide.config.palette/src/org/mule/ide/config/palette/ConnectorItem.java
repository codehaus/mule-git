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

import java.util.Date;

import org.eclipse.emf.ecore.EObject;
import org.mule.ide.config.mulemodel.Connector;
import org.mule.ide.config.mulemodel.MuleConfig;
import org.mule.ide.config.mulemodel.MuleFactory;
import org.mule.ide.config.mulemodel.Router;

public class ConnectorItem extends PaletteItem {

	public ConnectorItem(String className) {
		super(className);
	}

	public boolean mayDropOn(EObject obj) {
		while (obj != null && !(obj instanceof MuleConfig)) obj = obj.eContainer();
		
		// We can only drop connectors on routers and in the global
		// connectors section.
		//
		// If we drop onto a Router, we will automatically create an 
		// endpoint
		System.out.println("ConnectorItem checking drop on " + obj.getClass().getName());
		if (obj instanceof Router) return true;
		else if (obj instanceof MuleConfig) return true;
		else return false;
	}
	
	public void performDropOn(EObject obj, MuleFactory factory) {
		if (obj instanceof MuleConfig) {
			if (obj instanceof MuleConfig) {
				System.out.println("Dropping connector on MuleConfig");
				MuleConfig cfg = (MuleConfig)obj;
				Connector connector = factory.createConnector();
				connector.setName(this.getName());
				connector.setClassName(this.getName()); // Not supported in XML
				connector.setComment("Dropped from the Mule palette on " + new Date());
				cfg.getConnectors().add(connector);
			} else if (obj instanceof Router) {
				//Router router = (Router) obj;
			}
		}
	}
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.config.mulemodel.presentation;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emfworkbench.integration.ProjectResourceSetEditImpl;

public class MuleResourceSetEditImpl extends ProjectResourceSetEditImpl {

	public MuleResourceSetEditImpl(IProject aProject) {
		super(aProject);
	}
  /**
   * Loads the given resource.
   * It is called by {@link #demandLoadHelper(Resource) demandLoadHelper(Resource)} 
   * to perform a demand load.
   * This implementation simply calls <code>resource.</code>{@link Resource#load(Map) load}({@link #getLoadOptions() getLoadOptions}()).
   * Clients may extend this as appropriate.
   * @param resource  a resource that isn't loaded.
   * @exception IOException if there are serious problems loading the resource.
   * @see #getResource(URI, boolean)
   * @see #demandLoadHelper(Resource)
   */
  protected void demandLoad(Resource resource) throws IOException
  {
	  TranslatorResource tr = (TranslatorResource)resource;
	  tr.accessForWrite();
	  super.demandLoad(resource);
  }
}

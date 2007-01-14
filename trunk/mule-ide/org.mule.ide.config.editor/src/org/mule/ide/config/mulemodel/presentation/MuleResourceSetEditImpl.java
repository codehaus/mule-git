package org.mule.ide.config.mulemodel.presentation;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.internal.emfworkbench.integration.ProjectResourceSetEditImpl;
import org.eclipse.wst.common.internal.emfworkbench.integration.ResourceSetWorkbenchEditSynchronizer;

public class MuleResourceSetEditImpl extends ProjectResourceSetEditImpl {

	public MuleResourceSetEditImpl(IProject aProject) {
		super(aProject);
		setSynchronizer(new ResourceSetWorkbenchEditSynchronizer(this, aProject));
	}
}

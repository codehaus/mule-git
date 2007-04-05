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



import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.sse.ui.internal.ISourceViewerActionBarContributor;
import org.eclipse.wst.xml.ui.internal.tabletree.SourceEditorActionBarContributor;


public class MuleMultiPageEditorActionBarContributor extends SourceEditorActionBarContributor {

	private boolean needsMultiInit = true;

	public MuleMultiPageEditorActionBarContributor() {
		super();
	}

	protected void initDesignViewerActionBarContributor(IActionBars actionBars) {
		super.initDesignViewerActionBarContributor(actionBars);

//		if (designViewerActionBarContributor != null)
//			if (designViewerActionBarContributor instanceof IDesignViewerActionBarContributor)
//				((IDesignViewerActionBarContributor) designViewerActionBarContributor).initViewerSpecificContributions(actionBars);
	}

	protected void activateDesignPage(IEditorPart activeEditor) {
		if (sourceViewerActionContributor != null && sourceViewerActionContributor instanceof ISourceViewerActionBarContributor) {
			// if design page is not really an IEditorPart, activeEditor ==
			// null, so pass in multiPageEditor instead (d282414)
			if (activeEditor == null) {
				sourceViewerActionContributor.setActiveEditor(multiPageEditor);
			} else {
				sourceViewerActionContributor.setActiveEditor(activeEditor);
			}
			((ISourceViewerActionBarContributor) sourceViewerActionContributor).setViewerSpecificContributionsEnabled(false);
		}

//		if (designViewerActionBarContributor != null && designViewerActionBarContributor instanceof IDesignViewerActionBarContributor) {
//			designViewerActionBarContributor.setActiveEditor(multiPageEditor);
//			((IDesignViewerActionBarContributor) designViewerActionBarContributor).setViewerSpecificContributionsEnabled(true);
//		}
	}

	protected void activateSourcePage(IEditorPart activeEditor) {
//		if (designViewerActionBarContributor != null && designViewerActionBarContributor instanceof IDesignViewerActionBarContributor) {
//			designViewerActionBarContributor.setActiveEditor(multiPageEditor);
//			((IDesignViewerActionBarContributor) designViewerActionBarContributor).setViewerSpecificContributionsEnabled(false);
//		}

		if (sourceViewerActionContributor != null && sourceViewerActionContributor instanceof ISourceViewerActionBarContributor) {
			sourceViewerActionContributor.setActiveEditor(activeEditor);
			((ISourceViewerActionBarContributor) sourceViewerActionContributor).setViewerSpecificContributionsEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#init(org.eclipse.ui.IActionBars)
	 */
	public void init(IActionBars actionBars) {
		super.init(actionBars);
		needsMultiInit = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IEditorPart targetEditor) {
		if (needsMultiInit) {
//			designViewerActionBarContributor = new XMLTableTreeActionBarContributor();
			initDesignViewerActionBarContributor(getActionBars());
			needsMultiInit = false;
		}
		super.setActiveEditor(targetEditor);
	}

}
/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.palette.views;

import org.mule.ide.config.palette.ComponentItem;
import org.mule.ide.config.palette.FilterItem;
import org.mule.ide.config.palette.FolderItem;

import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.util.LocalSelectionTransfer;

/**
 * @author Lajos
 *
 */
public class ViewDragListener extends DragSourceAdapter {
    protected TreeViewer treeViewer = null;

    public ViewDragListener(TreeViewer treeViewer) {
            this.treeViewer = treeViewer;
    }

    public void dragStart(DragSourceEvent event) {
        IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
        Object object = selection.getFirstElement();

        if (object instanceof FolderItem || selection.size() != 1) {
            event.doit = false;
        } else {
            LocalSelectionTransfer.getTransfer().setSelection(selection);
            event.doit = true;
        }
    }

    public void dragSetData(DragSourceEvent event) {
        IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
        Object object = selection.getFirstElement();

        if (object instanceof FilterItem) {
        	FilterItem component = (FilterItem)object;
            event.data = component.getName();
            System.out.println("Setting event.data to " + event.data);
        } else if (object instanceof ComponentItem) {
        	ComponentItem component = (ComponentItem)object;
            event.data = component.getName();
            System.out.println("Setting event.data to " + event.data);
        }
    }

    public void dragFinished(DragSourceEvent event) {
            LocalSelectionTransfer.getTransfer().setSelection(null);
    }
}

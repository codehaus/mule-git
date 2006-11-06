/**
 * 
 */
package org.mule.ide.prototype.palette.views;

import org.mule.ide.prototype.palette.ComponentItem;
import org.mule.ide.prototype.palette.FolderItem;

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

        if (object instanceof ComponentItem) {
        	ComponentItem component = (ComponentItem)object;
            event.data = component.getName();
            System.out.println("Setting event.data to " + event.data);
        }
    }

    public void dragFinished(DragSourceEvent event) {
            LocalSelectionTransfer.getTransfer().setSelection(null);
    }
}

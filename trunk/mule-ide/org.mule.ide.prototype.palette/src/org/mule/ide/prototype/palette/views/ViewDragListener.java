/**
 * 
 */
package org.mule.ide.prototype.palette.views;

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
    System.out.println("dragStart() selection = " + selection);
    Object object = selection.getFirstElement();
    System.out.println("dragStart() object = " + object);

    /*
    if (object instanceof MuleConfigCategory || selection.size() != 1) {
            event.doit = false;
    } else {
            LocalSelectionTransfer.getTransfer().setSelection(selection);
            event.doit = true;
    }
    */
    }

    public void dragSetData(DragSourceEvent event) {
            System.out.println("dragSetData is called");
            IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
    Object object = selection.getFirstElement();
    /*
    if (object instanceof MuleConfigComponent) {
            MuleConfigComponent component = (MuleConfigComponent)object;
            event.data = component;
    }
    */
    }

    public void dragFinished(DragSourceEvent event) {
            LocalSelectionTransfer.getTransfer().setSelection(null);
    }
}

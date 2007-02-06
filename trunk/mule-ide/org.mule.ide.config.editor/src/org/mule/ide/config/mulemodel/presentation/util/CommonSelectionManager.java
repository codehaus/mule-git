/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.mulemodel.presentation.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageSelectionProvider;

public class CommonSelectionManager extends MultiPageSelectionProvider implements ISelectionProvider, ISelectionChangedListener
{

  public CommonSelectionManager(MultiPageEditorPart multiPageEditor)
  {
    super(multiPageEditor);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
   */
  public void addSelectionChangedListener(ISelectionChangedListener listener)
  {
    listenerList.add(listener);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
   */
  public ISelection getSelection()
  {
    return currentSelection;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
   */
  public void removeSelectionChangedListener(ISelectionChangedListener listener)
  {
    listenerList.remove(listener);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
   */
  public void setSelection(ISelection selection)
  {
    setSelection(selection, this);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent event)
  {
    if (enableNotify)
    {
      setSelection(event.getSelection(), event.getSelectionProvider());
    }
  }

  
  protected List listenerList = new ArrayList();
  protected ISelection currentSelection;
  protected boolean enableNotify = true;

  public boolean getEnableNotify()
  {
    return enableNotify;
  }
  
  public void setSelection(ISelection selection, ISelectionProvider source)
  {  
    if (enableNotify)
    {
      currentSelection = selection;
      enableNotify = false;
      try
      {
        SelectionChangedEvent event = new SelectionChangedEvent(source, selection);
        List copyOfListenerList = new ArrayList(listenerList);
        for (Iterator i = copyOfListenerList.iterator(); i.hasNext(); )
        {
          ISelectionChangedListener listener = (ISelectionChangedListener)i.next();
          listener.selectionChanged(event);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        enableNotify = true;
      }
    }
  }
}

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

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
  private static final String BUNDLE_NAME = "org.eclipse.wst.xsd.ui.internal.adt.editor.messages"; //$NON-NLS-1$

  private Messages()
  {
  }

  static
  {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }
  public static String _UI_ACTION_SHOW_PROPERTIES;
  public static String _UI_ACTION_SET_AS_FOCUS;
  public static String _UI_ACTION_DELETE;
  public static String _UI_ACTION_ADD_FIELD;
  public static String _UI_ACTION_BROWSE;
  public static String _UI_ACTION_NEW;
  public static String _UI_ACTION_UPDATE_NAME;
  public static String _UI_ACTION_UPDATE_TYPE;
  public static String _UI_ACTION_UPDATE_ELEMENT_REFERENCE;
  public static String _UI_LABEL_DESIGN;
  public static String _UI_LABEL_SOURCE;
}

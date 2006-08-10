/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vfs.transformers;

/**
 * Created by IntelliJ IDEA.
 * User: Ian de Beer
 * Date: Jun 28, 2005
 * Time: 11:30:18 AM
 */
public class TextMessage {
  private String message;

  public TextMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

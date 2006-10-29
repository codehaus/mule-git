/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.wire;

import java.io.InputStream;
import java.io.OutputStream;

import org.mule.umo.UMOException;

/**
 * TODO
 */

public interface WireFormat
{
    public Object read(InputStream is) throws UMOException;

    public void write(OutputStream out, Object o) throws UMOException;
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.provider;

import org.mule.umo.UMOEvent;
import org.mule.umo.lifecycle.Disposable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <code>UMOMessageResource</code> represents the resource responsible for the 
 * generation of a message. It is currently only relevant for streaming messages,
 * wherein a resource associated with a message may have to be dealt with in some
 * way after the message is read.
 *
 * A UMOMessageResource currently is owned only by a StreamMessageAdapter.
 */

public interface UMOMessageResource extends Disposable
{
    // No methods - all we need is in Disposable
}

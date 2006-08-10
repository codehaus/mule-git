/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import java.util.Map;

import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;

public interface EventRouter {
	public UMOMessage sendEvent(String url, Object payload, Map messageProperties) throws UMOException;
}

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

public interface MessageService {

    public void generateMessage(String endpoint, Object payloadObject, Map messageProperties, boolean synchronous) throws Exception;
}

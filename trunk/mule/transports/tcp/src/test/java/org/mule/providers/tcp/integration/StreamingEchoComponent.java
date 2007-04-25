/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.integration;

import org.mule.impl.model.streaming.StreamingService;
import org.mule.umo.UMOEventContext;
import org.mule.util.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamingEchoComponent implements StreamingService
{
    public void call(InputStream in, OutputStream out, UMOEventContext context) throws Exception
    {
        IOUtils.copy(in, out);
    }

}

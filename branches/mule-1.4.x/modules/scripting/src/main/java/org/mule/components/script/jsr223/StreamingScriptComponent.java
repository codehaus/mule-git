/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.script.jsr223;

import org.mule.impl.model.streaming.StreamingService;
import org.mule.umo.UMOEventContext;

import java.io.InputStream;
import java.io.OutputStream;

import javax.script.Bindings;

/**
 * A JSR 223 Script streaming component. Allows any JSR 223 compliant script engines
 * such as JavaScript, Groovy or Rhino to be embedded as Mule components.
 */
public class StreamingScriptComponent extends AbstractScriptComponent implements StreamingService
{

    public void call(InputStream in, OutputStream out, UMOEventContext eventContext) throws Exception
    {
        Bindings bindings = this.getBindings();
        this.populateBindings(bindings, in, out, eventContext);
        this.runScript(bindings);
    }

    protected void populateBindings(Bindings namespace, InputStream in, OutputStream out,
        UMOEventContext context)
    {
        super.populateBindings(namespace, context);
        
        namespace.put("inputStream", in);
        namespace.put("outputStream", out);
    }

}

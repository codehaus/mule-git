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

import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;

import javax.script.Bindings;


/**
 * A JSR 223 Script component. Allows any JSR 223 compliant script engines such as
 * JavaScript, Groovy or Rhino to be embedded as Mule components.
 */
public class ScriptComponent extends AbstractScriptComponent implements Callable
{
    
    protected void populateBindings(Bindings namespace, UMOEventContext context)
    {
        super.populateBindings(namespace, context);
        namespace.put("result", new Object());
    }

    public Object onCall(UMOEventContext eventContext) throws Exception
    {
        Bindings bindings = this.getBindings();
        populateBindings(bindings, eventContext);
        Object result = runScript(bindings);
        if (result == null)
        {
            result = bindings.get("result");
        }
        return result;
    }

}

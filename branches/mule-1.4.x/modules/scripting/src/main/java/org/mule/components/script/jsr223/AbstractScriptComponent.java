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

import org.mule.MuleManager;
import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.util.MuleLogger;

import javax.script.Bindings;

public class AbstractScriptComponent extends Scriptable
{
    private Bindings bindings;

    public void initialise() throws InitialisationException
    {
        super.initialise();
        bindings = getScriptEngine().createBindings();
    }

    protected void populateBindings(Bindings namespace, UMOEventContext context)
    {
        namespace.put("eventContext", context);
        namespace.put("managementContext", MuleManager.getInstance());
        namespace.put("message", context.getMessage());
        namespace.put("descriptor", context.getComponentDescriptor());
        namespace.put("componentNamespace", this.bindings);
        namespace.put("log", new MuleLogger(logger));
    }

    public Bindings getBindings()
    {
        return bindings;
    }
}



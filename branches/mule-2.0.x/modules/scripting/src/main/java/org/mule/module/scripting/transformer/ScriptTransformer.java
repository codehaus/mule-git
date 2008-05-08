/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.scripting.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.mule.module.scripting.component.Scriptable;
import org.mule.transformer.AbstractMessageAwareTransformer;

import javax.script.Bindings;
import javax.script.ScriptException;

/**
 * Runs a script to perform transformation on an object.
 */
public class ScriptTransformer extends AbstractMessageAwareTransformer
{
    private Scriptable script;

    public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
    {
        Bindings bindings;
        if (script != null && script.getScriptEngine() != null)
        {
            bindings = script.getScriptEngine().createBindings();
        }
        else
        {
            throw new TransformerException(MessageFactory.createStaticMessage("Script has not been initialized"), this);
        }
        populateBindings(bindings, message);
        try
        {
            Object result = script.runScript(bindings);
            if (result == null)
            {
                result = bindings.get("result");
            }
            return result;
        }
        catch (ScriptException e)
        {
            throw new TransformerException(this, e);
        }
    }

    protected void populateBindings(Bindings namespace, MuleMessage message)
    {
        namespace.put("message", message);
        namespace.put("src", message.getPayload());
        namespace.put("correlationId", message.getCorrelationId());
        namespace.put("transformerNamespace", namespace);
        namespace.put("log", logger);
        namespace.put("result", new Object());
    }

    public Scriptable getScript()
    {
        return script;
    }

    public void setScript(Scriptable script)
    {
        this.script = script;
    }
}

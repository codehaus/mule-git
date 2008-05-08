/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.scripting.component;

import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.component.builder.AbstractMessageBuilder;
import org.mule.component.builder.MessageBuilderException;
import org.mule.config.i18n.MessageFactory;

import javax.script.Bindings;
import javax.script.ScriptException;

/**
 * A message builder service that can execute message building as a script.
 */
public class ScriptMessageBuilder extends AbstractMessageBuilder implements Initialisable
{
    private Scriptable script;
    private Bindings bindings;

    public void initialise() throws InitialisationException
    {
        if (script != null && script.getScriptEngine() != null)
        {
            bindings = script.getScriptEngine().createBindings();
        }
        else
        {
            throw new InitialisationException(MessageFactory.createStaticMessage("Script has not been initialized"), this);
        }
    }

    public Object buildMessage(MuleMessage request, MuleMessage response) throws MessageBuilderException
    {
        populateBindings(bindings, request, response);
        Object result;
        try
        {
            result = script.runScript(bindings);
        }
        catch (ScriptException e)
        {
            throw new MessageBuilderException(response, e);
        }
        if (result == null)
        {
            result = bindings.get("result");
        }
        if (result == null)
        {
            throw new IllegalArgumentException("A result payload must be returned from the groovy script");
        }
        return result;
    }

    protected void populateBindings(Bindings namespace, MuleMessage request, MuleMessage response)
    {
        namespace.put("request", request);
        namespace.put("response", response);
        namespace.put("service", service);
        namespace.put("componentNamespace", namespace);
        namespace.put("log", logger);
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

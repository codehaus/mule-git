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

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.TransformerException;
import org.mule.component.AbstractComponent;
import org.mule.config.i18n.MessageFactory;
import org.mule.util.MuleLogger;

import javax.script.Bindings;

/**
 * A Script service backed by a JSR-223 compliant script engine such as
 * Groovy, JavaScript, or Rhino.
 */
public class ScriptComponent extends AbstractComponent 
{
    private Scriptable script;
    private Bindings bindings;

    public void initialise() throws InitialisationException
    {
        super.initialise();
        if (script != null && script.getScriptEngine() != null)
        {
            bindings = script.getScriptEngine().createBindings();
        }
        else
        {
            throw new InitialisationException(MessageFactory.createStaticMessage("Script has not been initialized"), this);
        }
    }

    //@Override
    protected MuleMessage doOnCall(MuleEvent event) throws Exception
    {
        populateBindings(bindings, event);
        Object result = script.runScript(bindings);
        if (result == null)
        {
            result = bindings.get("result");
        }
        return new DefaultMuleMessage(result);
    }

    protected void populateBindings(Bindings namespace, MuleEvent event)
    {
        namespace.put("muleContext", event.getMuleContext());
        try
        {
            namespace.put("message", event.transformMessage());
        }
        catch (TransformerException e)
        {
            logger.warn(e);
        }
        namespace.put("id", event.getId());
        namespace.put("service", event.getService());
        namespace.put("log", new MuleLogger(logger));
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

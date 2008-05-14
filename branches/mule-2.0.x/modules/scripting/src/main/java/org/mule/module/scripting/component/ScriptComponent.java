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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.script.Bindings;

/**
 * A Script service backed by a JSR-223 compliant script engine such as
 * Groovy, JavaScript, or Rhino.
 */
public class ScriptComponent extends AbstractComponent 
{
    private Scriptable script;
    private Bindings bindings;
    private Properties properties;

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
        if (properties != null)
        {
            bindings.putAll((Map) properties);
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
        // Set the most important script variables: 
        //   "message" = incoming message
        //   "result" = outgoing result
        try
        {
            namespace.put("message", event.transformMessage());
        }
        catch (TransformerException e)
        {
            logger.warn(e);
        }
        namespace.put("result", new Object());

        // Set any message properties as variables for the script.
        String propertyName;
        for (Iterator iterator = event.getMessage().getPropertyNames().iterator(); iterator.hasNext();)
        {
            propertyName = (String)iterator.next();
            namespace.put(propertyName, event.getMessage().getProperty(propertyName));
        }

        // Set a few other misc. variables for the script.
        namespace.put("muleContext", event.getMuleContext());
        namespace.put("id", event.getId());
        namespace.put("service", event.getService());
        namespace.put("log", new MuleLogger(logger));
    }

    public Scriptable getScript()
    {
        return script;
    }

    public void setScript(Scriptable script)
    {
        this.script = script;
    }

    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }
}

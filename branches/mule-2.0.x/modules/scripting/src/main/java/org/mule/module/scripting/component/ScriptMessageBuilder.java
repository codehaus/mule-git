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

import javax.script.ScriptException;

/**
 * A message builder service that can execute message building as a script.
 */
public class ScriptMessageBuilder extends AbstractMessageBuilder implements Initialisable
{
    private Scriptable script;

    public void initialise() throws InitialisationException
    {
        // nothing to do
    }

    public Object buildMessage(MuleMessage request, MuleMessage response) throws MessageBuilderException
    {
        Object result;
        try
        {
            script.populateBindings(request, response);
            result = script.runScript();
        }
        catch (ScriptException e)
        {
            throw new MessageBuilderException(response, e);
        }

        if (result == null)
        {
            throw new IllegalArgumentException("A result payload must be returned from the groovy script");
        }
        return result;
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

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.scripting.config;

import org.mule.config.spring.parsers.assembly.BeanAssembler;
import org.mule.config.spring.parsers.generic.OptionalChildDefinitionParser;
import org.mule.module.scripting.component.Scriptable;
import org.mule.util.StringUtils;

import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ScriptDefinitionParser extends OptionalChildDefinitionParser
{
    public ScriptDefinitionParser()
    {
        super("script", Scriptable.class);
        addIgnored("name");
        addAlias("engine", "scriptEngineName");
        addAlias("file", "scriptFile");        
    }

    protected void postProcess(ParserContext context, BeanAssembler assembler, Element element)
    {
        Node node = element.getFirstChild();
        if (node != null)
        {
            String value = node.getNodeValue();
            if (!StringUtils.isBlank(value))
            {
                assembler.getBean().addPropertyValue("scriptText", value);
            }
        }
        super.postProcess(context, assembler, element);
    }
}



package org.mule.module.sxc;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.collection.ChildMapEntryDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.specific.RouterDefinitionParser;

public class SxcNamespaceHandler extends AbstractMuleNamespaceHandler
{
    public void init()
    {        
    	registerBeanDefinitionParser("filtering-router", 
    			new RouterDefinitionParser(SxcFilteringOutboundRouter.class)	);
    	 registerBeanDefinitionParser("namespace", 
    	     new ChildMapEntryDefinitionParser("namespaces", "prefix", "uri"));
    	 
        registerBeanDefinitionParser("filter", 
        		new ChildDefinitionParser("filter", SxcFilter.class));
    }
}
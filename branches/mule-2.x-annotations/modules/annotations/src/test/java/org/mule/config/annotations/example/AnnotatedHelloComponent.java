/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.annotations.example;

import org.mule.config.annotations.EndpointBinding;
import org.mule.config.annotations.InboundEndpoint;
import org.mule.config.annotations.Service;

import java.util.Locale;

/**
 * This annotate service provides all the details Mule needs to configure a service without any
 * additional configuration!
 *
 * TODO RM*: Note that this is just a prototype so I have added support for filters or transformers
 */
@Service(entryPoint = "hello", name = "helloService")
@InboundEndpoint(endpoint = "${hello.endpoint}", synchronous = true)
public class AnnotatedHelloComponent
{
    @EndpointBinding(endpoint = "${greeter.endpoint}", interfaceMethod = "getGreeting")
    private LanguageService languageService;

    public LanguageService getLanguageService()
    {
        return languageService;
    }

    public void setLanguageService(LanguageService languageService)
    {
        this.languageService = languageService;
    }

    public String hello(String name)
    {
        String greeting = languageService.getGreeting(Locale.getDefault());
        return greeting + " " + name;
    }
}

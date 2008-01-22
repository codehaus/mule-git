/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.specific;

import org.mule.config.spring.parsers.collection.ChildMapEntryDefinitionParser;
import org.mule.config.spring.parsers.processors.CheckExclusiveAttributes;
import org.mule.config.spring.parsers.processors.CheckRequiredAttributes;
import org.mule.config.spring.parsers.PreProcessor;
import org.mule.config.spring.parsers.assembly.configuration.PropertyConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class NotificationDefinitionParser extends ChildMapEntryDefinitionParser
{

    public static final Map EVENT_MAP;
    public static final Map INTERFACE_MAP;
    public static final String INTERFACE = "interface";
    public static final String INTERFACE_CLASS = "interface-class";
    public static final String EVENT = "event";
    public static final String EVENT_CLASS = "event-class";
    public static final String[][] INTERFACE_ATTRIBUTES =
            new String[][]{new String[]{INTERFACE}, new String[]{INTERFACE_CLASS}};
    public static final String[][] EVENT_ATTRIBUTES =
            new String[][]{new String[]{EVENT}, new String[]{EVENT_CLASS}};
    public static final String[][] ALL_ATTRIBUTES =
            new String[][]{
                    new String[]{EVENT}, new String[]{EVENT_CLASS},
                    new String[]{INTERFACE}, new String[]{INTERFACE_CLASS}};

    static
    {
        EVENT_MAP = new HashMap();
        EVENT_MAP.put("MANAGER", org.mule.internal.notifications.ManagerNotification.class.getName());
        EVENT_MAP.put("MODEL", org.mule.internal.notifications.ModelNotification.class.getName());
        EVENT_MAP.put("COMPONENT", org.mule.internal.notifications.ComponentNotification.class.getName());
        EVENT_MAP.put("SECURITY", org.mule.internal.notifications.SecurityNotification.class.getName());
        EVENT_MAP.put("MANAGEMENT", org.mule.internal.notifications.ManagementNotification.class.getName());
        EVENT_MAP.put("ADMIN", org.mule.internal.notifications.AdminNotification.class.getName());
        EVENT_MAP.put("CONNECTION", org.mule.internal.notifications.ConnectionNotification.class.getName());
        EVENT_MAP.put("REGISTRY", org.mule.internal.notifications.RegistryNotification.class.getName());
        EVENT_MAP.put("CUSTOM", org.mule.internal.notifications.CustomNotification.class.getName());
        EVENT_MAP.put("MESSAGE", org.mule.internal.notifications.MessageNotification.class.getName());
        EVENT_MAP.put("EXCEPTION", org.mule.internal.notifications.ExceptionNotification.class.getName());
        EVENT_MAP.put("TRANSACTION", org.mule.internal.notifications.TransactionNotification.class.getName());

        INTERFACE_MAP = new HashMap();
        INTERFACE_MAP.put("MANAGER", org.mule.api.context.notification.ManagerNotificationListener.class.getName());
        INTERFACE_MAP.put("MODEL", org.mule.api.context.notification.ModelNotificationListener.class.getName());
        INTERFACE_MAP.put("COMPONENT", org.mule.api.context.notification.ComponentNotificationListener.class.getName());
        INTERFACE_MAP.put("SECURITY", org.mule.api.context.notification.SecurityNotificationListener.class.getName());
        INTERFACE_MAP.put("MANAGEMENT", org.mule.api.context.notification.ManagementNotificationListener.class.getName());
        INTERFACE_MAP.put("ADMIN", org.mule.api.context.notification.AdminNotificationListener.class.getName());
        INTERFACE_MAP.put("CONNECTION", org.mule.api.context.notification.ConnectionNotificationListener.class.getName());
        INTERFACE_MAP.put("REGISTRY", org.mule.api.context.notification.RegistryNotificationListener.class.getName());
        INTERFACE_MAP.put("CUSTOM", org.mule.api.context.notification.CustomNotificationListener.class.getName());
        INTERFACE_MAP.put("MESSAGE", org.mule.api.context.notification.MessageNotificationListener.class.getName());
        INTERFACE_MAP.put("EXCEPTION", org.mule.api.context.notification.ExceptionNotificationListener.class.getName());
        INTERFACE_MAP.put("TRANSACTION", org.mule.api.context.notification.TransactionNotificationListener.class.getName());
    }

    public NotificationDefinitionParser()
    {
        super("interfaceToType", INTERFACE_CLASS, EVENT_CLASS);
        addMapping(EVENT, EVENT_MAP);
        addAlias(EVENT, VALUE);
        addMapping(INTERFACE, INTERFACE_MAP);
        addAlias(INTERFACE, KEY);
        registerPreProcessor(new CheckExclusiveAttributes(INTERFACE_ATTRIBUTES));
        registerPreProcessor(new CheckExclusiveAttributes(EVENT_ATTRIBUTES));
        registerPreProcessor(new CheckRequiredAttributes(INTERFACE_ATTRIBUTES));
        registerPreProcessor(new CheckRequiredAttributes(EVENT_ATTRIBUTES));
        registerPreProcessor(new SetDefaults());
    }

    /**
     * If only one of event or interface is set, use it as default for the other
     */
    private class SetDefaults implements PreProcessor
    {

        public void preProcess(PropertyConfiguration config, Element element)
        {
            copy(element, INTERFACE, EVENT, EVENT_CLASS);
            copy(element, EVENT, INTERFACE, INTERFACE_CLASS);
        }

        private void copy(Element element, String from, String to, String blocker)
        {
            if (element.hasAttribute(from) && !element.hasAttribute(to) && !element.hasAttribute(blocker))
            {
                element.setAttribute(to, element.getAttribute(from));
            }
        }

    }

}

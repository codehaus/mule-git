/*
 * $Id: LocaleMessage.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore;

import org.mule.config.i18n.MessageFactory;

public class LocaleMessage extends MessageFactory
{
private static final String BUNDLE_PATH = "messages.bookstore-messages";
    
    public static String getWelcomeMessage()
    {
        return getString(BUNDLE_PATH, 1);
    }

    public static String getMenuOption1()
    {
        return getString(BUNDLE_PATH, 2);
    }

    public static String getMenuOption2()
    {
        return getString(BUNDLE_PATH, 3);
    }
    
    public static String getMenuOption3()
    {
        return getString(BUNDLE_PATH, 4);
    }
    
    public static String getMenuOption4()
    {
        return getString(BUNDLE_PATH, 5);
    }
    
    public static String getMenuOptionQuit()
    {
        return getString(BUNDLE_PATH, 6);
    }

    public static String getMenuPromptMessage()
    {
        return getString(BUNDLE_PATH, 7);
    }

    public static String getGoodbyeMessage()
    {
        return getString(BUNDLE_PATH, 8);
    }

    public static String getMenuErrorMessage()
    {
        return getString(BUNDLE_PATH, 9);
    }
    
    public static String getBookTitlePrompt()
    {
        return getString(BUNDLE_PATH, 10);
    }
    
    public static String getAuthorNamePrompt()
    {
        return getString(BUNDLE_PATH, 11);
    }
    
    public static String getAddBooksMessagePrompt()
    {
        return getString(BUNDLE_PATH, 12);
    }
    
    public static String getOrderWelcomeMessage()
    {
        return getString(BUNDLE_PATH, 13);
    }
    
    public static String getBookIdPrompt()
    {
        return getString(BUNDLE_PATH, 14);
    }
    
    public static String getHomeAddressPrompt()
    {
        return getString(BUNDLE_PATH, 15);
    }
    
    public static String getEmailAddressPrompt()
    {
        return getString(BUNDLE_PATH, 16);
    }
}

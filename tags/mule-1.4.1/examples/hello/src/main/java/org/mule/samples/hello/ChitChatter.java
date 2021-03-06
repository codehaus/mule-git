/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.samples.hello;

import org.mule.config.i18n.Message;

/**
 * <code>ChitChatter</code> TODO (document class)
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class ChitChatter
{
    private String chitchat = "";

    public ChitChatter()
    {
        chitchat = LocaleMessage.getGreetingPart2();
    }

    public void chat(ChatString string)
    {
        string.append(chitchat);
    }

}

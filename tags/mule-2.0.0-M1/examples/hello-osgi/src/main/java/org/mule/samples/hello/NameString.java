/*
 * $Id: NameString.java 3650 2006-10-24 10:26:34 +0000 (Tue, 24 Oct 2006) holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.samples.hello;

import java.io.Serializable;

/**
 * <code>NameString</code> TODO (document class)
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 3650 $
 */
public class NameString implements Serializable
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 7010138636008560022L;

    private String name;
    private String greeting;

    public NameString(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns the greeting.
     */
    public String getGreeting()
    {
        return greeting;
    }

    /**
     * @param greeting The greeting to set.
     */
    public void setGreeting(String greeting)
    {
        this.greeting = greeting;
    }

}

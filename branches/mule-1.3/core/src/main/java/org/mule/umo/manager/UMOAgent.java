/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.manager;

import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Lifecycle;

/**
 * <code>UMOAgent</code> is a server plugin that can be initialised, started and
 * destroyed along with the UMOManager itself. Agents can initialise or bind to
 * external services such as a Jmx server.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public interface UMOAgent extends Lifecycle, Initialisable
{
    /**
     * Gets the name of this agent
     * 
     * @return the agent name
     */
    String getName();

    /**
     * Sets the name of this agent
     * 
     * @param name the name of the agent
     */
    void setName(String name);

    /**
     * Should be a 1 line description of the agent
     * 
     * @return
     */
    String getDescription();

    void registered();

    void unregistered();
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.lifecycle;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.i18n.CoreMessages;

/**
 * <code>AlreadyInitialisedException</code> is thrown when a service or connector
 * has already been initialised.
 */

public class AlreadyInitialisedException extends InitialisationException
{
    /** Serial version */
    private static final long serialVersionUID = 3121894155097428317L;

    /**
     * @param object the object that has been initialised and cannot be initialised
     *               again
     */
    public AlreadyInitialisedException(String name, Initialisable object)
    {
        super(CoreMessages.objectAlreadyInitialised(name), object);
    }
}

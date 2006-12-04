/*
 * $Id: 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry.store;

import org.mule.registry.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The PersistenceManager handles the persistence of RegistryStores
 */
public class PersistenceManager
{
    public PersistenceManager()
    {
    }

    class PersistenceThread extends Thread
    {
        public PersistenceThread()
        {
            super();
        }

        public void run()
        {
        }
    }
}


/*
 * $Id: PersistenceManager.java 3649 2006-10-24 10:09:08Z holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.persistence;

import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.Disposable;

/**
 * <code>PersistenceManager</code> is the interface for any object that
 * can persist stuff. Right now, it is used for the registry. For example,
 * a RegistryPersistenceManager will get data from the RegistryStore, via
 * XStream, and persist it to a file or xml database.
 *
 * In the future, we might have different kinds of persistence managers.
 * 
 * @author 
 * @version $Revision: 3649 $
 */
public interface PersistenceManager extends Initialisable, Disposable
{
    /*
     * Temporary method!
     * Call the manager to schedule the persistence
     */
    public void requestPersistence(Persistable source);
}

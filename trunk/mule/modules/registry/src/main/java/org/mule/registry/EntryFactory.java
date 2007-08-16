/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry;

/**
 * TODO document
 */
public interface EntryFactory
{

    public RegistryComponent createComponent(String name, ComponentType type, Registry registry);

    public RegistryDescriptor createDescriptor();

    public Assembly createAssembly();
}

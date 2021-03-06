/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.security;

import org.mule.umo.UMOEncryptionStrategy;
import org.mule.umo.lifecycle.Initialisable;

import java.util.Collection;

/**
 * <code>UMOSecurityManager</code> is responsible for managing one or more
 * security providers.
 */

public interface UMOSecurityManager extends Initialisable
{
    
    UMOAuthentication authenticate(UMOAuthentication authentication)
        throws SecurityException, SecurityProviderNotFoundException;

    void addProvider(UMOSecurityProvider provider);

    UMOSecurityProvider getProvider(String name);

    UMOSecurityProvider removeProvider(String name);

    Collection getProviders();

    void setProviders(Collection providers);

    UMOSecurityContext createSecurityContext(UMOAuthentication authentication)
        throws UnknownAuthenticationTypeException;

    UMOEncryptionStrategy getEncryptionStrategy(String name);

    void addEncryptionStrategy(UMOEncryptionStrategy strategy);

    UMOEncryptionStrategy removeEncryptionStrategy(String name);

    Collection getEncryptionStrategies();

    void setEncryptionStrategies(Collection strategies);

}

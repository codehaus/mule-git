/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.umo.security.TlsPropertiesSocketFactory;

import javax.net.SocketFactory;

public class ImapsSocketFactory extends TlsPropertiesSocketFactory
{

    public static final String MULE_IMAPS_NAMESPACE = "mule.email.imaps";

    public ImapsSocketFactory()
    {
        super(true, MULE_IMAPS_NAMESPACE);
    }
    
    public static SocketFactory getDefault() 
    {
        return new ImapsSocketFactory();
    }

}



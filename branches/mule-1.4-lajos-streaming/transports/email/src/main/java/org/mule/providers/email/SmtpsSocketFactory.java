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

public class SmtpsSocketFactory extends TlsPropertiesSocketFactory
{
    
    public static final String MULE_SMTPS_NAMESPACE = "mule.email.smtps";

    public SmtpsSocketFactory()
    {
        super(true, MULE_SMTPS_NAMESPACE);
    }
    
    public static SocketFactory getDefault() 
    {
        return new SmtpsSocketFactory();
    }

}



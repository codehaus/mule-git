/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.jaas;

import org.mule.umo.security.UMOAuthentication;
import org.mule.umo.security.UMOSecurityContext;

public class JaasSecurityContext implements UMOSecurityContext
{

    private JaasAuthentication authentication;

    /**
     * Constructor for the class
     * 
     * @param authentication
     */
    public JaasSecurityContext(JaasAuthentication authentication)
    {
        this.authentication = authentication;
    }

    /**
     * Returns the authentication
     * 
     * @return authentication
     */
    public final UMOAuthentication getAuthentication()
    {
        return authentication;
    }

    /**
     * Sets the Authentication
     * 
     * @param authentication
     */
    public final void setAuthentication(UMOAuthentication authentication)
    {
        this.authentication = (JaasAuthentication) authentication;
    }
}

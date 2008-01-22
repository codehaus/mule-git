/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.pgp;

import org.mule.api.security.MuleAuthentication;
import org.mule.api.security.SecurityContext;

public class PGPSecurityContext implements SecurityContext
{
    private volatile PGPAuthentication authentication;

    public PGPSecurityContext(PGPAuthentication authentication)
    {
        this.authentication = authentication;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.SecurityContext#setAuthentication(org.mule.api.security.Authentication)
     */
    public void setAuthentication(MuleAuthentication authentication)
    {
        this.authentication = (PGPAuthentication)authentication;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.SecurityContext#getAuthentication()
     */
    public MuleAuthentication getAuthentication()
    {
        return authentication;
    }

}

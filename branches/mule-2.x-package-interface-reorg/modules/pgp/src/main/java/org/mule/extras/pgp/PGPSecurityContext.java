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

import org.mule.api.security.UMOAuthentication;
import org.mule.api.security.UMOSecurityContext;

public class PGPSecurityContext implements UMOSecurityContext
{
    private volatile PGPAuthentication authentication;

    public PGPSecurityContext(PGPAuthentication authentication)
    {
        this.authentication = authentication;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityContext#setAuthentication(org.mule.api.security.UMOAuthentication)
     */
    public void setAuthentication(UMOAuthentication authentication)
    {
        this.authentication = (PGPAuthentication)authentication;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityContext#getAuthentication()
     */
    public UMOAuthentication getAuthentication()
    {
        return authentication;
    }

}

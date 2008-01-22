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

import org.mule.api.UMOEvent;
import org.mule.api.security.UMOCredentialsAccessor;

public class FakeCredentialAccessor implements UMOCredentialsAccessor
{

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOCredentialsAccessor#getCredentials(org.mule.api.UMOEvent)
     */
    public Object getCredentials(UMOEvent event)
    {
        return "Mule client <mule_client@mule.com>";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOCredentialsAccessor#setCredentials(org.mule.api.UMOEvent,
     *      java.lang.Object)
     */
    public void setCredentials(UMOEvent event, Object credentials)
    {
        // dummy
    }

}

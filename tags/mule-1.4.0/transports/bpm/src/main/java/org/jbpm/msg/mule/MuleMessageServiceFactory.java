/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.jbpm.msg.mule;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

public class MuleMessageServiceFactory implements ServiceFactory
{
    private static final long serialVersionUID = 1L;

    public Service openService()
    {
        return new MuleMessageService();
    }

    public void close()
    {
        // no-op
    }

}

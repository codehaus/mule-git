/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.mulemq;

import org.mule.transport.jms.JmsConstants;

public class MuleMQXAJmsConnector extends MuleMQJmsConnector
{

    public static final String MULEMQ_XA_CONNECTION_FACTORY_CLASS = "com.pcbsys.nirvana.nJMS.XAConnectionFactoryImpl";

    public boolean supportJms102bSpec = true;

    @Override
    protected String getMuleMQFactoryClass()
    {
        return MULEMQ_XA_CONNECTION_FACTORY_CLASS;
    }

    @Override
    public boolean isSupportJms102bSpec()
    {
        return supportJms102bSpec;
    }

    @Override
    public void setSupportJms102bSpec(boolean supportJms102bSpec)
    {
        this.supportJms102bSpec = supportJms102bSpec;
    }

}

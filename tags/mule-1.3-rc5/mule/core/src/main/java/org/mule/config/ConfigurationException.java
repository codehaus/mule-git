/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config;

import org.mule.MuleException;
import org.mule.config.i18n.Message;

/**
 * <code>ConfigurationException</code> TODO (document class)
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class ConfigurationException extends MuleException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 3658822340943734960L;

    public ConfigurationException(Message message)
    {
        super(message);
    }

    public ConfigurationException(Message message, Throwable cause)
    {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause)
    {
        super(cause);
    }
}

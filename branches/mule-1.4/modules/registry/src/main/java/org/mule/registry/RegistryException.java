/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;
import org.mule.umo.UMOException;

/**
 * Base Registry exception thrown when reading or writing to the registry
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class RegistryException extends UMOException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 7362073316934744362L;

    public RegistryException(Message message)
    {
        super(message);
    }

    public RegistryException(Message message, Throwable throwable)
    {
        super(message, throwable);
    }

    public RegistryException(Throwable throwable)
    {
        super(throwable);
    }

    // TODO remove
    public RegistryException(String message)
    {
        super(MessageFactory.createStaticMessage(message));
    }

    public RegistryException(String message, Throwable throwable)
    {
        super(MessageFactory.createStaticMessage(message), throwable);
    }
}

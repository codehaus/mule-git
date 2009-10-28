/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.model.SessionException;
import org.mule.api.transport.SessionHandler;
import org.mule.config.i18n.MessageFactory;
import org.mule.util.IOUtils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default session handler used to store and retrieve session information on an
 * event. The DefaultMuleSession information is stored as a header on the message (does not
 * support Tcp, Udp, etc. unless the MuleMessage object is serialised across the
 * wire). The session is stored in the "MULE_SESSION" property as String key/value
 * pairs that are Base64 encoded, for example:
 * ID=dfokokdf-3ek3oke-dkfokd;MySessionProp1=Value1;MySessionProp2=Value2
 */
public class MuleSessionHandler implements SessionHandler
{
    protected transient Log logger = LogFactory.getLog(getClass());

    public MuleSession retrieveSessionInfoFromMessage(MuleMessage message) throws MuleException
    {
        MuleSession session = null;
        byte[] serializedSession = (byte[]) message.removeProperty(MuleProperties.MULE_SESSION_PROPERTY);

        if (serializedSession != null)
        {
            try
            {
                session = (MuleSession) IOUtils.deserialize(serializedSession);
            }
            catch (IOException e)
            {
                throw new SessionException(MessageFactory.createStaticMessage("Unable to deserialize MuleSession"), e);
            }
            catch (ClassNotFoundException e)
            {
                throw new SessionException(MessageFactory.createStaticMessage("Unable to deserialize MuleSession"), e);
            }
        }
        return session;
    }

    /**
     * @deprecated Use retrieveSessionInfoFromMessage(MuleMessage message) instead
     */
    public void retrieveSessionInfoFromMessage(MuleMessage message, MuleSession session) throws MuleException
    {
        session = retrieveSessionInfoFromMessage(message);
    }

    public void storeSessionInfoToMessage(MuleSession session, MuleMessage message) throws MuleException
    {
        byte[] serializedSession;
        try
        {
            serializedSession = IOUtils.serialize(session);
        }
        catch (Exception e)
        {
            throw new SessionException(MessageFactory.createStaticMessage("Unable to serialize MuleSession"), e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Adding serialized Session header to message: " + serializedSession);
        }
        message.setProperty(MuleProperties.MULE_SESSION_PROPERTY, serializedSession);
    }
    
    /**
     * @deprecated This method is no longer needed and will be removed in the next major release
     */
    public String getSessionIDKey()
    {
        return "ID";
    }
}

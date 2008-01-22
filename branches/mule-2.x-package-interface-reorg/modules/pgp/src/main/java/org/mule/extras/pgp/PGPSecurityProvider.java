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

import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.security.SecurityException;
import org.mule.api.security.UMOAuthentication;
import org.mule.api.security.UMOSecurityContext;
import org.mule.api.security.UMOSecurityContextFactory;
import org.mule.api.security.UMOSecurityProvider;
import org.mule.api.security.UnauthorisedException;
import org.mule.api.security.UnknownAuthenticationTypeException;
import org.mule.extras.pgp.i18n.PGPMessages;
import org.mule.imple.config.i18n.CoreMessages;

import cryptix.message.Message;
import cryptix.message.MessageException;
import cryptix.message.SignedMessage;
import cryptix.pki.KeyBundle;

public class PGPSecurityProvider implements UMOSecurityProvider
{
    private String name = "PGPSecurityProvider";

    private PGPKeyRing keyManager;

    private UMOSecurityContextFactory factory;

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityProvider#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityProvider#getName()
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityProvider#authenticate(org.mule.api.security.UMOAuthentication)
     */
    public UMOAuthentication authenticate(UMOAuthentication authentication) throws SecurityException
    {
        PGPAuthentication auth = (PGPAuthentication) authentication;

        String userId = (String) auth.getPrincipal();

        if (userId == null)
        {
            throw new UnauthorisedException(CoreMessages.objectIsNull("UserId"));
        }

        KeyBundle userKeyBundle = keyManager.getKeyBundle(userId);

        if (userKeyBundle == null)
        {
            throw new UnauthorisedException(PGPMessages.noPublicKeyForUser(userId));
        }

        Message msg = (Message) auth.getCredentials();

        if (!((msg != null) && msg instanceof SignedMessage))
        {
            throw new UnauthorisedException(PGPMessages.noSignedMessageFound());
        }

        try
        {
            if (!((SignedMessage) msg).verify(userKeyBundle))
            {
                throw new UnauthorisedException(PGPMessages.invalidSignature());
            }
        }
        catch (MessageException e)
        {
            throw new UnauthorisedException(PGPMessages.errorVerifySignature(), e);
        }

        auth.setAuthenticated(true);
        auth.setDetails(userKeyBundle);

        return auth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityProvider#supports(java.lang.Class)
     */
    public boolean supports(Class aClass)
    {
        return PGPAuthentication.class.isAssignableFrom(aClass);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.security.UMOSecurityProvider#createSecurityContext(org.mule.api.security.UMOAuthentication)
     */
    public UMOSecurityContext createSecurityContext(UMOAuthentication auth)
            throws UnknownAuthenticationTypeException
    {
        return factory.create(auth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.lifecycle.Initialisable#initialise()
     */
    public void initialise() throws InitialisationException
    {
        try
        {
            java.security.Security.addProvider(new cryptix.jce.provider.CryptixCrypto());
            java.security.Security.addProvider(new cryptix.openpgp.provider.CryptixOpenPGP());

            factory = new PGPSecurityContextFactory();
        }
        catch (Exception e)
        {
            throw new InitialisationException(CoreMessages.failedToCreate("PGPProvider"), e, this);
        }
    }

    public PGPKeyRing getKeyManager()
    {
        return keyManager;
    }

    public void setKeyManager(PGPKeyRing keyManager)
    {
        this.keyManager = keyManager;
    }
}

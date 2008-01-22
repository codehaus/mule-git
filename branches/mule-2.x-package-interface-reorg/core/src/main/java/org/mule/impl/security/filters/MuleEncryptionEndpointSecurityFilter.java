/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.security.filters;

import org.mule.api.EncryptionStrategy;
import org.mule.api.Event;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.security.Credentials;
import org.mule.api.security.CredentialsNotSetException;
import org.mule.api.security.CryptoFailureException;
import org.mule.api.security.EncryptionStrategyNotFoundException;
import org.mule.api.security.MuleAuthentication;
import org.mule.api.security.SecurityContext;
import org.mule.api.security.SecurityException;
import org.mule.api.security.SecurityProviderNotFoundException;
import org.mule.api.security.UnauthorisedException;
import org.mule.api.security.UnknownAuthenticationTypeException;
import org.mule.impl.config.i18n.CoreMessages;
import org.mule.impl.security.AbstractEndpointSecurityFilter;
import org.mule.impl.security.DefaultMuleAuthentication;
import org.mule.impl.security.MuleCredentials;
import org.mule.impl.security.MuleHeaderCredentialsAccessor;

/**
 * <code>MuleEncryptionEndpointSecurityFilter</code> provides password-based
 * encryption
 */
public class MuleEncryptionEndpointSecurityFilter extends AbstractEndpointSecurityFilter
{
    private EncryptionStrategy strategy;

    public MuleEncryptionEndpointSecurityFilter()
    {
        setCredentialsAccessor(new MuleHeaderCredentialsAccessor());
    }

    protected final void authenticateInbound(Event event)
        throws SecurityException, CryptoFailureException, EncryptionStrategyNotFoundException,
        UnknownAuthenticationTypeException
    {
        String userHeader = (String) getCredentialsAccessor().getCredentials(event);
        if (userHeader == null)
        {
            throw new CredentialsNotSetException(event.getMessage(), event.getSession().getSecurityContext(),
                event.getEndpoint(), this);
        }

        Credentials user = new MuleCredentials(userHeader, getSecurityManager());

        MuleAuthentication authResult;
        MuleAuthentication umoAuthentication = new DefaultMuleAuthentication(user);
        try
        {
            authResult = getSecurityManager().authenticate(umoAuthentication);
        }
        catch (Exception e)
        {
            // Authentication failed
            if (logger.isDebugEnabled())
            {
                logger.debug("Authentication request for user: " + user.getUsername() 
                    + " failed: " + e.toString());
            }
            throw new UnauthorisedException(CoreMessages.authFailedForUser(user.getUsername()),
                event.getMessage(), e);
        }

        // Authentication success
        if (logger.isDebugEnabled())
        {
            logger.debug("Authentication success: " + authResult.toString());
        }

        SecurityContext context = getSecurityManager().createSecurityContext(authResult);
        context.setAuthentication(authResult);
        event.getSession().setSecurityContext(context);
    }

    protected void authenticateOutbound(Event event)
        throws SecurityException, SecurityProviderNotFoundException, CryptoFailureException
    {
        if (event.getSession().getSecurityContext() == null)
        {
            if (isAuthenticate())
            {
                throw new UnauthorisedException(event.getMessage(), event.getSession().getSecurityContext(),
                    event.getEndpoint(), this);
            }
            else
            {
                return;
            }
        }
        MuleAuthentication auth = event.getSession().getSecurityContext().getAuthentication();
        if (isAuthenticate())
        {
            auth = getSecurityManager().authenticate(auth);
            if (logger.isDebugEnabled())
            {
                logger.debug("Authentication success: " + auth.toString());
            }
        }

        String token = auth.getCredentials().toString();
        String header = new String(strategy.encrypt(token.getBytes(), null));
        getCredentialsAccessor().setCredentials(event, header);

    }

    protected void doInitialise() throws InitialisationException
    {
        if (strategy == null)
        {
            throw new InitialisationException(CoreMessages.encryptionStrategyNotSet(), this);
        }
    }

    public EncryptionStrategy getStrategy()
    {
        return strategy;
    }

    public void setStrategy(EncryptionStrategy strategy)
    {
        this.strategy = strategy;
    }

}

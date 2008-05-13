/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.impl.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.impl.DefaultLifecycleAdapter;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.util.StringUtils;

import java.net.URI;
import java.util.Properties;

/**
 * <code>ResourceNameEndpointBuilder</code> extracts a resource name from a uri
 * endpointUri
 * 
 */
public class ResourceNameEndpointBuilder extends AbstractEndpointBuilder
{
    protected static final Log logger = LogFactory.getLog(ResourceNameEndpointBuilder.class);
	
    public static final String RESOURCE_INFO_PROPERTY = "resourceInfo";

    protected void setEndpoint(URI uri, Properties props) throws MalformedEndpointException
    {
        address = StringUtils.EMPTY;
        String host = uri.getHost();
        if (host != null && !"localhost".equals(host))
        {
            address = host;
        }

        String path = uri.getPath();
        String authority = uri.getAuthority();
        
        if (path != null && path.length() != 0)
        {
            if (address.length() > 0)
            {
                address += "/";
            }
            address += path.substring(1);
        }
        else if (authority != null && !authority.equals(address))
        {        	
            address += authority;
            
            int atCharIndex = -1;
            if (address != null && address.length() != 0 && ((atCharIndex = address.indexOf("@")) > -1))
            {
            	address = address.substring(atCharIndex + 1);
            }

        }
        
        // is user info specified?
        int y = address.indexOf("@");
        if (y > -1)
        {
            userInfo = address.substring(0, y);
        }
        // increment to 0 or one char past the @
        y++;

        String credentials = uri.getUserInfo();
        if (credentials != null && credentials.length() != 0)
        {
        	userInfo = credentials;
        }
        
        int x = address.indexOf(":", y);
        if (x > -1)
        {
            String resourceInfo = address.substring(y, x);
            props.setProperty("resourceInfo", resourceInfo);
            address = address.substring(x + 1);
        }
    }
}

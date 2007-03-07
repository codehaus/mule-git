/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MPL style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http.extras;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class URLAuthenticator extends Authenticator
{   
    protected String userName;
    protected String password;
    
    public URLAuthenticator(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password.toCharArray());
    }
}
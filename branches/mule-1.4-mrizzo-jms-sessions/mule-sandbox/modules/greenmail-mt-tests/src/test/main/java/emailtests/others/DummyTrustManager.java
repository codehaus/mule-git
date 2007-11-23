/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests.others;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * DummyTrustManager - NOT SECURE
 */
public class DummyTrustManager implements X509TrustManager
{

    public void checkClientTrusted(X509Certificate[] cert, String authType)
    {
        // everything is trusted
    }

    public void checkServerTrusted(X509Certificate[] cert, String authType)
    {
        // everything is trusted
    }

    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[0];
    }
}

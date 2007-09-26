/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo.retry;

/**
 * The UMORetryNotifier interface is a callback that allows actions to be performed after each retry. For example, when
 * retrying connections Mule will fire server notification events on success or failure.
 */

public interface UMORetryNotifier
{
    public void sucess(UMORetryContext context);

    public void failed(UMORetryContext context, Throwable e);
}

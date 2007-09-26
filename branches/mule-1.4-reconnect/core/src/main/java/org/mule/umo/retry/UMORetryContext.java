/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo.retry;

import org.mule.umo.UMOMessage;

import java.util.Map;

/** TODO */

public interface UMORetryContext
{
    Map getMetaInfo();

    void setMetaInfo(Map metaInfo);

    UMOMessage[] getReturnMessages();

    UMOMessage getFirstReturnMessage();

    void setReturnMessages(UMOMessage[] returnMessages);

    void addReturnMessage(UMOMessage result);

    String getDescription();
}

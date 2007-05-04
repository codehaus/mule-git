/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.i18n;

import org.mule.util.DateUtils;

import java.util.Date;

// TODO DO: sort methods by code
public class CoreMessages extends MessageFactory
{
    private static final String BUNDLE_NAME = "core";

    public static Message shutdownNormally(Date date)
    {
        return createMessage(BUNDLE_NAME, 7, date);
    }

    public static Object serverWasUpForDuration(long duration)
    {
        String formattedDuration = DateUtils.getFormattedDuration(duration);
        return createMessage(BUNDLE_NAME, 8, formattedDuration);
    }

    public static Message transactionManagerAlreadySet()
    {
        return createMessage(BUNDLE_NAME, 140);
    }

    public static Message notSet()
    {
        return createMessage(BUNDLE_NAME, 5);
    }

    public static Message version()
    {
        return createMessage(BUNDLE_NAME, 6);
    }

    public static Message versionNotSet()
    {
        return createMessage(BUNDLE_NAME, 1);
    }

    public static Message agentsRunning()
    {
        return createMessage(BUNDLE_NAME, 4);
    }

    public static Message none()
    {
        return createMessage(BUNDLE_NAME, 22);
    }

    public static Message serverEventManagerNotEnabled()
    {
        return createMessage(BUNDLE_NAME, 150);
    }

    public static Message configNotFoundUsage()
    {
        return createMessage(BUNDLE_NAME, 9);
    }

    public static Message fatalErrorWhileRunning()
    {
        return createMessage(BUNDLE_NAME, 10);
    }

    public static Object fatalErrorInShutdown()
    {
        return createMessage(BUNDLE_NAME, 20);
    }

    public static Object normalShutdown()
    {
        return createMessage(BUNDLE_NAME, 21);
    }

    public static Message failedToBuildMessage()
    {
        return createMessage(BUNDLE_NAME, 180);
    }

    public static Message managerAlreadyStarted()
    {
        return createMessage(BUNDLE_NAME, 88);
    }

    public static Message cannotUseTxAndRemoteSync()
    {
        return createMessage(BUNDLE_NAME, 178);
    }

    public static Message noComponentForEndpoint()
    {
        return createMessage(BUNDLE_NAME, 64);
    }

    public static Message proxyPoolTimedOut()
    {
        return createMessage(BUNDLE_NAME, 43);
    }

    public static Message failedToGetPooledObject()
    {
        return createMessage(BUNDLE_NAME, 44);
    }

    public static Message streamingEndpointsMustBeUsedWithStreamingModel()
    {
        return createMessage(BUNDLE_NAME, 214);
    }

    public static Message streamingEndpointsDoNotSupportTransformers()
    {
        return createMessage(BUNDLE_NAME, 213);
    }

    public static Message authSecurityManagerNotSet()
    {
        return createMessage(BUNDLE_NAME, 132);
    }

    public static Message encryptionStrategyNotSet()
    {
        return createMessage(BUNDLE_NAME, 56);
    }

    public static Message connectorCausedError()
    {
        return createMessage(BUNDLE_NAME, 33);
    }

    public static Message cannotUseDisposedConnector()
    {
        return createMessage(BUNDLE_NAME, 32);
    }

    public static Message endpointIsNullForListener()
    {
        return createMessage(BUNDLE_NAME, 34);
    }

    public static Message failedToScheduleWork()
    {
        return createMessage(BUNDLE_NAME, 151);
    }

    public static Message failedToReadPayload()
    {
        return createMessage(BUNDLE_NAME, 124);
    }

    public static Message noCatchAllEndpointSet()
    {
        return createMessage(BUNDLE_NAME, 105);
    }

    public static Message transformFailedBeforeFilter()
    {
        return createMessage(BUNDLE_NAME, 52);
    }

    public static Message mustSetMethodNamesOnBinding()
    {
        return createMessage(BUNDLE_NAME, 218);
    }

    public static Message noEndpointsForRouter()
    {
        return createMessage(BUNDLE_NAME, 89);
    }

    public static Message noCorrelationId()
    {
        return createMessage(BUNDLE_NAME, 66);
    }

    public static Message transactionCannotBindToNullKey()
    {
        return createMessage(BUNDLE_NAME, 78);
    }

    public static Message transactionCannotBindNullResource()
    {
        return createMessage(BUNDLE_NAME, 79);
    }

    public static Message transactionSingleResourceOnly()
    {
        return createMessage(BUNDLE_NAME, 80);
    }

    public static Message transactionMarkedForRollback()
    {
        return createMessage(BUNDLE_NAME, 77);
    }

    public static Message transactionCannotUnbind()
    {
        return createMessage(BUNDLE_NAME, 107);
    }

    public static Message transactionAlreadyBound()
    {
        return createMessage(BUNDLE_NAME, 108);
    }

    public static Message transactionRollbackFailed()
    {
        return createMessage(BUNDLE_NAME, 98);
    }

    public static Message transactionCannotReadState()
    {
        return createMessage(BUNDLE_NAME, 99);
    }

    public static Message transactionCommitFailed()
    {
        return createMessage(BUNDLE_NAME, 97);
    }

    public static Message noCurrentEventForTransformer()
    {
        return createMessage(BUNDLE_NAME, 81);
    }

    public static Message exceptionStackIs()
    {
        return createMessage(BUNDLE_NAME, 12);
    }

    public static Message rootStackTrace()
    {
        return createMessage(BUNDLE_NAME, 11);
    }

    public static Message cryptoFailure()
    {
        return createMessage(BUNDLE_NAME, 112);
    }

    public static Message days()
    {
        return createMessage(BUNDLE_NAME, 193);
    }

    public static Message hours()
    {
        return createMessage(BUNDLE_NAME, 194);
    }

    public static Message mins()
    {
        return createMessage(BUNDLE_NAME, 195);
    }

    public static Message sec()
    {
        return createMessage(BUNDLE_NAME, 196);
    }

    public static Message resourceManagerNotStarted()
    {
        return createMessage(BUNDLE_NAME, 161);
    }

    public static Message resourceManagerDirty()
    {
        return createMessage(BUNDLE_NAME, 162);
    }

    public static Message resourceManagerNotReady()
    {
        return createMessage(BUNDLE_NAME, 163);
    }
}

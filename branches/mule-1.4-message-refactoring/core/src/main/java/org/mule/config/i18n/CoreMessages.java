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

import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
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
        return connectorCausedError(null);
    }
    
    public static Message connectorCausedError(Object connector)
    {
        return createMessage(BUNDLE_NAME, 33, connector);
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

    public static Message transformFailedFrom(Class clazz)
    {
        return createMessage(BUNDLE_NAME, 55, clazz);
    }

    public static Message serverStartedAt(long startDate)
    {
        return createMessage(BUNDLE_NAME, 2, new Date(startDate));
    }

    public static Message serverShutdownAt(Date date)
    {
        return createMessage(BUNDLE_NAME, 3, date);
    }

    public static Message failedToLoad(String string)
    {
        return createMessage(BUNDLE_NAME, 58, string);
    }

    public static Message objectIsNull(String string)
    {
        return createMessage(BUNDLE_NAME, 45, string);
    }

    public static Message transactionResourceAlreadyListedForKey(Object key)
    {
        return createMessage(BUNDLE_NAME, 100, key);
    }

    public static Message objectNotRegisteredWithManager(String string)
    {
        return createMessage(BUNDLE_NAME, 82, string);
    }

    public static Message cannotStartTransaction(String string)
    {
        return createMessage(BUNDLE_NAME, 96, string);
    }

    public static Message authSetButNoContext(String name)
    {
        return createMessage(BUNDLE_NAME, 133, name);
    }

    public static Message authFailedForUser(Object user)
    {
        return createMessage(BUNDLE_NAME, 135, user);
    }

    public static Message authDeniedOnEndpoint(UMOEndpointURI endpointURI)
    {
        return createMessage(BUNDLE_NAME, 134, endpointURI);
    }

    public static Message failedToCreateManagerInstance(String className)
    {
        return createMessage(BUNDLE_NAME, 144, className);
    }

    public static Message failedToClone(String string)
    {
        return createMessage(BUNDLE_NAME, 145, string);
    }

    public static Message initialisationFailure(String string)
    {
        return createMessage(BUNDLE_NAME, 85, string);
    }

    public static Message cannotSetObjectOnceItHasBeenSet(String string)
    {
        return createMessage(BUNDLE_NAME, 165, string);
    }

    public static Message failedToInvoke(String string)
    {
        return createMessage(BUNDLE_NAME, 68, string);
    }

    public static Object propertyIsNotSetOnEvent(String property)
    {
        return createMessage(BUNDLE_NAME, 168, property);
    }

    public static Message failedToInvokeRestService(String service)
    {
        return createMessage(BUNDLE_NAME, 172, service);
    }

    public static Message failedToSetPropertiesOn(String string)
    {
        return createMessage(BUNDLE_NAME, 83, string);
    }

    public static Message objectAlreadyInitialised(String name)
    {
        return createMessage(BUNDLE_NAME, 37, name);
    }

    public static Message failedToStart(String string)
    {
        return createMessage(BUNDLE_NAME, 42, string);
    }

    public static Message failedToStop(String string)
    {
        return createMessage(BUNDLE_NAME, 41, string);
    }

    public static Message connectorWithProtocolNotRegistered(String scheme)
    {
        return createMessage(BUNDLE_NAME, 156, scheme);
    }

    public static Message failedToCreateConnectorFromUri(UMOEndpointURI uri)
    {
        return createMessage(BUNDLE_NAME, 84, uri);
    }

    public static Message cannotReadPayloadAsBytes(String type)
    {
        return createMessage(BUNDLE_NAME, 69, type);
    }

    public static Message cannotReadPayloadAsString(String type)
    {
        return createMessage(BUNDLE_NAME, 70, type);
    }

    public static Message failedToReadAttachment(String string)
    {
        return createMessage(BUNDLE_NAME, 207, string);
    }

    public static Message propertiesNotSet(String string)
    {
        return createMessage(BUNDLE_NAME, 183, string);
    }

    public static Message noOutboundRouterSetOn(String string)
    {
        return createMessage(BUNDLE_NAME, 101, string);
    }

    public static Message failedToCreate(String string)
    {
        return createMessage(BUNDLE_NAME, 65, string);
    }

    public static Message sessionValueIsMalformed(String string)
    {
        return createMessage(BUNDLE_NAME, 201, string);
    }

    public static Message containerAlreadyRegistered(String name)
    {
        return createMessage(BUNDLE_NAME, 155, name);
    }

    public static Message eventTypeNotRecognised(String string)
    {
        return createMessage(BUNDLE_NAME, 166, string);
    }

    public static Message couldNotDetermineDestinationComponentFromEndpoint(String endpoint)
    {
        return createMessage(BUNDLE_NAME, 198, endpoint);
    }

    public static Message componentIsStopped(String name)
    {
        return createMessage(BUNDLE_NAME, 167, name);
    }

    public static Message descriptorAlreadyExists(String name)
    {
        return createMessage(BUNDLE_NAME, 171, name);
    }

    public static Message componentNotRegistered(String name)
    {
        return createMessage(BUNDLE_NAME, 46, name);
    }

    public static Message objectFailedToInitialise(String string)
    {
        return createMessage(BUNDLE_NAME, 40, string);
    }

    public static Object failedToDispose(String string)
    {
        return createMessage(BUNDLE_NAME, 67, string);
    }

    public static Message eventProcessingFailedFor(String name)
    {
        return createMessage(BUNDLE_NAME, 127, name);
    }

    public static Message interruptedQueuingEventFor(String name)
    {
        return createMessage(BUNDLE_NAME, 106, name);
    }

    public static Message componentCausedErrorIs(Object component)
    {
        return createMessage(BUNDLE_NAME, 38, component);
    }

    public static Message authNoCredentials()
    {
        return createMessage(BUNDLE_NAME, 152);
    }

    public static Message streamingFailedNoStream()
    {
        return createMessage(BUNDLE_NAME, 205);
    }

    public static Message failedToGetOutputStream()
    {
        return createMessage(BUNDLE_NAME, 223);
    }

    public static Message streamingComponentMustHaveOneEndpoint(String name)
    {
        return createMessage(BUNDLE_NAME, 210, name);
    }

    public static Message streamingFailedForEndpoint(String string)
    {
        return createMessage(BUNDLE_NAME, 212, string);
    }

    public static Message exceptionOnConnectorNotExceptionListener(String name)
    {
        return createMessage(BUNDLE_NAME, 146, name);
    }

    public static Message listenerAlreadyRegistered(UMOEndpointURI endpointUri)
    {
        return createMessage(BUNDLE_NAME, 35, endpointUri);
    }

    public static Message streamingNotSupported(String protocol)
    {
        return createMessage(BUNDLE_NAME, 209, protocol);
    }

    public static Message failedToCreateEndpointFromLocation(String string)
    {
        return createMessage(BUNDLE_NAME, 87, string);
    }

    public static Message moreThanOneConnectorWithProtocol(String protocol)
    {
        return createMessage(BUNDLE_NAME, 221, protocol);
    }

    public static Message cannotInstanciateFinder(String serviceFinder)
    {
        return createMessage(BUNDLE_NAME, 73, serviceFinder);
    }

    public static Message failedToReadFromStore(String absolutePath)
    {
        return createMessage(BUNDLE_NAME, 95, absolutePath);
    }

    public static Message cannotFindBindingForMethod(String name)
    {
        return createMessage(BUNDLE_NAME, 219, name);
    }

    public static Message transactionAvailableButActionIs(String string)
    {
        return createMessage(BUNDLE_NAME, 103, string);
    }

    public static Message endpointNotFound(String endpoint)
    {
        return createMessage(BUNDLE_NAME, 126, endpoint);
    }

    public static Message endpointIsMalformed(String endpoint)
    {
        return createMessage(BUNDLE_NAME, 51, endpoint);
    }

    public static Message objectNotFound(String object)
    {
        return createMessage(BUNDLE_NAME, 76, object);
    }

    public static Message uniqueIdNotSupportedByAdapter(String name)
    {
        return createMessage(BUNDLE_NAME, 147, name);
    }

    public static Message authNoEncryptionStrategy(String strategyName)
    {
        return createMessage(BUNDLE_NAME, 174, strategyName);
    }

    public static Message authNoSecurityProvider(String providerName)
    {
        return createMessage(BUNDLE_NAME, 117, providerName);
    }

    public static Message authTypeNotRecognised(String string)
    {
        return createMessage(BUNDLE_NAME, 131, string);
    }

    public static Message cannotLoadFromClasspath(String string)
    {
        return createMessage(BUNDLE_NAME, 122, string);
    }

    public static Message failedToConvertStringUsingEncoding(String encoding)
    {
        return createMessage(BUNDLE_NAME, 188, encoding);
    }

    public static Message failedToDispatchToReplyto(UMOEndpoint endpoint)
    {
        return createMessage(BUNDLE_NAME, 128, endpoint);
    }

    public static Message failedToRouterViaEndpoint(UMOImmutableEndpoint endpoint)
    {
        return createMessage(BUNDLE_NAME, 30, endpoint);
    }
}

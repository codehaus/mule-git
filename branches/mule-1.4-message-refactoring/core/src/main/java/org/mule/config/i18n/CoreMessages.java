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
    private static final String BUNDLE_PATH = getBundlePath("core");

    public static Message shutdownNormally(Date date)
    {
        return createMessage(BUNDLE_PATH, 7, date);
    }

    public static Object serverWasUpForDuration(long duration)
    {
        String formattedDuration = DateUtils.getFormattedDuration(duration);
        return createMessage(BUNDLE_PATH, 8, formattedDuration);
    }

    public static Message transactionManagerAlreadySet()
    {
        return createMessage(BUNDLE_PATH, 140);
    }

    public static Message notSet()
    {
        return createMessage(BUNDLE_PATH, 5);
    }

    public static Message version()
    {
        return createMessage(BUNDLE_PATH, 6);
    }

    public static Message versionNotSet()
    {
        return createMessage(BUNDLE_PATH, 1);
    }

    public static Message agentsRunning()
    {
        return createMessage(BUNDLE_PATH, 4);
    }

    public static Message none()
    {
        return createMessage(BUNDLE_PATH, 22);
    }

    public static Message serverEventManagerNotEnabled()
    {
        return createMessage(BUNDLE_PATH, 150);
    }

    public static Message configNotFoundUsage()
    {
        return createMessage(BUNDLE_PATH, 9);
    }

    public static Message fatalErrorWhileRunning()
    {
        return createMessage(BUNDLE_PATH, 10);
    }

    public static Object fatalErrorInShutdown()
    {
        return createMessage(BUNDLE_PATH, 20);
    }

    public static Object normalShutdown()
    {
        return createMessage(BUNDLE_PATH, 21);
    }

    public static Message failedToBuildMessage()
    {
        return createMessage(BUNDLE_PATH, 180);
    }

    public static Message managerAlreadyStarted()
    {
        return createMessage(BUNDLE_PATH, 88);
    }

    public static Message cannotUseTxAndRemoteSync()
    {
        return createMessage(BUNDLE_PATH, 178);
    }

    public static Message noComponentForEndpoint()
    {
        return createMessage(BUNDLE_PATH, 64);
    }

    public static Message proxyPoolTimedOut()
    {
        return createMessage(BUNDLE_PATH, 43);
    }

    public static Message failedToGetPooledObject()
    {
        return createMessage(BUNDLE_PATH, 44);
    }

    public static Message streamingEndpointsMustBeUsedWithStreamingModel()
    {
        return createMessage(BUNDLE_PATH, 214);
    }

    public static Message streamingEndpointsDoNotSupportTransformers()
    {
        return createMessage(BUNDLE_PATH, 213);
    }

    public static Message authSecurityManagerNotSet()
    {
        return createMessage(BUNDLE_PATH, 132);
    }

    public static Message encryptionStrategyNotSet()
    {
        return createMessage(BUNDLE_PATH, 56);
    }

    public static Message connectorCausedError()
    {
        return connectorCausedError(null);
    }
    
    public static Message connectorCausedError(Object connector)
    {
        return createMessage(BUNDLE_PATH, 33, connector);
    }

    public static Message cannotUseDisposedConnector()
    {
        return createMessage(BUNDLE_PATH, 32);
    }

    public static Message endpointIsNullForListener()
    {
        return createMessage(BUNDLE_PATH, 34);
    }

    public static Message failedToScheduleWork()
    {
        return createMessage(BUNDLE_PATH, 151);
    }

    public static Message failedToReadPayload()
    {
        return createMessage(BUNDLE_PATH, 124);
    }

    public static Message noCatchAllEndpointSet()
    {
        return createMessage(BUNDLE_PATH, 105);
    }

    public static Message transformFailedBeforeFilter()
    {
        return createMessage(BUNDLE_PATH, 52);
    }

    public static Message mustSetMethodNamesOnBinding()
    {
        return createMessage(BUNDLE_PATH, 218);
    }

    public static Message noEndpointsForRouter()
    {
        return createMessage(BUNDLE_PATH, 89);
    }

    public static Message noCorrelationId()
    {
        return createMessage(BUNDLE_PATH, 66);
    }

    public static Message transactionCannotBindToNullKey()
    {
        return createMessage(BUNDLE_PATH, 78);
    }

    public static Message transactionCannotBindNullResource()
    {
        return createMessage(BUNDLE_PATH, 79);
    }

    public static Message transactionSingleResourceOnly()
    {
        return createMessage(BUNDLE_PATH, 80);
    }

    public static Message transactionMarkedForRollback()
    {
        return createMessage(BUNDLE_PATH, 77);
    }

    public static Message transactionCannotUnbind()
    {
        return createMessage(BUNDLE_PATH, 107);
    }

    public static Message transactionAlreadyBound()
    {
        return createMessage(BUNDLE_PATH, 108);
    }

    public static Message transactionRollbackFailed()
    {
        return createMessage(BUNDLE_PATH, 98);
    }

    public static Message transactionCannotReadState()
    {
        return createMessage(BUNDLE_PATH, 99);
    }

    public static Message transactionCommitFailed()
    {
        return createMessage(BUNDLE_PATH, 97);
    }

    public static Message noCurrentEventForTransformer()
    {
        return createMessage(BUNDLE_PATH, 81);
    }

    public static Message exceptionStackIs()
    {
        return createMessage(BUNDLE_PATH, 12);
    }

    public static Message rootStackTrace()
    {
        return createMessage(BUNDLE_PATH, 11);
    }

    public static Message cryptoFailure()
    {
        return createMessage(BUNDLE_PATH, 112);
    }

    public static Message days()
    {
        return createMessage(BUNDLE_PATH, 193);
    }

    public static Message hours()
    {
        return createMessage(BUNDLE_PATH, 194);
    }

    public static Message mins()
    {
        return createMessage(BUNDLE_PATH, 195);
    }

    public static Message sec()
    {
        return createMessage(BUNDLE_PATH, 196);
    }

    public static Message resourceManagerNotStarted()
    {
        return createMessage(BUNDLE_PATH, 161);
    }

    public static Message resourceManagerDirty()
    {
        return createMessage(BUNDLE_PATH, 162);
    }

    public static Message resourceManagerNotReady()
    {
        return createMessage(BUNDLE_PATH, 163);
    }

    public static Message transformFailedFrom(Class clazz)
    {
        return createMessage(BUNDLE_PATH, 55, clazz);
    }

    public static Message serverStartedAt(long startDate)
    {
        return createMessage(BUNDLE_PATH, 2, new Date(startDate));
    }

    public static Message serverShutdownAt(Date date)
    {
        return createMessage(BUNDLE_PATH, 3, date);
    }

    public static Message failedToLoad(String string)
    {
        return createMessage(BUNDLE_PATH, 58, string);
    }

    public static Message objectIsNull(String string)
    {
        return createMessage(BUNDLE_PATH, 45, string);
    }

    public static Message transactionResourceAlreadyListedForKey(Object key)
    {
        return createMessage(BUNDLE_PATH, 100, key);
    }

    public static Message objectNotRegisteredWithManager(String string)
    {
        return createMessage(BUNDLE_PATH, 82, string);
    }

    public static Message cannotStartTransaction(String string)
    {
        return createMessage(BUNDLE_PATH, 96, string);
    }

    public static Message authSetButNoContext(String name)
    {
        return createMessage(BUNDLE_PATH, 133, name);
    }

    public static Message authFailedForUser(Object user)
    {
        return createMessage(BUNDLE_PATH, 135, user);
    }

    public static Message authDeniedOnEndpoint(UMOEndpointURI endpointURI)
    {
        return createMessage(BUNDLE_PATH, 134, endpointURI);
    }

    public static Message failedToCreateManagerInstance(String className)
    {
        return createMessage(BUNDLE_PATH, 144, className);
    }

    public static Message failedToClone(String string)
    {
        return createMessage(BUNDLE_PATH, 145, string);
    }

    public static Message initialisationFailure(String string)
    {
        return createMessage(BUNDLE_PATH, 85, string);
    }

    public static Message cannotSetObjectOnceItHasBeenSet(String string)
    {
        return createMessage(BUNDLE_PATH, 165, string);
    }

    public static Message failedToInvoke(String string)
    {
        return createMessage(BUNDLE_PATH, 68, string);
    }

    public static Object propertyIsNotSetOnEvent(String property)
    {
        return createMessage(BUNDLE_PATH, 168, property);
    }

    public static Message failedToInvokeRestService(String service)
    {
        return createMessage(BUNDLE_PATH, 172, service);
    }

    public static Message failedToSetPropertiesOn(String string)
    {
        return createMessage(BUNDLE_PATH, 83, string);
    }

    public static Message objectAlreadyInitialised(String name)
    {
        return createMessage(BUNDLE_PATH, 37, name);
    }

    public static Message failedToStart(String string)
    {
        return createMessage(BUNDLE_PATH, 42, string);
    }

    public static Message failedToStop(String string)
    {
        return createMessage(BUNDLE_PATH, 41, string);
    }

    public static Message connectorWithProtocolNotRegistered(String scheme)
    {
        return createMessage(BUNDLE_PATH, 156, scheme);
    }

    public static Message failedToCreateConnectorFromUri(UMOEndpointURI uri)
    {
        return createMessage(BUNDLE_PATH, 84, uri);
    }

    public static Message cannotReadPayloadAsBytes(String type)
    {
        return createMessage(BUNDLE_PATH, 69, type);
    }

    public static Message cannotReadPayloadAsString(String type)
    {
        return createMessage(BUNDLE_PATH, 70, type);
    }

    public static Message failedToReadAttachment(String string)
    {
        return createMessage(BUNDLE_PATH, 207, string);
    }

    public static Message propertiesNotSet(String string)
    {
        return createMessage(BUNDLE_PATH, 183, string);
    }

    public static Message noOutboundRouterSetOn(String string)
    {
        return createMessage(BUNDLE_PATH, 101, string);
    }

    public static Message failedToCreate(String string)
    {
        return createMessage(BUNDLE_PATH, 65, string);
    }

    public static Message sessionValueIsMalformed(String string)
    {
        return createMessage(BUNDLE_PATH, 201, string);
    }

    public static Message containerAlreadyRegistered(String name)
    {
        return createMessage(BUNDLE_PATH, 155, name);
    }

    public static Message eventTypeNotRecognised(String string)
    {
        return createMessage(BUNDLE_PATH, 166, string);
    }

    public static Message couldNotDetermineDestinationComponentFromEndpoint(String endpoint)
    {
        return createMessage(BUNDLE_PATH, 198, endpoint);
    }

    public static Message componentIsStopped(String name)
    {
        return createMessage(BUNDLE_PATH, 167, name);
    }

    public static Message descriptorAlreadyExists(String name)
    {
        return createMessage(BUNDLE_PATH, 171, name);
    }

    public static Message componentNotRegistered(String name)
    {
        return createMessage(BUNDLE_PATH, 46, name);
    }

    public static Message objectFailedToInitialise(String string)
    {
        return createMessage(BUNDLE_PATH, 40, string);
    }

    public static Object failedToDispose(String string)
    {
        return createMessage(BUNDLE_PATH, 67, string);
    }

    public static Message eventProcessingFailedFor(String name)
    {
        return createMessage(BUNDLE_PATH, 127, name);
    }

    public static Message interruptedQueuingEventFor(String name)
    {
        return createMessage(BUNDLE_PATH, 106, name);
    }

    public static Message componentCausedErrorIs(Object component)
    {
        return createMessage(BUNDLE_PATH, 38, component);
    }

    public static Message authNoCredentials()
    {
        return createMessage(BUNDLE_PATH, 152);
    }

    public static Message streamingFailedNoStream()
    {
        return createMessage(BUNDLE_PATH, 205);
    }

    public static Message failedToGetOutputStream()
    {
        return createMessage(BUNDLE_PATH, 223);
    }

    public static Message streamingComponentMustHaveOneEndpoint(String name)
    {
        return createMessage(BUNDLE_PATH, 210, name);
    }

    public static Message streamingFailedForEndpoint(String string)
    {
        return createMessage(BUNDLE_PATH, 212, string);
    }

    public static Message exceptionOnConnectorNotExceptionListener(String name)
    {
        return createMessage(BUNDLE_PATH, 146, name);
    }

    public static Message listenerAlreadyRegistered(UMOEndpointURI endpointUri)
    {
        return createMessage(BUNDLE_PATH, 35, endpointUri);
    }

    public static Message streamingNotSupported(String protocol)
    {
        return createMessage(BUNDLE_PATH, 209, protocol);
    }

    public static Message failedToCreateEndpointFromLocation(String string)
    {
        return createMessage(BUNDLE_PATH, 87, string);
    }

    public static Message moreThanOneConnectorWithProtocol(String protocol)
    {
        return createMessage(BUNDLE_PATH, 221, protocol);
    }

    public static Message cannotInstanciateFinder(String serviceFinder)
    {
        return createMessage(BUNDLE_PATH, 73, serviceFinder);
    }

    public static Message failedToReadFromStore(String absolutePath)
    {
        return createMessage(BUNDLE_PATH, 95, absolutePath);
    }

    public static Message cannotFindBindingForMethod(String name)
    {
        return createMessage(BUNDLE_PATH, 219, name);
    }

    public static Message transactionAvailableButActionIs(String string)
    {
        return createMessage(BUNDLE_PATH, 103, string);
    }

    public static Message endpointNotFound(String endpoint)
    {
        return createMessage(BUNDLE_PATH, 126, endpoint);
    }

    public static Message endpointIsMalformed(String endpoint)
    {
        return createMessage(BUNDLE_PATH, 51, endpoint);
    }

    public static Message objectNotFound(String object)
    {
        return createMessage(BUNDLE_PATH, 76, object);
    }

    public static Message uniqueIdNotSupportedByAdapter(String name)
    {
        return createMessage(BUNDLE_PATH, 147, name);
    }

    public static Message authNoEncryptionStrategy(String strategyName)
    {
        return createMessage(BUNDLE_PATH, 174, strategyName);
    }

    public static Message authNoSecurityProvider(String providerName)
    {
        return createMessage(BUNDLE_PATH, 117, providerName);
    }

    public static Message authTypeNotRecognised(String string)
    {
        return createMessage(BUNDLE_PATH, 131, string);
    }

    public static Message cannotLoadFromClasspath(String string)
    {
        return createMessage(BUNDLE_PATH, 122, string);
    }

    public static Message failedToConvertStringUsingEncoding(String encoding)
    {
        return createMessage(BUNDLE_PATH, 188, encoding);
    }

    public static Message failedToDispatchToReplyto(UMOEndpoint endpoint)
    {
        return createMessage(BUNDLE_PATH, 128, endpoint);
    }

    public static Message failedToRouterViaEndpoint(UMOImmutableEndpoint endpoint)
    {
        return createMessage(BUNDLE_PATH, 30, endpoint);
    }
}

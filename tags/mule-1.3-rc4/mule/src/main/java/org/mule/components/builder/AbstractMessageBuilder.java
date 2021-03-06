/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.components.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.MuleProperties;
import org.mule.impl.MuleMessage;
import org.mule.impl.UMODescriptorAware;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.Callable;
import org.mule.umo.routing.UMOOutboundRouter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A component that will invoke all outbound endpoints configured on the component allow the
 * result of each endpoint invocation to be aggregated to a single message.
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public abstract class AbstractMessageBuilder implements UMODescriptorAware, Callable, MessageBuilder {

    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    protected UMODescriptor descriptor;

    public void setDescriptor(UMODescriptor descriptor) {
        this.descriptor = descriptor;
    }


    public Object onCall(UMOEventContext eventContext) throws Exception {

        UMOMessage requestMessage = new MuleMessage(eventContext.getTransformedMessage(), eventContext.getMessage());

        UMOMessage responseMessage = requestMessage;
        Object builtMessage;

        if(descriptor.getOutboundRouter().hasEndpoints() ) {
            List endpoints = new ArrayList();
            for (Iterator iterator = descriptor.getOutboundRouter().getRouters().iterator(); iterator.hasNext();) {
                UMOOutboundRouter router = (UMOOutboundRouter) iterator.next();
                endpoints.addAll(router.getEndpoints());
            }
            for (Iterator iterator = endpoints.iterator(); iterator.hasNext();) {
                UMOEndpoint endpoint = (UMOEndpoint) iterator.next();
                boolean rsync = eventContext.getMessage().getBooleanProperty(
                        MuleProperties.MULE_REMOTE_SYNC_PROPERTY, endpoint.isRemoteSync());
                if(!rsync) {
                    logger.info("Endpoint: " + endpoint + " is not remoteSync enabled. Message builder finishing");
                    if(eventContext.isSynchronous()) {
                        responseMessage = eventContext.sendEvent(requestMessage, endpoint);
                    } else {
                        eventContext.dispatchEvent(requestMessage, endpoint);
                        responseMessage=null;
                    }
                    break;
                } else {
                    responseMessage = eventContext.sendEvent(requestMessage, endpoint);
                    builtMessage = buildMessage(requestMessage, responseMessage);
                    responseMessage = new MuleMessage(builtMessage, responseMessage);
                    requestMessage = responseMessage;
                }
            }
        } else {
            logger.info("There are currently no endpoints configured on component: " + descriptor.getName());
        }
        eventContext.setStopFurtherProcessing(true);
        return responseMessage;
    }
}

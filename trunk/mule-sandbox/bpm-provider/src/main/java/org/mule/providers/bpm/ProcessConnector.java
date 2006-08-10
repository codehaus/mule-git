/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.extras.client.MuleClient;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.ConnectorException;

/**
 * The BPM provider allows Mule events to initiate and/or advance processes in an
 * external or embedded Business Process Management System (BPMS). It also allows
 * executing processes to generate Mule events.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessConnector extends AbstractServiceEnabledConnector {

    public static final String PROPERTY_PROCESS_TYPE = "processType";
    public static final String PROPERTY_PROCESS_ID = "processId";
    public static final String PROPERTY_PROCESS_STARTED = "processStarted";
    public static final String PROPERTY_ACTION = "action";
    public static final String PROPERTY_TRANSITION = "transition";
    public static final String ACTION_START = "start";
    public static final String ACTION_ADVANCE = "advance";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_ABORT = "abort";
    public static final String PROCESS_VARIABLE_INCOMING = "incoming";
    public static final String PROCESS_VARIABLE_DATA = "data";
    public static final int MULE_CLIENT_TIMEOUT = 1000;

    private MuleJbpmSessionFactory jbpmSessionFactory = null;
    private MuleClient muleClient = null;

    public String getProtocol() {
        return "bpm";
    }

    public void doInitialise() throws InitialisationException {
        super.doInitialise();
        try {
            // TODO
        } catch (Exception e) {
            throw new InitialisationException(new Message(
                    Messages.INITIALISATION_FAILURE_X, "BPM provider"), e);
        }
    }

    protected void doStart() throws UMOException {
        super.doStart();
        try {
            // Create the jBpm session factory (this takes awhile because it verifies
            // all the Hibernate (ORM) mappings).
            if (jbpmSessionFactory == null) {
                jbpmSessionFactory = new MuleJbpmSessionFactory();
            }

            // Create a client for the Mule server (used to generate events from the
            // executing processes).
            if (muleClient == null) {
                muleClient = new MuleClient(/*initialiseManager*/false);
            }
        } catch (Exception e) {
            throw new ConnectorException(new Message(
                    Messages.FAILED_TO_START_X, "BPM provider"), this, e);
        }
    }

    // TODO
    protected void doStop() throws UMOException {
        try {
            if (jbpmSessionFactory != null) {
                jbpmSessionFactory.getSessionFactory().close();
            }
            super.doStop();
        } catch (Exception e) {
            throw new ConnectorException(new Message(Messages.FAILED_TO_STOP_X,
                    "BPM provider"), this, e);
        }
    }

    public MuleJbpmSessionFactory getJbpmSessionFactory() {
        return jbpmSessionFactory;
    }

    public MuleClient getMuleClient() {
        return muleClient;
    }

    private static Log log = LogFactory.getLog(ProcessConnector.class);
}

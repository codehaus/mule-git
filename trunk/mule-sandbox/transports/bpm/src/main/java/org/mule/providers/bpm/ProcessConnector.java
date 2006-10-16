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

import org.mule.extras.client.MuleClient;
import org.mule.providers.AbstractServiceEnabledConnector;

/**
 * The BPM provider allows Mule events to initiate and/or advance processes in an
 * external or embedded Business Process Management System (BPMS). It also allows
 * executing processes to generate Mule events.
 */
public class ProcessConnector extends AbstractServiceEnabledConnector {

    /** The underlying BPMS */
    protected BPMS bpms;

    /** This field will be used to correlated messages with processes. */
    protected String processIdField;

    /** If true, a process can only send messages to the outgoing endpoints defined for
     * its component.  If false, a process can send messages to dynamically-generated
     * endpoints. */
    protected boolean localEndpointsOnly = true;

    /** The global receiver allows an endpoint of type "bpm://*" without specifying a process name.  This
     * could be useful for dynamically generating the name of the process to send to/receive from. */
    protected boolean allowGlobalReceiver = false;

    public static final String PROPERTY_ENDPOINT = "endpoint";
    public static final String PROPERTY_PROCESS_TYPE = "processType";
    public static final String PROPERTY_PROCESS_ID = "processId";
    public static final String PROPERTY_ACTION = "action";
    public static final String PROPERTY_TRANSITION = "transition";
    public static final String PROPERTY_PROCESS_STARTED = "started";
    public static final String ACTION_START = "start";
    public static final String ACTION_ADVANCE = "advance";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_ABORT = "abort";
    public static final String PROCESS_VARIABLE_INCOMING = "incoming";
    public static final String PROCESS_VARIABLE_INCOMING_SOURCE = "incomingSource";
    public static final String PROCESS_VARIABLE_DATA = "data";

    public static final String PROTOCOL = "bpm";
    public static final String GLOBAL_RECEIVER = PROTOCOL + "://*";

    private MuleClient muleClient = null;

    public String getProtocol() {
        return PROTOCOL;
    }

    public BPMS getBpms() {
        return bpms;
    }

    public void setBpms(BPMS bpms) {
        this.bpms = bpms;
    }

    public MuleClient getMuleClient() {
        return muleClient;
    }

    public boolean isLocalEndpointsOnly() {
        return localEndpointsOnly;
    }

    public void setLocalEndpointsOnly(boolean localEndpointsOnly) {
        this.localEndpointsOnly = localEndpointsOnly;
    }

    public boolean isAllowGlobalReceiver() {
        return allowGlobalReceiver;
    }

    public void setAllowGlobalReceiver(boolean allowGlobalReceiver) {
        this.allowGlobalReceiver = allowGlobalReceiver;
    }

    public String getProcessIdField() {
        return processIdField;
    }

    public void setProcessIdField(String processIdField) {
        this.processIdField = processIdField;
    }
}

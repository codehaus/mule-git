/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm.osworkflow.functions;

import org.mule.config.MuleProperties;
import org.mule.providers.bpm.MessageService;
import org.mule.providers.bpm.ProcessConnector;
import org.mule.providers.bpm.osworkflow.MuleConfiguration;
import org.mule.umo.UMOMessage;
import org.mule.util.StringUtils;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.WorkflowEntry;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Sends a Mule message to the specified endpoint. If the message is synchronous, 
 * the response from Mule will be automatically stored in PROCESS_VARIABLE_INCOMING.
 * 
 *    <function type="class">
 *      <arg name="class.name">org.mule.providers.bpm.osworkflow.functions.SendMuleEvent</arg>
 *      <arg name="endpoint">vm://echo</arg>
 *      <arg name="payload">Message in a bottle.</arg>
 *      <arg name="synchronous">true</arg>
 *    </function>
 *            
 * @param endpoint - the Mule endpoint
 * @param transformers - any transformers to be applied
 * @param payload - specify the payload as a string directly in the jPDL
 * @param payloadSource - process variable from which to generate the message
 *            payload, defaults to {@link ProcessConnector.PROCESS_VARIABLE_DATA} or
 *            {@link ProcessConnector.PROCESS_VARIABLE_INCOMING}
 */
public class SendMuleEvent implements FunctionProvider
{
    protected static transient Log logger = LogFactory.getLog(SendMuleEvent.class);
    
    public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException
    {
        boolean synchronous = "true".equals(args.get("synchronous"));

        String endpoint = (String) args.get("endpoint");
        String transformers = (String) args.get("transformers");
        if (transformers != null)
        {
            endpoint += "?transformers=" + transformers;
        }
        
        // Use "payload" to easily specify the payload as a string directly in the process definition.
        // Use "payloadSource" to get the payload from a process variable. 
        String payload = (String) args.get("payload");
        String payloadSource = (String) args.get("payloadSource");
        
        // Get the actual payload (as an object).
        Object payloadObject;
        if (payload == null)
        {
            if (payloadSource == null)
            {
                payloadObject = ps.getObject(ProcessConnector.PROCESS_VARIABLE_DATA);
                if (payloadObject == null)
                {
                    payloadObject = ps.getObject(ProcessConnector.PROCESS_VARIABLE_INCOMING);
                }
            }
            else
            {
                // The payloadSource may be specified using JavaBean notation (e.g.,
                // "myObject.myStuff.myField" would first retrieve the process
                // variable "myObject" and then call .getMyStuff().getMyField()
                String[] tokens = StringUtils.split(payloadSource, ".", 2);
                payloadObject = ps.getObject(tokens[0]);
                if (tokens.length > 1)
                {
                    JXPathContext context = JXPathContext.newContext(payloadObject);
                    payloadObject = context.getValue(tokens[1]);
                }
            }
        }
        else
        {
            payloadObject = payload;
        }
        if (payloadObject == null)
        {
            throw new IllegalArgumentException("Payload for message is null.  Payload source is \""
                            + payloadSource + "\"");
        }

        /////////////////////////////////////////////////////////////////////////////////
        
        // Get info. on the current process.
        WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
        Map props = new HashMap();
        props.put(ProcessConnector.PROPERTY_PROCESS_TYPE, entry.getWorkflowName());
        props.put(ProcessConnector.PROPERTY_PROCESS_ID, new Long(entry.getId()));
        props.put(MuleProperties.MULE_CORRELATION_ID_PROPERTY, new Long(entry.getId()));

        // Get a callback to Mule from the configuration.
        MuleConfiguration config = (MuleConfiguration) transientVars.get("configuration");
        MessageService mule = config.getMsgService();

        // Generate a message in Mule.
        UMOMessage response;
        try 
        {
            response = mule.generateMessage(endpoint, payloadObject, props, synchronous);
        }
        catch (Exception e)
        {
            throw new WorkflowException(e);
        }

        // If the message is synchronous, pipe the response back into the workflow.
        if (synchronous)
        {
            if (response != null)
            {
                ps.setObject(ProcessConnector.PROCESS_VARIABLE_INCOMING, response.getPayload());
            }
            else 
            {
                logger.info("Synchronous message was sent to endpoint " + endpoint
                    + ", but no response was returned.");
            }
        }
    }

}

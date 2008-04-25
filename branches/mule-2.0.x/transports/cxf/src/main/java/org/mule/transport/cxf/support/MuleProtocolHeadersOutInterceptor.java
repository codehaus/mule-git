/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.support;

import org.mule.api.MuleMessage;
import org.mule.transport.cxf.CxfConstants;
import org.mule.transport.http.HttpConstants;

import java.util.List;
import java.util.Map;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class MuleProtocolHeadersOutInterceptor
    extends AbstractPhaseInterceptor<Message>
{

    public MuleProtocolHeadersOutInterceptor()
    {
        super(Phase.PRE_STREAM);
        getAfter().add(AttachmentOutInterceptor.class.getName());
    }

    public void handleMessage(Message message) throws Fault
    {
        MuleMessage muleMsg = (MuleMessage) message.getExchange().get(CxfConstants.MULE_MESSAGE);

        if (muleMsg == null)
        {
            return;
        }
        
        String ct = (String) message.get(Message.CONTENT_TYPE);
        if (ct != null)
        {
            muleMsg.setProperty(HttpConstants.HEADER_CONTENT_TYPE, ct);
        }
        
        Map<String, List<String>> reqHeaders = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
        if (reqHeaders != null)
        {
            for (Map.Entry<String, List<String>> e : reqHeaders.entrySet())
            {
                String val = format(e.getValue());
                muleMsg.setProperty(e.getKey(), val);
            }
        }   
        message.getInterceptorChain().pause();
    }

    private String format(List<String> value)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        
        for (String s : value) {
            if (!first) 
            {
                sb.append(", ");
                first = false;
            }
            else 
            {
                first = false;
            }
            
            sb.append(s);
        }
        return sb.toString();
    }
}



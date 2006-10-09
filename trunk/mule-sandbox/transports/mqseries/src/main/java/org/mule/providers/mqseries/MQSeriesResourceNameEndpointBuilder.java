/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/MQSeriesResourceNameEndpointBuilder.java,v 1.4 2006/02/02 12:23:29 rossmason Exp $
* $Revision: 1.4 $
* $Date: 2006/02/02 12:23:29 $
* ------------------------------------------------------------------------------------------------------
* 
* Copyright (c) SymphonySoft Limited. All rights reserved.
* http://www.symphonysoft.com
* 
* The software in this package is published under the terms of the BSD
* style license a copy of which has been included with this distribution in
* the LICENSE.txt file. 
*
*/
package org.mule.providers.mqseries;

import org.mule.impl.endpoint.ResourceNameEndpointBuilder;
import org.mule.umo.endpoint.MalformedEndpointException;

import java.net.URI;
import java.util.Properties;

import com.ibm.mq.jms.MQQueue;

/**
 * translates an MQSeries Uri to a MuleEndpointURI.  MQSeries Uris are in the form of -
 * <p/>
 * mqs://[queue manager/]<queue name>?[endpoint params]
 *
 * or for topics
 *
 * mqs://[queue manager/]topic:<topic name>?[endpoint params]
 *
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.4 $
 */
public class MQSeriesResourceNameEndpointBuilder extends ResourceNameEndpointBuilder {

    public static final String QUEUE_MANAGER_PROPERTY = "queueManager";
    public static final String MQS_SCHEME = "mqs";

    
    protected void setEndpoint(URI uri, Properties props) throws MalformedEndpointException {

        String scheme = uri.getScheme().toLowerCase();
        super.setEndpoint(uri, props);
        endpointName = (String)props.remove("endpointName");

        if(uri.getHost()!=null && !address.equals(uri.getHost())) {
            props.setProperty(QUEUE_MANAGER_PROPERTY, uri.getHost());
        } else if(uri.getAuthority()!=null && !address.equals(uri.getAuthority())) {
            props.setProperty(QUEUE_MANAGER_PROPERTY, uri.getAuthority());
        }

        //Handle topic:// queue:// schems
        if(!MQS_SCHEME.equalsIgnoreCase(scheme)) {
            address = uri.toString();
            props.setProperty(RESOURCE_INFO_PROPERTY, scheme);
        }

        //super.setEndpoint(uri, props);
//        if(uri.getPath().equals("")) {
//            if(uri.getHost()==null) {
//                 address = uri.getAuthority();
//            } else {
//                address = uri.getHost();
//            }
//        } else {
//            props.setProperty(QUEUE_MANAGER_PROPERTY, uri.getHost());
//            address = uri.getPath().substring(1);
//        }
//
//
//        int x = address.indexOf(":");
//        if (x > -1) {
//            String resourceInfo = address.substring(0, x);
//            props.setProperty(RESOURCE_INFO_PROPERTY, resourceInfo);
//            address = address.substring(x + 1);
//        }
    }
}

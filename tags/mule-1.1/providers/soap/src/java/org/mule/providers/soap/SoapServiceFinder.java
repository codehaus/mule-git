/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.soap;

import org.mule.providers.service.ConnectorFactory;
import org.mule.providers.service.ConnectorFactoryException;
import org.mule.providers.service.ConnectorServiceDescriptor;
import org.mule.providers.service.ConnectorServiceFinder;
import org.mule.providers.service.ConnectorServiceNotFoundException;
import org.mule.util.ClassHelper;

/**
 * <code>SoapServiceFinder</code> finds a the connector service to use by
 * checking the classpath for jars required for each of the soap connector
 * implementations
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class SoapServiceFinder implements ConnectorServiceFinder
{
    public static final String AXIS_CLASS = "org.apache.axis.AxisEngine";
    public static final String GLUE_CLASS = "electric.server.http.HTTP";

    public ConnectorServiceDescriptor findService(String service) throws ConnectorFactoryException
    {
        try {
            ClassHelper.loadClass(AXIS_CLASS, getClass());
            return ConnectorFactory.getServiceDescriptor("axis");
        } catch (ClassNotFoundException e) {
            try {
                ClassHelper.loadClass(GLUE_CLASS, getClass());
                return ConnectorFactory.getServiceDescriptor("glue");
            } catch (ClassNotFoundException e1) {
                throw new ConnectorServiceNotFoundException("Could not find Axis or glue on the classpath");
            }
        }
    }
}

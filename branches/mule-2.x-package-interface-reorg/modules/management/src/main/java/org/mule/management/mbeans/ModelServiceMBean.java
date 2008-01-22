/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import org.mule.api.AbstractMuleException;

/**
 * <code>ModelServiceMBean</code> JMX Service interface for the Model.
 */
public interface ModelServiceMBean
{

    void start() throws AbstractMuleException;

    void stop() throws AbstractMuleException;

//    boolean isComponentRegistered(String name);
//
//    UMODescriptor getComponentDescriptor(String name);
//
//    void startComponent(String name) throws AbstractMuleException;
//
//    void stopComponent(String name) throws AbstractMuleException;
//
//    void pauseComponent(String name) throws AbstractMuleException;
//
//    void resumeComponent(String name) throws AbstractMuleException;
//
//    void unregisterComponent(String name) throws AbstractMuleException;

    String getName();

    String getType();
}

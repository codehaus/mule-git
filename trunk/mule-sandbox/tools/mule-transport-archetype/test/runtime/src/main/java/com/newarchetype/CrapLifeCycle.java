/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 * CrapLifeCycle.java
 *
 */
package com.newarchetype;


import java.util.logging.Logger;
import javax.jbi.JBIException;
import javax.jbi.component.ComponentLifeCycle;
import javax.jbi.component.ComponentContext;
import javax.management.ObjectName;



/**
 * ComponentLifeCycle implementation for Crap component.
 *
 *
 */
public class CrapLifeCycle implements ComponentLifeCycle {
    /**
     * Component Context.
     */
    private ComponentContext compCtx;
    
    /**
     * Logger.
     */
    private Logger log;
    
    
    /**
     * Creates a new instance of CrapLifeCycle.
     */
    public CrapLifeCycle() {
    }
    
    /**
     * gets the ComponentContext set by the JBI server
     * @return JBI env set component context
     */
    public ComponentContext getComponentContext(){
        return compCtx;
    }
    
    //javax.jbi.component.ComponentLifeCycle methods
    
    /**
     * Get the JMX ObjectName for any additional MBean for this ComponentLifecycle. If there is
     * none, return null.
     *
     * @return ObjectName the JMX object name of the additional MBean or null
     *         if there is no additional MBean.
     */
    public ObjectName getExtensionMBeanName() {
        return null;
    }
    
    /**
     * Initialize the component. This performs initialization required by the component
     * but does not make it ready to process messages. This method is called once for
     * each life cycle of the component.
     * If the component needs to register an additional MBean to extend its life cycle,
     * or provide other component management tasks, it should be registered during this call.
     * This method is called immediately after installation.
     * It is also called when the JBI framework is starting up, and any time the component is being
     * restarted after previously being shut down through a call to shutDown().
     *
     * @param context the JBI environment mContext
     *
     * @throws javax.jbi.JBIException if the transformation engine is unable to
     *         initialize.
     */
    public void init(ComponentContext context)
    throws JBIException {
        compCtx = context;
        try{
            log = compCtx.getLogger("LifeCycle",null);
            log.finest(String.format("%s LifeCycle initialized ",compCtx.getComponentName()));
        }catch (JBIException jbi){
            jbi.printStackTrace();
        }
    }
    
    /**
     * Shut down the component. This performs clean-up, releasing all run-time resources
     * used by the component. Once this method has been called, init(ComponentContext)
     * must be called before the component can be started again with a call to start().
     *
     * @throws javax.jbi.JBIException if component is unable to
     *         shut down.
     */
    public void shutDown()
    throws JBIException {
        log.finest(String.format("%s LifeCycle shutdown ",compCtx.getComponentName()));
    }
    
    /**
     * Start the component. This makes the component ready to process messages.
     * This method is called after init(ComponentContext), both when the component is
     * being started for the first time and when the component is being restarted
     * after a previous call to shutDown(). If stop() was called previously but
     * shutDown() was not, start() can be called again without another call to
     * init(ComponentContext).
     *
     * @throws javax.jbi.JBIException if the transformation engine is unable to
     *         start.
     */
    public void start()
    throws JBIException {
        log.finest(String.format("%s LifeCycle started ",compCtx.getComponentName()));
    }
    
    /**
     * Stop the component. This makes the component stop accepting messages for processing.
     * After a call to this method, start() may be called again without first
     * calling init(ComponentContext).
     *
     * @throws javax.jbi.JBIException if the transformation engine is unable to
     *         stop.
     */
    public void stop() throws JBIException {
        log.finest(String.format("%s LifeCycle stopped ",compCtx.getComponentName()));
    }
}



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
 * CrapSUManager.java
 *
 */
package com.newarchetype;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.jbi.JBIException;
import javax.jbi.component.ComponentContext;
import javax.jbi.management.DeploymentException;
import javax.jbi.component.ServiceUnitManager;


/**
 * Service unit manager for SE.
 *
 *
 */
public class CrapSUManager implements ServiceUnitManager {
    /**
     * Component Context.
     */
    private ComponentContext compCtx;
    
    /**
     * Logger.
     */
    private Logger log;
   
    /**
     * Creates a new instance of CrapSUManager.
     */
    public CrapSUManager(ComponentContext compCtx) {
        this.compCtx = compCtx;
        try{
            log = compCtx.getLogger("SUManager",null);
            log.finest(String.format("%s SU Manager intitialized ",compCtx.getComponentName()));
        }catch (JBIException jbi){
            jbi.printStackTrace();
        }
    }
    
    /**
     * This method is called by the framework when a deployment request comes
     * for the file binding.
     *
     * @param suId Service unit id.
     * @param suPath Service unit path.
     *
     * @return status message.
     *
     * @throws javax.jbi.management.DeploymentException deploy exception.
     * @throws DeploymentException
     */
    public String deploy(
            String suId,
            String suPath)
            throws DeploymentException {
        log.finest(String.format("deploying service unit %s to path %s",suId,suPath ));
        boolean success = true;
        
        if (success){
            log.finest("deploy successful");
            
            return  new ComponentDeploymentMessage(compCtx.getComponentName(), "deploy", true).generateDeploymentMessage();
        }else{
            ComponentDeploymentMessage msg = new ComponentDeploymentMessage(compCtx.getComponentName(), "deploy", false);
            msg.addExceptInfo(1,new Exception("Deploy failed"), null, "Component Deploy Failed");
            //throw new DeployException(msg.generateDeploymentMessage());
            return msg.generateDeploymentMessage();
        }
    }
    
    /**
     * This method is called by the framework when the deployment has to be \
     * initialised , just before starting it.
     *
     * @param suId service unit id.
     * @param suPath service unit path.
     *
     * @throws javax.jbi.management.DeploymentException DeploymentException
     * @throws DeploymentException
     */
    public void init(
            String suId,
            String suPath)
            throws DeploymentException {
        log.finest(String.format("initializing service unit %s to path %s",suId,suPath ));
        
    }
    
    /**
     * Shuts down the service unit.
     *
     * @param str su id.
     *
     * @throws javax.jbi.management.DeploymentException depl exception
     */
    public void shutDown(String suId)
    throws DeploymentException {
        log.finest(String.format("shutting down service unit %s" ));
    }
    
    /**
     * Starts an SU.
     *
     * @param suId su id.
     *
     * @throws javax.jbi.management.DeploymentException DeploymentException
     * @throws DeploymentException
     */
    public void start(String suId)
    throws DeploymentException {
        log.finest(String.format("starting service unit %s",suId));
    }
    
    /**
     * Stops an SU.
     *
     * @param suId su id.
     *
     * @throws javax.jbi.management.DeploymentException DeploymentException
     * @throws DeploymentException
     */
    public void stop(String suId)
    throws DeploymentException {
        log.finest(String.format("stopping service unit %s",suId));
    }
    
    /**
     * Undeploys an SU.
     *
     * @param suId su id.
     * @param suPath su path.
     *
     * @return management message string.
     *
     * @throws javax.jbi.management.DeploymentException DeploymentException
     * @throws DeploymentException
     */
    public String undeploy(
            String suId,
            String suPath)
            throws DeploymentException {
        log.finest(String.format("undeploying service unit %s to path %s",suId,suPath ));
        boolean success = true;
        
        if (success){
            log.finest("undeploy successfull");
            return  new ComponentDeploymentMessage(compCtx.getComponentName(), "undeploy", true).generateDeploymentMessage();
        }else{
            ComponentDeploymentMessage msg = new ComponentDeploymentMessage(compCtx.getComponentName(), "undeploy", false);
            msg.addExceptInfo(1,new Exception("Deploy failed"), null, "Component Deploy Failed");
            //throw new DeployException(msg.generateDeploymentMessage());
            return msg.generateDeploymentMessage();
        }
    }
    
    
    
    public class ComponentDeploymentMessage {
        StringBuilder msg = new StringBuilder();
        boolean setMsgType =false;
        boolean taskStatusMsg = false;
        
        public ComponentDeploymentMessage(String componentName, String taskID, boolean result ){
            msg .append("<component-task-result>")
            .append("<component-name>Crap</component-name>")
            .append("<component-task-result-details")
            .append("xmlns=\"http://java.sun.com/xml/ns/jbi/management-message\">")
            .append("<task-result-details>")
            .append("<task-id>")
            .append(taskID)
            .append("</task-id>")
            .append("<task-result>")
            .append(result ? "SUCCESS" : "FAILED")
            .append("</task-result>");
        }
        
        public void setMsgType(String type){
            if (!setMsgType){
                setMsgType = true;
                msg.append("<message-type>")
                .append(type)
                .append("</message-type>");
            }
        }
        
        public void startTaskStatusMsg(){
            if (!setMsgType){
                setMsgType("ERROR");
            }
            if (! taskStatusMsg){
                taskStatusMsg = true;
                msg.append("<task-status-msg>");
            }
        }

        public void endTaskStatusMsg(){
            if (taskStatusMsg){
                taskStatusMsg = false;
                msg.append("</task-status-msg>");
            }
            
        }
        
        public void addMsgLocInfo(String locToken, String locMsg, String ... locParam ){
            if (! taskStatusMsg){
                startTaskStatusMsg();
            }
            msg  .append("<msg-loc-info>")
            .append("<loc-token>")
            .append(locToken)
            .append("</loc-token>")
            .append("<loc-message>")
            .append(locMsg)
            .append("<loc-message>");
            for (String parm : locParam){
                msg.append("<loc-param>")
                .append(parm)
                .append("</loc-param>");
            }
            msg.append("</msg-loc-info>");
        }
        
        public void addExceptInfo(int nestLevel,Throwable stack, String locToken, String locMsg, String ... locParam ){
            if (taskStatusMsg){
                endTaskStatusMsg();
            }
            msg  .append("<exception-info>")
            .append("<nesting-level>")
            .append(nestLevel)
            .append("</nesting-level>");
            
            msg  .append("<msg-loc-info>")
            .append("<loc-token>")
            .append(locToken)
            .append("</loc-token>")
            .append("<loc-message>")
            .append(locMsg)
            .append("<loc-message>");
            for (String parm : locParam){
                msg.append("<loc-param>")
                .append(parm)
                .append("</loc-param>");
            }
            msg.append("</msg-loc-info>");
            
            StringWriter writer = new StringWriter();
            stack.printStackTrace(new PrintWriter(writer));
            msg
                    .append("<stack-trace>")
                    .append(writer.toString())
                    .append("</stack-trace>")
                    .append("<exception-info>");
        }
        
        public String generateDeploymentMessage(){
            if (taskStatusMsg){
                endTaskStatusMsg();
            }
            return msg  .append("</task-result-details>")
            .append("</component-task-result-details")
            .append("</component-task-result>").toString();
            
        }
    }
    
}




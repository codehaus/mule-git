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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.db.JbpmSession;
import org.jbpm.db.JbpmSessionFactory;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jpdl.par.ProcessArchiveDeployer;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.mule.util.ClassUtils;

public class JBpmUtil {

    /**
     * Deploy a new process definition.
     */
    public static void deployProcess(JbpmSessionFactory jbpmSessionFactory, String processDefinitionFile) throws FileNotFoundException, IOException {
        deployProcessFromStream(jbpmSessionFactory,
                ClassUtils.getResourceAsStream(processDefinitionFile, JBpmUtil.class,
                                                /*tryAsFile*/true, /*tryAsUrl*/true));
    }

    public static void deployProcessFromStream(JbpmSessionFactory jbpmSessionFactory, InputStream processDefinition) throws FileNotFoundException, IOException {
        deployProcess(jbpmSessionFactory,
                      ProcessDefinition.parseXmlInputStream(processDefinition));
    }

    private static void deployProcess(JbpmSessionFactory jbpmSessionFactory, ProcessDefinition processDefinition) {
        ProcessArchiveDeployer.deployProcessDefinition(processDefinition, jbpmSessionFactory);
    }

    /**
     * Start a new process instance.
     *
     * @return the newly created ProcessInstance
     */
    public static ProcessInstance startProcess(JbpmSessionFactory jbpmSessionFactory, String processName) throws Exception {
        return startProcess(jbpmSessionFactory, processName, /*variables*/null);
    }

    public static ProcessInstance startProcess(JbpmSessionFactory jbpmSessionFactory, String processName, Map variables) throws Exception {
        ProcessInstance processInstance = null;
        log.info("Starting process " + processName);

        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            jbpmSession.beginTransaction();
            ProcessDefinition processDefinition =
                jbpmSession.getGraphSession().findLatestProcessDefinition(processName);

            processInstance = new ProcessInstance(processDefinition);

            // Set any process variables.
            if (variables != null && !variables.isEmpty()) {
                processInstance.getContextInstance().addVariables(variables);
            }
            processInstance.getContextInstance().addVariables(variables);

            // Leave the start state.
            processInstance.signal();

            jbpmSession.getGraphSession().saveProcessInstance(processInstance);
            jbpmSession.commitTransactionAndClose();
        } catch (Exception e) {
            jbpmSession.rollbackTransactionAndClose();
            throw e;
        }
        log.info("New process ID = " + processInstance.getId());
        return processInstance;
    }

    /**
     * Look up a process instance by its ID.
     * */
    public static ProcessInstance loadProcess(JbpmSessionFactory jbpmSessionFactory, ProcessInstance processInstance) {
        return loadProcess(jbpmSessionFactory, processInstance.getId());
    }

    public static ProcessInstance loadProcess(JbpmSessionFactory jbpmSessionFactory, long instanceId) {
        ProcessInstance processInstance = null;

        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            processInstance = jbpmSession.getGraphSession().loadProcessInstance(instanceId);
        } finally {
            jbpmSession.close();
        }
        return processInstance;
    }

    /**
     * Look up all process instances of a given type (process definition).
     * */
    public static List/*<ProcessInstance>*/ loadProcesses(JbpmSessionFactory jbpmSessionFactory, String processName) {
        List/*<ProcessInstance>*/ processInstances = null;

        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            ProcessDefinition processDefinition =
                jbpmSession.getGraphSession().findLatestProcessDefinition(processName);
            processInstances = jbpmSession.getGraphSession().findProcessInstances(processDefinition.getId());
        } finally {
            jbpmSession.close();
        }
        return processInstances;
    }

    /**
     * Advance a process instance one step.
     *
     * @return the updated ProcessInstance
     * */
    public static ProcessInstance advanceProcess(JbpmSessionFactory jbpmSessionFactory, long instanceId) throws Exception {
        return advanceProcess(jbpmSessionFactory, instanceId, /*variables*/null, /*transition*/null);
    }

    /**
     * Advance a process instance one step.
     *
     * @return the updated ProcessInstance
     * */
    public static ProcessInstance advanceProcess(JbpmSessionFactory jbpmSessionFactory, long instanceId, Map variables, String transition) throws Exception {
        ProcessInstance processInstance = null;

        log.info("Advancing process, ID = " + instanceId);
        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            jbpmSession.beginTransaction();

            // Look up the process instance from the database.
            processInstance = jbpmSession.getGraphSession().loadProcessInstance(instanceId);

            // Set any process variables.
            // Note: addVariables() will replace the old value of a variable if it
            // already exists.
            if (variables != null && !variables.isEmpty()) {
                processInstance.getContextInstance().addVariables(variables);
            }

            // Advance the workflow.
            if (transition != null) {
                processInstance.signal(transition);
            } else {
                processInstance.signal();
            }

            // Save the process state back to the database.
            jbpmSession.getGraphSession().saveProcessInstance(processInstance);
            jbpmSession.commitTransactionAndClose();

        } catch (Exception e) {
            jbpmSession.rollbackTransactionAndClose();
            throw e;
        }
        return processInstance;
    }

    /**
     * Update the context for a process instance.
     *
     * @return the updated ProcessInstance
     * */
    public static ProcessInstance updateProcess(JbpmSessionFactory jbpmSessionFactory, long instanceId, Map variables) throws Exception {
        ProcessInstance processInstance = null;

        log.info("Updating process context, ID = " + instanceId);
        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            jbpmSession.beginTransaction();

            // Look up the process instance from the database.
            processInstance = jbpmSession.getGraphSession().loadProcessInstance(instanceId);

            // Set any process variables.
            // Note: addVariables() will replace the old value of a variable if it
            // already exists.
            if (variables != null && !variables.isEmpty()) {
                processInstance.getContextInstance().addVariables(variables);
            }

            // Save the process state back to the database.
            jbpmSession.getGraphSession().saveProcessInstance(processInstance);
            jbpmSession.commitTransactionAndClose();

        } catch (Exception e) {
            jbpmSession.rollbackTransactionAndClose();
            throw e;
        }
        return processInstance;
    }

    public static List/*<TaskInstance>*/ loadTasks(JbpmSessionFactory jbpmSessionFactory, ProcessInstance process) {
        List/*<TaskInstance>*/ taskInstances = null;

        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            taskInstances = jbpmSession.getTaskMgmtSession().findTaskInstancesByToken(process.getRootToken().getId());
        } finally {
            jbpmSession.close();
        }
        return taskInstances;
    }

    public static void completeTask(JbpmSessionFactory jbpmSessionFactory, TaskInstance task) {
        completeTask(jbpmSessionFactory, task, /*transition*/null);
    }

    public static void completeTask(JbpmSessionFactory jbpmSessionFactory, TaskInstance task, String transition) {
        JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSession();
        try {
            jbpmSession.beginTransaction();
            task = jbpmSession.getTaskMgmtSession().loadTaskInstance(task.getId());
            if (transition != null) {
                task.end(transition);
            } else {
                task.end();
            }
            jbpmSession.commitTransactionAndClose();
        } catch (Exception e) {
            jbpmSession.rollbackTransactionAndClose();
        }
    }

    public static String getState(ProcessInstance process) {
        return process.getRootToken().getNode().getName();
    }

    public static String getState(JbpmSessionFactory jbpmSessionFactory, long instanceId) {
        ProcessInstance process = loadProcess(jbpmSessionFactory, instanceId);
        return getState(process);
    }

    public static boolean processHasEnded(JbpmSessionFactory jbpmSessionFactory, long instanceId) {
        return loadProcess(jbpmSessionFactory, instanceId).hasEnded();
    }

    private static Log log = LogFactory.getLog(JBpmUtil.class);
}

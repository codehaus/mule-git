package org.jbpm.mule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.mule.providers.bpm.BPMS;
import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.Lifecycle;
import org.mule.util.IOUtils;
import org.mule.util.NumberUtils;

public class Jbpm implements BPMS, Lifecycle {
    protected static transient Log logger = LogFactory.getLog(Jbpm.class);

    protected JbpmConfiguration jbpmConfiguration = null;

    /////////////////////////////////////////////////////////////////////////////
    // Lifecycle methods
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Creates the Mule interface and will instantiate a new jBPM instance.
     */
    public Jbpm() { }

    /**
     * Creates the Mule interface based on an already-initialized jBPM instance
     * @param jbpmSessionFactory - the already-initialized jBPM instance
     */
    public Jbpm(JbpmConfiguration jbpmConfiguration) {
        setJbpmConfiguration(jbpmConfiguration);
    }

    public void start() throws UMOException {
        // nothing to do
    }

    public void stop() throws UMOException {
        // nothing to do
    }

    public void dispose() {
        // nothing to do
    }

    /////////////////////////////////////////////////////////////////////////////
    // Process status / lookup
    /////////////////////////////////////////////////////////////////////////////

    public Object getId(Object process) throws Exception {
        return new Long(((ProcessInstance) process).getId());
    }

    public Object getState(Object process) throws Exception {
        return ((ProcessInstance) process).getRootToken().getNode().getName();
    }

    public boolean hasEnded(Object process) throws Exception {
        return ((ProcessInstance) process).hasEnded();
    }

    /**
     * Look up an already-running process instance.
     * @return the ProcessInstance
     * */
    public Object lookupProcess(Object processId) throws Exception {
        ProcessInstance processInstance = null;

        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            // Look up the process instance from the database.
            processInstance = jbpmContext.getGraphSession().loadProcessInstance(NumberUtils.toLong(processId));
        } finally {
            jbpmContext.close();
        }
        return processInstance;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Process manipulation
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Start a new process.
     * @return the newly-created ProcessInstance
     */
    public synchronized Object startProcess(Object processType) throws Exception {
        return startProcess(processType, /*processVariables*/null);
    }

    /**
     * Start a new process.
     * @return the newly-created ProcessInstance
     */
    public synchronized Object startProcess(Object processType, Map processVariables) throws Exception {
        ProcessInstance processInstance = null;

        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            ProcessDefinition processDefinition =
                jbpmContext.getGraphSession().findLatestProcessDefinition((String) processType);
            if (processDefinition == null) throw new IllegalArgumentException("No process definition found for process " + processType);

            processInstance = new ProcessInstance(processDefinition);

            // Set any process variables.
            if (processVariables != null && !processVariables.isEmpty()) {
                processInstance.getContextInstance().addVariables(processVariables);
            }
            processInstance.getContextInstance().addVariables(processVariables);

            // Leave the start state.
            processInstance.signal();

            jbpmContext.save(processInstance);

        } catch (Exception e) {
            jbpmContext.setRollbackOnly();
            throw e;
        } finally {
            jbpmContext.close();
        }
        return processInstance;
    }

    /**
     * Advance a process instance one step.
     * @return the updated ProcessInstance
     * */
    public synchronized Object advanceProcess(Object processId) throws Exception {
        return advanceProcess(processId, /*transition*/null, /*processVariables*/null);
    }

    /**
     * Advance a process instance one step.
     * @return the updated ProcessInstance
     * */
    public synchronized Object advanceProcess(Object processId, Object transition, Map processVariables) throws Exception {
        ProcessInstance processInstance = null;

        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            // Look up the process instance from the database.
            processInstance = jbpmContext.getGraphSession().loadProcessInstance(NumberUtils.toLong(processId));

            if (processInstance.hasEnded()) {
                throw new IllegalStateException("Process cannot be advanced because it has already terminated, processId = " + processId);
            }

            // Set any process variables.
            // Note: addVariables() will replace the old value of a variable if it
            // already exists.
            if (processVariables != null && !processVariables.isEmpty()) {
                processInstance.getContextInstance().addVariables(processVariables);
            }

            // Advance the workflow.
            if (transition != null) {
                processInstance.signal((String) transition);
            } else {
                processInstance.signal();
            }

            // Save the process state back to the database.
            jbpmContext.save(processInstance);

        } catch (Exception e) {
            jbpmContext.setRollbackOnly();
            throw e;
        } finally {
            jbpmContext.close();
        }
        return processInstance;
    }

    /**
     * Update the variables for a process instance.
     * @return the updated ProcessInstance
     * */
    public synchronized Object updateProcess(Object processId, Map processVariables) throws Exception {
        ProcessInstance processInstance = null;

        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            // Look up the process instance from the database.
            processInstance = jbpmContext.getGraphSession().loadProcessInstance(NumberUtils.toLong(processId));

            // Set any process variables.
            // Note: addVariables() will replace the old value of a variable if it
            // already exists.
            if (processVariables != null && !processVariables.isEmpty()) {
                processInstance.getContextInstance().addVariables(processVariables);
            }

            // Save the process state back to the database.
            jbpmContext.save(processInstance);

        } catch (Exception e) {
            jbpmContext.setRollbackOnly();
            throw e;
        } finally {
            jbpmContext.close();
        }
        return processInstance;
    }

    /**
     * Delete a process instance.
     */
    public synchronized void abortProcess(Object processId) throws Exception {
        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            jbpmContext.getGraphSession().deleteProcessInstance(NumberUtils.toLong(processId));

        } catch (Exception e) {
            jbpmContext.setRollbackOnly();
            throw e;
        } finally {
            jbpmContext.close();
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    // Miscellaneous
    /////////////////////////////////////////////////////////////////////////////

    /**
     * Deploy a new process definition.
     */
    public void deployProcess(String processDefinitionFile) throws FileNotFoundException, IOException {
        deployProcessFromStream(
                IOUtils.getResourceAsStream(processDefinitionFile, getClass()));
    }

    public void deployProcessFromStream(InputStream processDefinition) throws FileNotFoundException, IOException {
        deployProcess(ProcessDefinition.parseXmlInputStream(processDefinition));
    }

    private void deployProcess(ProcessDefinition processDefinition) {
        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            jbpmContext.deployProcessDefinition(processDefinition);
        } finally {
            jbpmContext.close();
        }
    }

    public List/*<TaskInstance>*/ loadTasks(ProcessInstance process) {
        List/*<TaskInstance>*/ taskInstances = null;

        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            taskInstances = jbpmContext.getTaskMgmtSession().findTaskInstancesByToken(process.getRootToken().getId());
        } finally {
            jbpmContext.close();
        }
        return taskInstances;
    }

    public synchronized void completeTask(TaskInstance task) {
        completeTask(task, /*transition*/null);
    }

    public synchronized void completeTask(TaskInstance task, String transition) {
        JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
        try {
            task = jbpmContext.getTaskMgmtSession().loadTaskInstance(task.getId());
            if (transition != null) {
                task.end(transition);
            } else {
                task.end();
            }
        } finally {
            jbpmContext.close();
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    // Getters and setters
    /////////////////////////////////////////////////////////////////////////////

    public JbpmConfiguration getJbpmConfiguration() {
        return jbpmConfiguration;
    }

    public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {
        this.jbpmConfiguration = jbpmConfiguration;
    }
}

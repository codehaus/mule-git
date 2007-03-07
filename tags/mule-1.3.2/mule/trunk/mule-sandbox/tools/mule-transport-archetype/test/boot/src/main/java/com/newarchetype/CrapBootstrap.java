package com.newarchetype;

import java.util.logging.Logger;

import javax.jbi.component.Bootstrap;
import javax.jbi.component.InstallationContext;


/**
 * Implemented by a  SE to provide any special processing required at
 * install/uninstall time. Things such as creation/deletion of directories,
 * files, database tables could be done by the onInstall() and onUninstall()
 * methods, respectively. Also allows the BPE to terminate the installation or
 * uninstallation in the event of an error.
 *
 * 
 */
public class CrapBootstrap
        implements Bootstrap {
    /**
     * Creates a new instance of CrapBootstrap.
     */
    public CrapBootstrap() {
    }
    
    /**
     * Get the JMX ObjectName for the optional installation configuration MBean
     * for this BPE. If there is none, the value is null.
     *
     * @return ObjectName the JMX object name of the installation configuration
     *         MBean or null if there is no MBean.
     */
    public javax.management.ObjectName getExtensionMBeanName() {
        return null;
    }
    
    /**
     * Cleans up any resources allocated by the bootstrap implementation,
     * including deregistration of the extension MBean, if applicable. This
     * method will be called after the onInstall() or onUninstall() method is
     * called, whether it succeeds or fails.
     *
     * @throws javax.jbi.JBIException when cleanup processing fails to complete
     *         successfully.
     */
    public void cleanUp()
    throws javax.jbi.JBIException {
    }
    
    /**
     * Called to initialize the BPE bootstrap.
     *
     * @param installContext is the context containing information from the
     *        install command and from the BPE jar file.
     *
     * @throws javax.jbi.JBIException when there is an error requiring that the
     *         installation be terminated.
     */
    public void init(InstallationContext installContext)
    throws javax.jbi.JBIException {
    }
    
    /**
     * Called at the beginning of installation of StockQuoteEngine. For this
     * sample engine, all the required installation tasks have been taken care
     * by the InstallationService.
     *
     * @throws javax.jbi.JBIException when there is an error requiring that the
     *         installation be terminated.
     */
    public void onInstall()
    throws javax.jbi.JBIException {
    }
    
    /**
     * Called at the beginning of uninstallation of StockQuoteEngine. For this
     * sample engine, all the required uninstallation tasks have been taken
     * care of by the InstallationService
     *
     * @throws javax.jbi.JBIException when there is an error requiring that the
     *         uninstallation be terminated.
     */
    public void onUninstall()
    throws javax.jbi.JBIException {
    }
}

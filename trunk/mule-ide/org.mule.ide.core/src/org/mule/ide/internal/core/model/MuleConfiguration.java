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
 * $Id$
 */
package org.mule.ide.internal.core.model;

import org.mule.ide.core.MuleCorePlugin;
import org.mule.ide.core.model.IMuleConfiguration;
import org.mule.ide.core.model.IMuleModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Default Mule configuration implementation.
 */
public class MuleConfiguration extends MuleModelElement implements IMuleConfiguration {

    /** The parent model */
    private IMuleModel parent;

    /** Unique id */
    private String id;

    /** Description */
    private String description;

    /** The relative path to the config file */
    private String relativePath;

    /** Project relative path to config file */
    private IPath filePath;

    /** Error indicating that a config file was not found */
    private static final String ERROR_CONFIG_NOT_FOUND = "The Mule configuration file was not found: ";

    /**
     * Create a new Mule configuration.
     *
     * @param parent the parent model
     * @param id the unique id
     * @param description the description
     * @param relativePath the project-relative path to the config file
     */
    public MuleConfiguration(IMuleModel parent, String id, String description, String relativePath) {
        this.parent = parent;
        this.setId(id);
        this.setDescription(description);
        this.relativePath = relativePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleConfiguration#refresh()
     */
    public IStatus refresh() {
        setStatus(Status.OK_STATUS);
        IFile configFile = parent.getProject().getFile(relativePath);
        setFilePath(configFile.getProjectRelativePath());
        if (!configFile.exists()) {
            setStatus(MuleCorePlugin.getDefault().createErrorStatus(
                    ERROR_CONFIG_NOT_FOUND + relativePath, null));
        } else {
            try {
                // TODO: Call the Builder to do this - but why?

            } catch (Exception e) {
                MuleCorePlugin.getDefault().logException(e.getMessage(), e);
            } finally {
            }
        }
        return getStatus();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleModelElement#getLabel()
     */
    public String getLabel() {
        return getRelativePath() + " [" + getDescription() + "]";
    }

    /**
     * Sets the 'id' field.
     *
     * @param id The 'id' value.
     */
    protected void setId(String id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleConfiguration#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the 'description' field.
     *
     * @param description The 'description' value.
     */
    protected void setDescription(String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleConfiguration#getDescription()
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the 'filePath' field.
     *
     * @param filePath The 'filePath' value.
     */
    protected void setFilePath(IPath filePath) {
        this.filePath = filePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleConfiguration#getFilePath()
     */
    public IPath getFilePath() {
        return filePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleConfiguration#getRelativePath()
     */
    public String getRelativePath() {
        return relativePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.ide.core.model.IMuleModelElement#getMuleModel()
     */
    public IMuleModel getMuleModel() {
        return this.parent;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        IMuleConfiguration other = (IMuleConfiguration) o;
        return getRelativePath().compareTo(other.getRelativePath());
    }
}
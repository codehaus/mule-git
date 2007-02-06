/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.model;

import org.eclipse.core.runtime.IPath;
import org.mule.ide.core.exception.MuleModelException;
import org.mule.schema.DocumentRoot;

/**
 * Wraps the details of a single mule configuration file.
 */
public interface IMuleConfiguration extends IMuleModelElement, Comparable {

    /**
     * Get the unique configuration id.
     *
     * @return the unique id
     */
    public String getId();

    /**
     * Get the configuration description.
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Gets the project-relative path to the config file.
     *
     * @return the path
     */
    public String getRelativePath();

    /**
     * Get the project-relative IPath to the configuration file.
     *
     * @return the project-relative path
     */
    public IPath getFilePath();

    /**
     * Get the underlying EMF model document root object.
     *
     * @return the EMF model
     * @throws MuleModelException
     */
    public DocumentRoot getConfigDocument() throws MuleModelException;
}
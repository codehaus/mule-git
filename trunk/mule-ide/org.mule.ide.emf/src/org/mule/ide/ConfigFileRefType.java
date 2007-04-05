/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config File Ref Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.ConfigFileRefType#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.MuleIDEPackage#getConfigFileRefType()
 * @model extendedMetaData="name='config-file-ref_._type' kind='empty'"
 * @generated
 */
public interface ConfigFileRefType extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.mule.ide.MuleIDEPackage#getConfigFileRefType_Id()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.mule.ide.ConfigFileRefType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

} // ConfigFileRefType

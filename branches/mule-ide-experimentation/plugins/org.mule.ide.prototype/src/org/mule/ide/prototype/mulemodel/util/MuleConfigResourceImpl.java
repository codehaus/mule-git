/*****************************************************************************
 * Copyright (c) 2005 StrutsBox.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    StrutsBox - initial API and implementation
 *****************************************************************************/
/*
 * $Id: StrutsConfigResourceImpl.java,v 1.3 2005/07/21 15:21:01 daniel_rohe Exp $
 */
package org.mule.ide.prototype.mulemodel.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;
import org.mule.ide.prototype.mulemodel.MuleConfig;
import org.mule.ide.prototype.translators.MuleConfigConstants;
import org.mule.ide.prototype.translators.MuleConfigEntityResolver;
import org.mule.ide.prototype.translators.MuleConfigTranslator;
import org.mule.ide.prototype.translators.MuleConfigXmlMapping;
import org.xml.sax.EntityResolver;

/**
 * The class <code>StrutsConfigResourceImpl</code> provides a resource
 * implementation for the StrutsConfig model.
 * 
 * @author Daniel
 */
public class MuleConfigResourceImpl extends TranslatorResourceImpl {

	/**
	 * The class <code>RootVersionAdapter</code> provides a notification that
	 * synchronizes the version of the root object in the resource with the
	 * version from the resource after the root object is added to the resource.
	 * 
	 * @author Daniel
	 */
	private class RootVersionAdapter extends AdapterImpl {

		/*
		 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
		 */
		public boolean isAdapterForType(Object type) {
			return super.isAdapterForType(type);
		}

		/*
		 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		public void notifyChanged(Notification msg) {
			if (msg.getFeatureID(null) == RESOURCE__CONTENTS
					&& msg.getEventType() == Notification.ADD) {
				((MuleConfigResourceImpl) msg.getNotifier())
						.syncVersionOfRootObject();
				((Notifier) msg.getNotifier()).eAdapters().remove(this);
			}
		}

	}

	/**
	 * entity resolver
	 */
	private EntityResolver entityResolver;

	/**
	 * Creates a new <code>StrutsConfigResourceImpl</code>.
	 * 
	 * @param renderer
	 *            resource renderer
	 */
	public MuleConfigResourceImpl(Renderer renderer) {
		super(renderer);
	}

	/**
	 * Creates a new <code>StrutsConfigResourceImpl</code>.
	 * 
	 * @param uri
	 *            resource uri
	 * @param renderer
	 *            resource renderer
	 */
	public MuleConfigResourceImpl(URI uri, Renderer renderer) {
		super(uri, renderer);
	}

	/**
	 * Returns a string representation of the version ID.
	 * 
	 * @return string representation of version ID
	 */
	public String getVersionString() {
		switch (getVersionID()) {
		case MuleConfigConstants.VERSION_1_0_ID:
			return MuleConfigConstants.VERSION_1_0_TEXT;
		default:
			return MuleConfigConstants.VERSION_1_0_TEXT;
		}
	}

	/**
	 * Synchronizes the version attribute of the root object with the version
	 * extracted from the resource.
	 */
	protected void syncVersionOfRootObject() {
		MuleConfig strutsConfig = (MuleConfig) getRootObject();
		if (strutsConfig != null) {
			String version = strutsConfig.getVersion();
			String newVersion = getVersionString();
			if (!newVersion.equals(version)) {
				strutsConfig.setVersion(newVersion);
			}
		}
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl#getDefaultSystemId()
	 */
	protected String getDefaultSystemId() {
		return MuleConfigConstants.MULE_CONFIG_SYSTEM_ID_1_0;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl#getDefaultPublicId()
	 */
	protected String getDefaultPublicId() {
		return MuleConfigConstants.MULE_CONFIG_PUBLIC_ID_1_0;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl#getDefaultVersionID()
	 */
	protected int getDefaultVersionID() {
		return MuleConfigConstants.VERSION_1_0_ID;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResource#getRootTranslator()
	 */
	public Translator getRootTranslator() {
		return MuleConfigTranslator.getInstance();
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResource#getEntityResolver()
	 */
	public EntityResolver getEntityResolver() {
		if (entityResolver == null) {
			entityResolver = new MuleConfigEntityResolver();
		}
		return entityResolver;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResource#getDoctype()
	 */
	public String getDoctype() {
		return MuleConfigXmlMapping.MULE_CONFIG;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResource#setDoctypeValues(java.lang.String,
	 *      java.lang.String)
	 */
	public void setDoctypeValues(String publicId, String systemId) {
		super.setDoctypeValues(publicId, systemId);

		// here we decide based on the given public id which struts-config
		// version gets loaded
		int version = MuleConfigConstants.VERSION_1_0_ID;
		setVersionID(version);
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl#initializeContents()
	 */
	protected void initializeContents() {
		super.initializeContents();
		eAdapters().add(new RootVersionAdapter());
	}

}

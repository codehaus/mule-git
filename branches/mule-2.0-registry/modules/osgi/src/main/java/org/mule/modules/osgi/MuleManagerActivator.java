package org.mule.modules.osgi;

import org.mule.MuleManager;
import org.mule.umo.manager.UMOManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class MuleManagerActivator implements BundleActivator {
	
	private UMOManager manager;

	public void start(BundleContext bc) throws Exception {
		manager = MuleManager.getInstance();
		manager.start();
	}

	public void stop(BundleContext bc) throws Exception {
		manager.stop();
	}
}

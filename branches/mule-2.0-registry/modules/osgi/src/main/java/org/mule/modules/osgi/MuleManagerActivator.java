package org.mule.modules.osgi;

import org.mule.MuleManager;
import org.mule.umo.manager.UMOManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class MuleManagerActivator implements BundleActivator {
	
	private UMOManager manager;
	//private ConcurrentHashMap springContexts = new ConcurrentHashMap();

	public void start(BundleContext bc) throws Exception {
		//bc.addBundleListener(new MuleBundleListener());		
		manager = MuleManager.getInstance();
		manager.start();
	}

	public void stop(BundleContext bc) throws Exception {
		manager.stop();
	}

//	class MuleBundleListener implements BundleListener {
//	    public void bundleChanged(BundleEvent event) {
//	    	if (event.getType() == BundleEvent.STARTING) {
//	    		System.out.println("Bundle starting: " + event.getBundle().getSymbolicName());
//	    		//springContexts.put(event.getBundle().getBundleId(), new OsgiBundleXmlApplicationContext(resources));
//	    	}
//	    	else if (event.getType() == BundleEvent.STOPPING) {
//	    		System.out.println("Bundle stopping: " + event.getBundle().getSymbolicName());
//	    	}
//	    	else if (event.getType() == BundleEvent.INSTALLED) {
//	    		System.out.println("Bundle has been installed: " + event.getBundle().getSymbolicName());
//	    	}
//	    	else if (event.getType() == BundleEvent.UNINSTALLED) {
//	    		System.out.println("Bundle has been uninstalled: " + event.getBundle().getSymbolicName());
//	    	}
//	    	else if (event.getType() == BundleEvent.UPDATED) {
//	    		System.out.println("Bundle has been updated: " + event.getBundle().getSymbolicName());
//	    	}
//		}
//	}
}

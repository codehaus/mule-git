/* 
* $Header$
* $Revision$
* $Date$
* ------------------------------------------------------------------------------------------------------
* 
* Copyright (c) SymphonySoft Limited. All rights reserved.
* http://www.symphonysoft.com
* 
* The software in this package is published under the terms of the BSD
* style license a copy of which has been included with this distribution in
* the LICENSE.txt file. 
*
*/
package org.mule.extras.pxe;

import com.fs.pxe.sfwk.spi.InteractionHandler;
import com.fs.pxe.sfwk.spi.MessageExchangeEvent;
import com.fs.pxe.sfwk.spi.MessageExchangeException;
import com.fs.pxe.sfwk.spi.ProtocolAdapter;
import com.fs.pxe.sfwk.spi.ServiceConfig;
import com.fs.pxe.sfwk.spi.ServiceContext;
import com.fs.pxe.sfwk.spi.ServiceEvent;
import com.fs.pxe.sfwk.spi.ServiceProviderContext;
import com.fs.pxe.sfwk.spi.ServiceProviderException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MuleProtocolAdapter implements ProtocolAdapter {

	private Log logger = LogFactory.getLog(MuleProtocolAdapter.class);

	private boolean isRunning = false;

	/** Service provider context. */
	private ServiceProviderContext context;

	public String getProviderURI() {
		return this.context.getConfig().getProviderURI();
	}

	public boolean isRunning() throws ServiceProviderException {
		return this.isRunning;
	}

	public void activateService(ServiceContext service) throws ServiceProviderException {
		logger.info("Activating service: " + service);
		PxeComponent.getInstance().activateService(service);
	}

	public void deactivateService(ServiceContext service) throws ServiceProviderException {
		logger.info("Deactivating service: " + service);
		PxeComponent.getInstance().deactivateService(service);
	}

    public InteractionHandler createInteractionHandler(Class aClass) throws ServiceProviderException {
        return null;
    }

	public void deployService(ServiceConfig service) throws ServiceProviderException {
		logger.info("Deploying service: " + service);
	}

	public void undeployService(ServiceConfig service) throws ServiceProviderException {
		logger.info("Undeploying service: " + service);
	}

	public void initialize(ServiceProviderContext context) throws ServiceProviderException {
		logger.info("Initializing");
		this.context = context;
	}

	public void start() throws ServiceProviderException {
		logger.info("Starting");
		this.isRunning = true;
	}

	public void stop() throws ServiceProviderException {
		logger.info("Stopping");
		this.isRunning = false;
	}

	public void onServiceEvent(ServiceEvent serviceEvent) throws ServiceProviderException, MessageExchangeException {
		logger.info("Service event: " + serviceEvent);
		if (serviceEvent instanceof MessageExchangeEvent) {
			PxeComponent.getInstance().onMessageExchange((MessageExchangeEvent) serviceEvent);
		}
	}
}
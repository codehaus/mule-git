/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.jbi.engines.agila;

import org.apache.agila.bpel.engine.common.transaction.TransactionException;
import org.apache.log4j.Logger;

public class AgilaListener implements org.apache.agila.bpel.engine.common.event.AgilaListener {

	private Logger log = Logger.getLogger(AgilaListener.class);
	
	public void objectLifecycleEvent(int type, int subtype, String comment, Object object) {
		if (log.isDebugEnabled()) {
			log.debug("ObjectLifecycleEvent: type=" + type +  ", subtype=" + subtype + ", comment=" + comment + ", object=" + object);
		}
	}

	public void errorEvent(Exception e) {
		if (log.isDebugEnabled()) {
			log.debug("ErrorEvent", e);
		}
	}

	public void errorEvent(String message, String messageDetail, int type, Object origin) throws TransactionException {
		if (log.isDebugEnabled()) {
			log.debug("ErrorEvent: message=" + message + ", details=" + messageDetail + ", type=" + type + ", origin=" + origin);
		}
	}

}

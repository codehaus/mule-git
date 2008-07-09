package org.mule.module.sxc;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class ReceiveComponent implements Callable {

	public Object onCall(MuleEventContext eventContext) throws Exception {
		return Boolean.TRUE;
	}

}

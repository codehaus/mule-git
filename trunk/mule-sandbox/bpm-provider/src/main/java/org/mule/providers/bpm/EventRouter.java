package org.mule.providers.bpm;

import java.util.Map;

import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;

public interface EventRouter {
	public UMOMessage sendEvent(String url, Object payload, Map messageProperties) throws UMOException;
}

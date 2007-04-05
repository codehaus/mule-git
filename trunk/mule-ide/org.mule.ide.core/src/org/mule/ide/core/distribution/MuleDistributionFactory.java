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
 * 
 */
package org.mule.ide.core.distribution;

import java.io.File;
import java.io.IOException;

/**
 * @author jsm
 *
 */
public class MuleDistributionFactory {

	protected static MuleDistributionFactory INSTANCE;
	
	public static MuleDistributionFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MuleDistributionFactory();
		}
		return INSTANCE;
	}
	
	public IMuleDistribution createMuleDistribution(File location) {
		if (location == null) throw new IllegalArgumentException("location must not be null");
		if (location != null && location.isDirectory()) {
			try {
				MuleFullDistribution muleFullDistribution = new MuleFullDistribution(location);
				muleFullDistribution.initialize();
				return muleFullDistribution;
			} catch (IOException e) {
				// Drop through and get the dummy
			}
		}
		throw new IllegalArgumentException("Unexpected distribution location type");
	}
	
}

/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.distribution;

import java.io.File;
import java.io.IOException;

public interface IMuleBundle {
	String getName();
	IMuleBundle[] getMuleDependencies() throws IOException;
	String[] getOtherDependencies() throws IOException;
	File getSourcePath();
}

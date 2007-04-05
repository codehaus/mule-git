/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

/**
 * Utility functions for establishing the Mule classpath
 * 
 * @author Derek Adams
 */
public class MuleClasspathUtils {

	public static IClasspathEntry createMuleClasspathContainer(String hint, Collection selectedBundles) {
		IPath path = new Path(MuleCorePlugin.ID_MULE_CLASSPATH_CONTAINER);
		path = path.append(collectionToCommaString(selectedBundles));
		if (hint != null)
			path = path.append(hint);
		
		return JavaCore.newContainerEntry(path);
	}
	
    /**
     * Checks whether a jar is the main Mule jar.
     *
     * @param fileName
     * @return
     */
    protected static boolean isMuleJar(String fileName) {
        if ((fileName.startsWith("mule-")) && (fileName.endsWith(".jar"))) {
            return true;
        }
        return false;
    }
    
	public static Set commaStringToSet(String bundleSelectString2) {
		Set selection = new HashSet();
		StringTokenizer st = new StringTokenizer(bundleSelectString2, ",");
		while (st.hasMoreTokens()) selection.add(st.nextToken().trim());
		return selection;
	}

	public static String collectionToCommaString(Collection bundleSelection) {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = bundleSelection.iterator(); it.hasNext(); ) {
			if (sb.length() > 0) sb.append(',');
			sb.append(it.next().toString());
		}
		return sb.toString();
	}

}
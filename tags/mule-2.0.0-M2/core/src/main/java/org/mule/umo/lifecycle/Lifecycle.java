/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.lifecycle;

/**
 * <code>LifecyclePhase</code> adds lifecycle methods <code>start</code>,
 * <code>stop</code> and <code>dispose</code>.
 * TODO MULE-2113
 */
public interface Lifecycle extends Startable, Stoppable, Disposable
{
    // no additional methods - see super interfaces
}

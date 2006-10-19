/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import org.jbpm.JbpmConfiguration;
import org.jbpm.mule.Jbpm;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.manager.UMOManager;

public class SimpleJbpmTestCase extends AbstractMuleTestCase {

    private ProcessConnector connector;
    private BPMS bpms;

    public SimpleJbpmTestCase() {
        super();
        setDisposeManagerPerSuite(true);
    }

    protected void doSetUp() throws Exception {
        connector = new ProcessConnector();
        bpms = new Jbpm(JbpmConfiguration.getInstance());
        connector.setBpms(bpms);

        UMOManager manager = getManager(true);
        manager.registerConnector(connector);
        manager.start();
        connector.startConnector();

        super.doSetUp();
    }

    public void testCreateSimpleProcess() throws Exception {
    }
}

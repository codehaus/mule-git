/*
 * $Id
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

public class BpmConnectorTestCase extends AbstractConnectorTestCase {

    public BpmConnectorTestCase() {
        super();
        this.setDisposeManagerPerSuite(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.providers.AbstractConnectorTestCase#getConnector()
     */
    public UMOConnector getConnector() throws Exception {
        ProcessConnector c = new ProcessConnector();
        c.setName("ProcessConnector");
        c.initialise();
        return c;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.providers.AbstractConnectorTestCase#getValidMessage()
     */
    public Object getValidMessage() throws Exception {
        return "test";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.providers.AbstractConnectorTestCase#getTestEndpointURI()
     */
    public String getTestEndpointURI() {
        return "bpm://dummyProcess?processId=1234";
    }
}

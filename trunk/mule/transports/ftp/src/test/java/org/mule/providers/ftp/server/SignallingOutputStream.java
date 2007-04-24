/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp.server;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class SignallingOutputStream extends OutputStream {

    private ServerState state;
    private ByteArrayOutputStream delegate = new ByteArrayOutputStream();

    public SignallingOutputStream(ServerState state)
    {
        this.state = state;
    }

    public void write(int b) throws IOException {
        delegate.write(b);
    }

    // @Override
    public void close() throws IOException
    {
        delegate.close();
        state.setPayload(delegate.toByteArray());
        state.registerCompletion();
        super.close();
    }

}

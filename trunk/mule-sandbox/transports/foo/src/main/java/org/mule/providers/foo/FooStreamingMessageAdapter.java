/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.providers.streaming.StreamMessageAdapter;
import org.mule.umo.provider.OutputHandler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <code>Foo</code> Todo Document
 */
public class FooStreamingMessageAdapter extends StreamMessageAdapter
{

    public FooStreamingMessageAdapter(InputStream in)
    {
        super(in);
    }

    public FooStreamingMessageAdapter(InputStream in, OutputStream out)
    {
        super(in, out);
    }

    public FooStreamingMessageAdapter(OutputHandler handler)
    {
        super(handler);
    }

    public FooStreamingMessageAdapter(OutputStream out, OutputHandler handler)
    {
        super(out, handler);
    }

    public FooStreamingMessageAdapter(InputStream in, OutputStream out, OutputHandler handler)
    {
        super(in, out, handler);
    }


    /**
     * The release method is called by Mule to notify this adapter that it is no
     * longer needed. This method can be used to release any resources that a custom
     * StreamAdapter may have associated with it.
     */
    public void release()
    {
        // nothing to do
    }
}

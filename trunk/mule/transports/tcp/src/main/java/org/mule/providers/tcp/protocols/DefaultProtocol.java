/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.protocols;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The DefaultProtocol class is an application level tcp protocol that does nothing.
 * The socket is read until no more bytes are (momentariy) available
 * (previously the transfer buffer also had to be full on the previous read, which made
 * stronger requirements on the underlying network).  On slow networks
 * {@link org.mule.providers.tcp.protocols.EOFProtocol} and
 * {@link org.mule.providers.tcp.protocols.LengthProtocol} may be more reliable.
 *
 * <p>Writing simply writes the data to the socket.</p>
 */
public class DefaultProtocol extends ByteProtocol
{

    private static final Log logger = LogFactory.getLog(DefaultProtocol.class);
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private int bufferSize;

    public DefaultProtocol()
    {
        this(DEFAULT_BUFFER_SIZE);
    }

    public DefaultProtocol(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    public Object read(InputStream is) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);
        try
        {
            byte[] buffer = new byte[bufferSize];
            int len;
            int size = 0;
            boolean repeat;
            do
            {
                len = copy(is, buffer, baos);
                if (len >= 0)
                {
                    size += len;
                    repeat = isRepeat(len, size, is.available());
                }
                else
                {
                    // always exit on end of file
                    repeat = false;
                }
                if (logger.isDebugEnabled())
                {
                    logger.debug("len/repeat: " + len + "/" + repeat);
                }
            }
            while (repeat);
        }
        finally
        {
            baos.flush();
            baos.close();
        }
        return nullEmptyArray(baos.toByteArray());
    }

    /**
     * Decide whether to isRepeat transfer.  This implementation does so if
     * more data are available.  Note that previously, while documented as such,
     * there was also the additional requirement that the previous transfer
     * completely used the transfer buffer.
     *
     * @param len Amount transferred last call (-1 on EOF or socket error)
     * @param size Total amount transferred
     * @param available Amount available
     * @return true if the transfer should continue
     */
    protected boolean isRepeat(int len, int size, int available)
    {
        // previous logic - less reliable on slow networks
//        return len == bufferSize && available > 0;
        
        return available > 0;
    }

}

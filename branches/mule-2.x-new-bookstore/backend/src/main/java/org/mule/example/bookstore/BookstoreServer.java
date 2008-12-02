/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.example.bookstore;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.context.DefaultMuleContextFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BookstoreServer
{
    protected static transient Log logger = LogFactory.getLog(BookstoreServer.class);
    
    protected MuleContext muleContext;
    
    public BookstoreServer(String config) throws MuleException
    {
        // create mule
        muleContext = new DefaultMuleContextFactory().createMuleContext(config);
        muleContext.start();        
    }

    public void close()
    {
        muleContext.dispose();
    }
    
    public static void main(String[] args) throws Exception
    {
        new BookstoreServer("bookstore-config.xml");
    }    
}

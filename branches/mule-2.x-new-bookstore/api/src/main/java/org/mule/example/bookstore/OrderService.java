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

import javax.jws.WebParam;
import javax.jws.WebService;
// START SNIPPET: interface
@WebService
public interface OrderService
{
    Order orderBook(@WebParam(name="book") Book book, 
                    @WebParam(name="quantity") int quantity, 
                    @WebParam(name="address") String address,
                    @WebParam(name="email") String email);
}
// END SNIPPET: interface
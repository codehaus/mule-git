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

import java.util.Collection;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
// START SNIPPET: interface
@WebService
public interface CatalogService
{
	static final String URL = "http://localhost:8777/services/catalog";
	
    @WebResult(name="book") 
    Book getBook(@WebParam(name="bookId") long bookId);

    @WebResult(name="books") 
    Collection<Book> getBooks();
}
// END SNIPPET: interface
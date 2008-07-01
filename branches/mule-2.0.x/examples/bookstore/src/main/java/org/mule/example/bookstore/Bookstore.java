/*
 * $Id: Bookstore.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore;

import java.util.Collection;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface Bookstore
{
long addBook(@WebParam(name="book") Book book);
    
    @WebResult(name="bookIds")
    Collection<Long> addBooks(@WebParam(name="books") Collection<Book> books);
    
    @WebResult(name="books") 
    Collection<Book> getBooks();
    
    void orderBook(@WebParam(name="bookId") long bookId, 
                   @WebParam(name="address") String address,
                   @WebParam(name="email") String email);
}



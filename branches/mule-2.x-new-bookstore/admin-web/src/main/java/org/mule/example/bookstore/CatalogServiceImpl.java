/*
 * $$Id: BookstoreImpl.java 13564 2008-12-05 21:55:22Z tcarlson $$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.example.bookstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;

@WebService(serviceName="CatalogService",
    portName="CatalogPort",
    endpointInterface="org.mule.example.bookstore.CatalogService")
public class CatalogServiceImpl implements CatalogService, Initialisable
{
    private Map < Long, Book > books = new HashMap < Long, Book > ();
    
    public void initialise() throws InitialisationException
    {
        books = new HashMap <Long, Book> ();
        addBook(new Book("J.R.R. Tolkien", "The Fellowship of the Ring", 8));
        addBook(new Book("J.R.R. Tolkien", "The Two Towers", 10));
        addBook(new Book("J.R.R. Tolkien", "Return of the King", 10));
    }

    public long addBook(Book book)
    {
        System.out.println("Adding book " + book.getTitle());
        long id = books.size() + 1;
        book.setId(id);
        books.put(id, book);
        return id;
    }

    public Collection < Long > addBooks(Collection < Book > books)
    {
        List < Long > ids = new ArrayList < Long > ();
        if (books != null)
        {
            for (Book book : books)
            {
                ids.add(addBook(book));
            }
        }
        return ids;
    }

    public Collection < Book > getBooks()
    {
        return books.values();
    }

    public Book getBook(long bookId)
    {
        return books.get(bookId);
    }
}

/*
 * $Id: BookstoreImpl.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.mule.DefaultMuleMessage;
import org.mule.RequestContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;

@WebService(serviceName="BookstoreService",
    portName="BookstorePort",
    endpointInterface="org.mule.example.bookstore.Bookstore")
public class BookstoreImpl implements Bookstore
{
    private Map < Long, Book > books = new HashMap < Long, Book > ();
    
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

    public void orderBook(long bookId, String address, String email)
    {
        // In the real world we'd want this hidden behind an OrderService interface
        try
        {
            Book book = books.get(bookId);
            MuleMessage msg = new DefaultMuleMessage(new Object[] { book, address, email} );

            RequestContext.getEventContext().dispatchEvent(msg, "orderEmailService");
            System.out.println("Dispatched message to orderService.");
        }
        catch (MuleException e)
        {
            // If this was real, we'd want better error handling
            throw new RuntimeException(e);
        }
    }
}

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.mule.api.MuleException;

public class BookstoreClient
{
    protected static transient Log logger = LogFactory.getLog(BookstoreClient.class);
    protected static CatalogService catalog;
    protected static OrderService orderService;
    
    public BookstoreClient() throws MuleException
    {
        JaxWsProxyFactoryBean pf = new JaxWsProxyFactoryBean();

        // Catalog web service
        pf.setServiceClass(CatalogService.class);
        pf.setAddress(CatalogService.URL);
        catalog = (CatalogService) pf.create();

		// Order web service
        JaxWsProxyFactoryBean pf2 = new JaxWsProxyFactoryBean();
	    pf2.setServiceClass(OrderService.class);
	    pf2.setAddress(OrderService.URL);
	    orderService = (OrderService) pf2.create();		
    }

    public static void main(String[] args) throws Exception
    {
        // This is just a simple non mule way to invoke a web service.
        // It will place an order so you can see the emailOrderService
        // in action.
        // For learning how to use CXF with an outbound router, please
        // see the mule-publisher-demo portion of the project.
        new BookstoreClient();
        int response = 0;
        
        System.out.println("\n" + LocaleMessage.getWelcomeMessage());
        
        while (response != 'q')
        {
            System.out.println("\n" + LocaleMessage.getMenuOption1());
            System.out.println("\n" + LocaleMessage.getMenuOption2());
            System.out.println("\n" + LocaleMessage.getMenuOptionQuit());
            System.out.println("\n" + LocaleMessage.getMenuPromptMessage());
            response = getSelection();
            
            if (response == '1')
            {
                Collection <Book> books = catalog.getBooks();
                // Something in the way CXF marshalls the response converts an empty collection to null
                if (books == null)
                {
                    books = new ArrayList();
                }
                System.out.println("There are " + books.size() + " available book(s):\n");

                for (Iterator i = books.iterator(); i.hasNext();)
                {
                    Book book = (Book) i.next();
                    System.out.println("Id: " + book.getId());
                    System.out.println("Title: " + book.getTitle());
                    System.out.println("Author: " + book.getAuthor());
                    System.out.println("Price: $" + book.getPrice());
                    System.out.println();
                }
            }
            else if (response == '2')
            {   
                System.out.println("\n" + LocaleMessage.getOrderWelcomeMessage());
                System.out.println("\n" + LocaleMessage.getBookIdPrompt());
                long bookId = Long.parseLong(getInput());
                System.out.println("\n" + LocaleMessage.getQuantityPrompt());
                int quantity = Integer.parseInt(getInput());
                System.out.println("\n" + LocaleMessage.getHomeAddressPrompt());
                String homeAddress = getInput();
                System.out.println("\n" + LocaleMessage.getEmailAddressPrompt());
                String emailAddress = getInput();
                
            	// Look up book details
            	Book book = catalog.getBook(bookId); 
                // order book
        	    orderService.orderBook(book, quantity, homeAddress, emailAddress); 
                
                System.out.println("\nThank you for your order, a notification e-mail will be sent to " + emailAddress);
            }
            else if (response == 'q')
            {
                System.out.println(LocaleMessage.getGoodbyeMessage());
                System.exit(0);
            }
            else
            {
                System.out.println(LocaleMessage.getMenuErrorMessage());
            }
        }
    }
    
    private static int getSelection() throws IOException
    {
        byte[] buf = new byte[16];
        System.in.read(buf);
        return buf[0];
    }
    
    private static String getInput() throws IOException
    {
        BufferedReader request = new BufferedReader(new InputStreamReader(System.in));
        return request.readLine();
    }
}

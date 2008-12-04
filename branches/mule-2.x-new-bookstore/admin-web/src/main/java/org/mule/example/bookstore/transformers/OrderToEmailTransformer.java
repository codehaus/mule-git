/*
 * $$Id$$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.example.bookstore.transformers;

import org.mule.RequestContext;
import org.mule.api.transformer.TransformerException;
import org.mule.example.bookstore.Book;
import org.mule.example.bookstore.Order;
import org.mule.transformer.AbstractTransformer;
import org.mule.transport.email.MailProperties;

public class OrderToEmailTransformer extends AbstractTransformer
{
    public OrderToEmailTransformer()
    {
        super();
        registerSourceType(Order.class);
        setReturnClass(String.class);
    }
    
    @Override
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        Order order = (Order) src;
        Book book = order.getBook();
        String address = order.getAddress();
        String email = order.getEmail();
        
        String body =  "Thank you for placing your order for " +
                       book.getTitle() + " with Mule Bookstore. " +
                       "Your order will be shipped  to " + 
                       address + " by 3 PM today!";
        
        RequestContext.getEventContext().getMessage().setProperty(
                      MailProperties.TO_ADDRESSES_PROPERTY, email);
        System.out.println("Sent email to " + email);
        return body;
    }
}

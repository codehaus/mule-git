/*
 * $Id: OrderToEmailTransformer.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore;

import org.mule.RequestContext;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.transport.email.MailProperties;

public class OrderToEmailTransformer extends AbstractTransformer
{
    @Override
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        Object[] payload = (Object[]) src;
        Book book = (Book) payload[0];
        String address = (String) payload[1];
        String email = (String) payload[2];
        
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

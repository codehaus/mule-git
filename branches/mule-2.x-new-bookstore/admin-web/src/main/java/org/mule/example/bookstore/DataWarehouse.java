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

/**
 * Tracks statistics on book orders.
 */
public class DataWarehouse
{
    int booksOrdered = 0;
    double totalRevenue = 0;
    double averagePrice = 0;
    // TODO Create a HashMap to track quantities and determine the bestseller
    String bestSeller;
    
    public String updateStats(Order order)
    {
        Book book = order.getBook();
        booksOrdered += order.getQuantity();
        totalRevenue += (book.getPrice() * order.getQuantity());
        averagePrice = totalRevenue / booksOrdered;
        bestSeller = book.getTitle();
        
        System.out.println("Updating stats");
        return HtmlTemplate.wrapHtmlBody(printHtmlStats());
    }
    
    protected String printHtmlStats()
    {
        String output = "<h2>Data Warehouse Statistics</h2>";
        output += "<table>";
        output += "<tr><td>Total orders</td> <td>" + booksOrdered + "</td></tr>";
        output += "<tr><td>Total revenue</td> <td>$" + totalRevenue + "</td></tr>";
        output += "<tr><td>Average price</td> <td>$" + averagePrice + "</td></tr>";
        output += "<tr><td>Best seller</td> <td>" + bestSeller + "</td></tr>";
        output += "</table>";
        return output;
    }
}



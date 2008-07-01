/*
 * $Id: Book.java 13295 2008-05-02 13:35:30Z marie.rizzo $
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.example.bookstore;

public class Book 
{
    private long id;
    private String author;
    private String title;

    public Book()
    {
        // empty constructor
    }
    
    public Book(long id, String author, String title)
    {
        this.id = id;
        this.author = author;
        this.title = title;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
}

<%@ page import="org.mule.example.bookstore.Book,
 				 org.mule.example.bookstore.Bookstore,
 				 java.util.ArrayList,
 				 java.util.Collection,
				 java.util.Iterator,
				 org.apache.cxf.jaxws.JaxWsProxyFactoryBean"%>
<%@ page language="java" %>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>On-line Bookstore</title>
</head>

<body link="#FFFFFF" vlink="#FFFFFF" alink="#FFFFFF" bgcolor="#990000" text="#FFFFFF">

<form method="POST" name="submitRequest" action="">
    Search for a book:
    <table>
        <tr><td>Title: </td><td>
            <input type="text" name="title"/>
        </td></tr>
        <tr><td>Author: </td><td>
            <input type="text" name="author"/>
        </td></tr>

        <tr><td colspan="2">
            <input type="submit" name="submit" value="Submit" />
        </td></tr>
    </table>
</form>

<%
    String title = request.getParameter("title");
    String author = request.getParameter("author");

    if (title!=null || author!=null) 
    {
        JaxWsProxyFactoryBean pf = new JaxWsProxyFactoryBean();
        pf.setServiceClass(Bookstore.class);
        pf.setAddress("http://localhost:8777/services/bookstore");
        Bookstore bookstore = (Bookstore) pf.create();

        Collection < Book > books = bookstore.getBooks();
        // Something in the way CXF marshalls the response converts an empty collection to null
        if (books == null)
        {
            books = new ArrayList();
        }
        %>
        Request returned <%=books.size()%> book(s)<br/>
        <br/>

        <%
        for (Iterator i = books.iterator(); i.hasNext();)
        {
            Book book = (Book) i.next();
            %>
            Title: <%=book.getTitle()%><br/>
            Author: <%=book.getAuthor()%><br/>
            <a href="order.jsp?id=<%=book.getId()%>">Order this book</a>
            <br/><%
        }
     } 
     %>

<p/>
<table border="1" bordercolor="#990000"  align="left">
<tr><td>For more information about the Bookstore example go <a target="_blank" href="http://mule.mulesource.org/display/MULE2INTRO/Bookstore+Example">here</a>.</td></tr>
</table>
</body>
</html>

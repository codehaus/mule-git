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

<%
	// Get form parameters and provide defaults if blank
	
    long id = Long.parseLong(request.getParameter("id"));

    String field = request.getParameter("quantity");
    int quantity = field != null ? Integer.parseInt(field) : 1;

    field = request.getParameter("address");
    String address = field != null ? field : "Someplace, Somewhere";

    field = request.getParameter("email");
    String email = field != null ? field : "me@my-mail.com";

	// Invoke CXF web service
    JaxWsProxyFactoryBean pf = new JaxWsProxyFactoryBean();
    pf.setServiceClass(Bookstore.class);
    pf.setAddress("http://localhost:8777/services/bookstore");
    Bookstore bookstore = (Bookstore) pf.create();	

	// Look up book details
	Book book = bookstore.getBook(id); 
%>


<form method="POST" name="submitRequest" action="">
    Place an order:
    <table>
        <tr><td>Title: </td><td><%=book.getTitle()%></td></tr>
        <tr><td>Author: </td><td><%=book.getAuthor()%></td></tr>
        <tr><td>Quantity: </td><td>
            <input type="text" name="quantity" value="<%=quantity%>"/>
        </td></tr>
        <tr><td>Shipping Address: </td><td>
            <input type="text" name="address" value="<%=address%>"/>
        </td></tr>
        <tr><td>E-mail: </td><td>
            <input type="text" name="email" value="<%=email%>"/>
        </td></tr>        
		<input type="hidden" name="submitted" value="true"/>
        <tr><td colspan="2">
            <input type="submit" name="submit" value="Order" />
        </td></tr>
    </table>
</form>

<%
    String submitted = request.getParameter("submitted");

    // Validate form fields
	if (submitted!=null && quantity>0 && address!=null && email!=null) 
	{
	    bookstore.orderBook(id, address, email); 
	    %>
	    Thank you for your order, a notification e-mail will be sent to <%=email%><br/>
	    <br/>
	    <a href="/bookstore">Return to Home Page</a>
	    <%
	 } 
 %>

</body>
</html>

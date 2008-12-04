<%@ page import="org.mule.api.MuleMessage,
				 org.mule.module.client.MuleClient,
                 org.mule.example.bookstore.Book,
 				 org.mule.example.bookstore.Bookstore,
 				 java.util.Collection,
				 java.util.Iterator"%>
<%@ page language="java" %>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Bookstore Administration Console</title>
</head>

<body link="#FFFFFF" vlink="#FFFFFF" alink="#FFFFFF" bgcolor="#990000" text="#FFFFFF">

<%
//MuleClient client = new MuleClient(false);
//MuleMessage msg = client.send("vm://bookstore?method=getBooks", null);
//Collection <Book> books = (Collection) msg.getPayload();
%>

<h2>Add a new book</h2>
<form method="POST" name="addBook" action="/bookstore-admin/rest/bookstore">
    <table>
        <tr>
        	<td>Title: </td>
        	<td><input type="text" name="title"/></td>
        </tr>
        <tr>
        	<td>Author: </td>
        	<td><input type="text" name="author"/></td>
        </tr>
        <tr>
        	<td>Price: </td>
        	<td><input type="text" name="price"/></td>
        </tr>
    </table>
    <input type="submit" name="submit" value="Add book" />
</form>

<h2>Get latest statistics from Data Warehouse</h2>
<form method="GET" name="getStats" action="/bookstore-admin/rest">
    <input type="hidden" name="endpoint" value="stats"/>
    <input type="submit" name="submit" value="Get" />
</form>

<hr/>
<center><i>
For more information about the Bookstore example go <a target="_blank" href="http://mule.mulesource.org/display/MULE2INTRO/Bookstore+Example">here</a>.
</i></center>
</body>
</html>

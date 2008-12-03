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
<base target="contents">
</head>

<body link="#FFFFFF" vlink="#FFFFFF" alink="#FFFFFF" bgcolor="#990000" text="#FFFFFF">

<%
//MuleClient client = new MuleClient(false);
//MuleMessage msg = client.send("vm://bookstore?method=getBooks", null);
//Collection <Book> books = (Collection) msg.getPayload();
%>

<form method="POST" name="addBook" action="/bookstore-admin/rest/bookstore">
    Add a new book:
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

<form method="GET" name="getStats" action="/bookstore-admin/rest">
    Get latest statistics from Data Warehouse:
    <table>
        <tr><td colspan="2">
            <input type="hidden" name="endpoint" value="stats"/>
            <input type="submit" name="submit" value="Get" />
        </td></tr>
    </table>
</form>

<p/>
<table border="1" bordercolor="#990000"  align="left">
<tr><td>For more information about the Bookstore example go <a target="_blank" href="http://mule.mulesource.org/display/MULE2INTRO/Bookstore+Example">here</a>.</td></tr>
</table>
</body>
</html>

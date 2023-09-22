<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Admin Home</h1>
<%

 
String admin=(String)session.getAttribute("admin");
if(admin!=null){
%>
<%=admin %>
<button>Approve Items</button>
<button>View Merchant</button>
<button>View Customer</button>
<a href="/logout"><button>Logout</button></a>
<%}
else
{
%>
<h1>Login First</h1>
<%} %>
</body>
</html>
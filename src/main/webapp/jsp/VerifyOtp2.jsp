<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Enter Otp</h1>
<h1 style="color: red">${neg}</h1>
<form action="/admin/verifyotp">
<input type="hidden" value=${id} name="id">
<input type="hidden" value=${email} name="email">
<input type="text" name="otp">
<button>Verify</button>
</form>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	String messString = (String)request.getAttribute("message");
%>
</head>
<body>
	<h1><%=messString %></h1>
	<a href="">返回登陆</a>
</body>
</html>
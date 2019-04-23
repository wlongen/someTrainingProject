<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">  
<meta charset="UTF-8">
<title>修改</title>
</head>
<body>
	<form action="add.do">
		jumpIP：<input type="text" name="jumpIP"/><br /> 
		jumpPort：<input type="text" name="jumpPort" /><br /> 
		targetIP：<input type="text" name="targetIP" /><br /> 
		targetPort：<input type="text" name="targetPort" /><br /> 
		<input type="submit" value="添加" name="tianjia"> 
	</form>
</body>
</html>
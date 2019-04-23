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
<title>修改密码</title>
<%
 	String errorStr=request.getParameter("error");
	String spanString = errorStr==null?"":errorStr; 
%>
</head>
<script type="text/javascript">
</script>
<body>
	<form action="modifypwd.do" method="post">
		<div id="error"><%=spanString %></div>
		账号：<input name ="username" type="text" value="" /><br /> 
		旧密码：<input name ="oldpwd" type="password" value="" /><br /> 
		新密码：<input name ="newpwd" type="password" value="" /><br /> 
		确认密码：<input name ="newowdsec" type="password" value="" /><br /> 
		<input type="submit" value="修改" name="xiugai" /> 
	</form>
</body>
</html>
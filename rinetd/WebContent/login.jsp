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
<title>登陆</title>
<%
 	String errorStr=request.getParameter("error");
	String spanString = errorStr==null?"":errorStr; 
%>
<script type="text/javascript">
	function changeImage() {
		var timestamp = (new Date()).valueOf();
		var domInput = document.getElementById('image');
		domInput.src = "image.do?timestamp="+timestamp;
	}
	function modifypasswd() {
		location.href = "modifypwd.jsp";
	}
</script>
</head>
<body>
	<div align="center">
	<form class="form1" action="login.do" method="post">
		<div align="center" id="error" style="padding-top: 80px;"><%=spanString %></div>
		<table style="padding-top: 10px;border-spacing:   20px;">
			<tr align="center">
				<td align="left" >账号:</td>
				<td align="left" ><input type="text" name="username" value=""></td>
			</tr>
			<tr align="center">
				<td align="left" >密码:</td>
				<td align="left" ><input type="password" name="pwd" value=""></td>
			</tr>
			<tr align="center">
				<td align="left" >验证码:</td>
				<td align="left"><input type="text" name="image" value=""></td>
				<td><img id="image" alt="" src="image.do" onclick="changeImage()"></td>
			</tr>
		</table>
		<input type="submit" value="登录" name="denglu"> 
		<input type="reset" value="重置">
		<input type="button" onclick="modifypasswd()" value="修改密码">
	</form>
	</div>
</body>
</html>
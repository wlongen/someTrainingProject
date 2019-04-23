<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>用户注册页面</h1>
	<form action="regiest.do" method="post">
		<table>
			<tr>
				<td>用户名</td>
				<td>
					<input type="text" name="username" />
				</td>
			</tr>
			<tr>
				<td>密码</td>
				<td>
					<input type="password" name="password" />
				</td>
			</tr>
			<tr>
				<td>昵称</td>
				<td>
					<input type="text" name="nickname" />
				</td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td>
					<input type="text" name="email" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="注册" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
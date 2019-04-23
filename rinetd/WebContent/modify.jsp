<%@page import="com.wenlong.rinetd.util.ControlRinetdUtil"%>
<%@page import="com.wenlong.rinetd.entity.IPAndPort"%>
<%@page import="java.util.Map"%>
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
<%	
	String key = request.getParameter("key");
	Map<Integer,IPAndPort> iapmap=ControlRinetdUtil.seeConf();
	IPAndPort iap = iapmap.get(Integer.valueOf(key));
	String pageNum = (String)request.getParameter("page");
%>
</head>
<script type="text/javascript">
</script>
<body>
	<form action="modify.do">
		jumpIP：<input name ="jumpIP" type="text" value="<%= iap.getJumpIP()%>"/><br /> 
		jumpPort：<input name ="jumpPort" type="text" value="<%= iap.getJumpPort()%>" /><br /> 
		targetIP：<input name ="targetIP" type="text" value="<%= iap.getTargetIP()%>" /><br /> 
		targetPort：<input name ="targetPort" type="text" value="<%= iap.getTargetPort()%>" /><br /> 
		<input name ="key" type="hidden" value="<%= key%>" />
		<input name ="page" type="hidden" value="<%= pageNum%>" />
		<input type="submit" value="修改" name="xiugai" /> 
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>主页</title>
<%
	String ip = (String)session.getAttribute("ip");
	String username = (String)session.getAttribute("username");
	String pwd = (String)session.getAttribute("pwd");
	Object pageNum = request.getAttribute("page");
%>
<script type="text/javascript" src="./crypto-js/aes.js"></script>
<script type="text/javascript" src="./crypto-js/pad-zeropadding-min.js"></script>
<script type="text/javascript">
	
	function encrypt(){
		var hash = document.getElementById('pwd').value;
		var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
		var iv = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
	    var encrypted = CryptoJS.AES.encrypt(hash, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
		document.getElementById('pwd').value = encrypted;
	    return true;
	}
	function add() {
		location.href = "add.jsp";
	}
	function del(id) {
		var flag = window.confirm("确定删除?");
		var domInput = document.getElementById('delete');
		var page = domInput.value;
		if(flag){
			location.href = "del.do?key="+id+"&page="+page;
		}
	}
	function modify(id) {
		var domInput = document.getElementById('modify');
		var page = domInput.value;
		location.href = "modify.jsp?key="+id+"&page="+page;
	}
	function restart() {
		var flag = window.confirm("确定重启服务?");
		if(flag){
			location.href = "restart.do";
		}
	}
	function breakoff() {
		var flag = window.confirm("确定断开连接?");
		if(flag){
			location.href = "breakoff.do";
		}
	}
	function refresh() {
		location.href = "show.do";
	}
	function page(page) {
		location.href = "show.do?page="+page;
	}
	function jump() {
		var domInput = document.getElementById('page');
		var page = domInput.value;
		location.href = "show.do?page="+page;
	}
</script>
</head>
<body>
	<div align="center" style="padding-top: 20px;">
		<form action="breakon.do" method="post" onsubmit="encrypt()">
			ip：<input type="text" name="ip" value="<%= ip==null?"":ip%>">
			username： <input type="text" name="username" value="<%= username==null?"":username%>">
			password：<input id="pwd" type="password" name="pwd" value="<%= pwd==null?"":pwd%>">
			<input type="submit" value="连接" name="lianjie"> 
			<input type="button" value="断开连接"  name="duankai"onClick="breakoff()" /> 
		</form>
	</div>
	<div align="center" style="padding-top: 20px;">
		<span style="text-align: center">配置信息</span>
		<table style="padding-top: 20px;">
			<tbody>
				<tr>
					<td width="160">序号</td>
					<td width="160">jumpIP</td>
					<td width="160">jumpPort</td>
					<td width="160">targetIP</td>
					<td width="160">targetPort</td>
					<td width="160">操作</td>
				</tr>
				<c:forEach items="${iapMap}" var="item">
					<tr id="${item.key}">
						<td width="160">${item.key}</td>
						<td width="160">${item.value.jumpIP}</td>
						<td width="160">${item.value.jumpPort}</td>
						<td width="160">${item.value.targetIP}</td>
						<td width="160">${item.value.targetPort}</td>
						<td width="160">
							<button type="button" id="delete" value="${page}" onClick="del(${item.key})">删除</button>
							<button type="button" id="modify" value="${page}" onClick="modify(${item.key})">修改</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div style="padding-top: 20px;">
			<button id="beforepage" onClick="page(${page}-1)">上一页</button>
			第<input type="text" value="${page}" id="page" />页，共<a>${totalPage}</a>页
			<button id="jump" onClick="jump()">跳转</button>
			<button id="afterpage" onClick="page(${page}+1)">下一页</button>
		</div>
	</div>
	<div align="right" style="padding-right: 173px;padding-top: 20px;">
		<button id="add" onClick="add()">添加</button>
		<button id="refresh" onClick="refresh()">刷新</button>
		<button id="restart" onClick="restart()">重启服务</button>
	</div>
</body>
</html>
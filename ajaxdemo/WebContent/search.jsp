<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style type="text/css">
		#mydiv{
			position: absolute;
			left:50%;
			top:50%;
			margin-left: -200px;
			margin-top: -50px;
		}
		.mouseOver{
			background: #708090;
			color: #FFFAFA
		}
		.mouseOut{
			background: #FFFAFA;
			color: #000000;
		}
	</style>
	<script type="text/javascript">
		var xmlHttp;
		function getMore() {
			//获取用户的输入
			var input = document.getElementById("keyword");
			if(input.value == ""){
				clearData();
				return;
			}
			//获取xmlHttp对象
			xmlHttp = getXmlHttp();
			var url="search.do?keyword="+escape(input.value);
			//true表示js脚本会在send()方法后继续执行，不会等待服务器响应
			xmlHttp.open("GET",url,true);
			//xmlHttp绑定回调方法，会在xmlHttp状态改变的时候被调用
			//xmlHttp的状态0-4，我们只关心4这个状态
			
			//回调函数
			xmlHttp.onreadystatechange=function() {
				if(xmlHttp.readyState==4 && xmlHttp.status == 200){
					//交互成功，获得相应的数据，是文本格式
					var result = xmlHttp.responseText;
					//解析获得数据
					var json = eval("("+result+")");
					//获得数据之后，就可以动态的显示这些数据
					setContent(json);
				}
			};
			xmlHttp.send(null);
		}
		
		//获取xmlHttp对象
		function getXmlHttp() {
			var xmlHttp;
			if(window.XMLHttpRequest){
				xmlHttp = new XMLHttpRequest();
			}
			if(window.ActiveXObject){
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				if(!xmlHttp){
					xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
				}
			}
			return xmlHttp;
		}
		
		//设置关联数据的展示，参数代表的是服务器传递过来的数据
		function setContent(contents) {
			//清空之前的数据
			clearData();
			//设置关联信息展示的位置
			setLocation();
			//首先获取数据的长度，用来确定生成多少个tr
			var size = contents.length;	
			//设置内容
			for(var i=0;i<size;i++){
				//代表json数据的第i个元素
				var nextNode = contents[i];
				var tr = document.createElement("tr");
				var td = document.createElement("td");
				td.setAttribute("border","0");
				td.setAttribute("bgcolor","#FFFAFA");
				td.onmouseover=function(){
					this.className="mouseOver";
				};
				td.onmouseout=function(){
					this.className="mouseOut";
				};
				td.onclick = function () {
					//当鼠标点击该数据是，输入框填充
				};
				var text = document.createTextNode(nextNode);
				td.appendChild(text);
				tr.appendChild(td);
				document.getElementById("content_table_body").appendChild(tr);
			}
		}
		
		//清空之前的数据
		function clearData() {
			var contentTableBody = document.getElementById("content_table_body");
			var size = contentTableBody.childNodes.length;
			for(var i=size-1;i>=0;i--){
				contentTableBody.removeChild(contentTableBody.childNodes[i]);
			}
			document.getElementById("popDiv").style.border="none";
		}
		
		//设置显示关联信息的位置
		function setLocation() {
			//宽度一致
			var content = document.getElementById("keyword");
			var width = content.offsetWidth;//输入框的宽度
			var left = content["offsetLeft"];//到左边框的距离
			var top = content["offsetTop"];//到顶部的距离
			
			//获取显示数据的div
			var popDiv = document.getElementById("popDiv");
			popDiv.style.border="black 1px solid";
			popDiv.style.left = left+"px";
			popDiv.style.top = top+"px";
			popDiv.style.width = width+"px";
			document.getElementById("content_table").style.width = width+"px";
		}
	</script>
</head>
<body>
	<div id="mydiv">
		<!-- 输入框 -->
		<input type="text" size="50" id="keyword" onkeyup="getMore()" onblur="clearData()"
		onfocus="getMore()">
		<input type="button" value="搜索" width="50px">
		<!-- 内容展示的区域 -->
		<div id="popDiv">
			<table id="content_table" bgcolor="#FFFAFA" border="0" cellspacing="0"
				cellpadding="0">
				<tbody id="content_table_body">
					<!-- 动态查询出来的数据显示在这里 -->
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
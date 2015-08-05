<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>检测用户名是否唯一</title>
<script type="text/javascript">
	function createRequest(url) {
		http_request = false;
		if (window.XMLHttpRequest) { // 非IE浏览器
			http_request = new XMLHttpRequest(); //创建XMLHttpRequest对象
		} else if (window.ActiveXObject) { // IE浏览器
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP"); //创建XMLHttpRequest对象
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP"); //创建XMLHttpRequest对象
				} catch (e) {
				}
			}
		}
		if (!http_request) {
			alert("不能创建XMLHttpRequest对象实例！");
			return false;
		}
		http_request.onreadystatechange = getResult; //调用返回结果处理函数
		http_request.open('GET', url, true); //创建与服务器的连接
		http_request.send(null); //向服务器发送请求
	}
	function getResult() {
		if (http_request.readyState == 4) { // 判断请求状态
			if (http_request.status == 200) { // 请求成功，开始处理返回结果
				document.getElementById("toolTip").innerHTML = http_request.responseText; //设置提示内容
				document.getElementById("toolTip").style.display = "block"; //显示提示框
			} else { // 请求页面有错误
				alert("您所请求的页面有错误！");
			}
		}
	}
	function checkUser(userName) {
		if (userName.value == "") {
			alert("请输入用户名！");
			userName.focus();
			return;
		} else {
			createRequest('checkUser.jsp?user='
					+ encodeURIComponent(userName.value));
		}
	}
</script>
<style type="text/css">
<!--
#toolTip {
	position: absolute; /*设置为绝对定位*/
	left: 331px; /*设置左边距*/
	top: 39px; /*设置顶边距*/
	width: 98px; /*设置宽度*/
	height: 48px; /*设置高度*/
	padding-top: 45px; /*设置文字与顶边的距离*/
	padding-left: 25px; /*设置文字与左边的距离*/
	padding-right: 25px; /*设置文字与右边的距离*/
	z-index: 1; /*设置*/
	display: none; /*设置默认不显示*/
	color: red; /*设置文字的颜色*/
	background-image: url(images/tooltip.jpg); /*设置背景图片*/
}

#bg {
	width: 509px; /*设置宽度*/
	height: 298px; /*设置高度*/
	background-image: url(images/bg.gif); /*设置背景图片*/
	padding-top: 54px;
	margin: 0 auto auto auto; /*设置外边距*/
}

body {
	font-size: 12px; /*设置文字的大小*/
}

ul {
	list-style: none; /*设置不显示列表的项目符号*/
}

li {
	padding: 10px; /*设置内边距*/
	font-weight: bold; /*设置文字加粗*/
	color: #8e6723; /*设置文字颜色*/
}
-->
</style>
</head>
<body>
<body style="margin: 0px;">
	<form method="post" action="" name="form1">
		<div id="bg">
			<div style="position: absolute;">
				<ul>
					<li>用 &nbsp;户 &nbsp;名：<input name="username" type="text"
						id="username" size="32"><img src="images/checkBt.jpg"
						width="104" height="23" style="cursor: pointer;"
						onClick="checkUser(form1.username);"></li>
					<li>密　　码：<input name="pwd1" type="password" id="pwd1" size="35">
					<div id="toolTip"></div></li>
					<li>确认密码：<input name="pwd2" type="password" id="pwd2"
						size="35"></li>
					<li>E-mail　：<input name="email" type="text" id="email"
						size="45"></li>
					<li><input type="image" name="imageField"
						src="images/registerBt.jpg"></li>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
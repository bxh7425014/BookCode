<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>检测用户名是否唯一</title>
<script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#checkuser").click(function(){
			if ($("#username").val()== "") {	//判断是否输入用户名
				alert("请输入用户名！");
				$("#username").focus();	//让用户名文本框获得焦点
				return;
			} else {	//已经输入用户名时，检测用户名是否唯一
				$.get("checkUser.jsp",
						{user:$("#username").val()},
						function(data){
							$("#toolTip").text(data); //设置提示内容
							$("#toolTip").show(); //显示提示框
				});
			}			
		});
	});
</script>
<style type="text/css">
<!--
#toolTip {
	position: absolute; /*设置为绝对定位*/
	left: 331px; /*设置左边距*/
	top: 45px; /*设置顶边距*/
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
						id="username" size="32"><img id="checkuser" src="images/checkBt.jpg"
						width="104" height="23" style="cursor: pointer;"></li>
					<li>密　　码：<input name="pwd1" type="password" id="pwd1" size="35">
					<div id="toolTip"></div></li>
					<li>确认密码：<input name="pwd2" type="password" id="pwd2"
						size="35"></li>
					<li>　E-mail ：<input name="email" type="text" id="email"
						size="45"></li>
					<li><input type="image" name="imageField"
						src="images/registerBt.jpg"></li>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
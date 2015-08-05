<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>我的第一个jQuery脚本</title>
<script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>
<script>
$(document).ready(function(){
	//获取超链接对象，并为其添加单击事件
	$("a").click(function(){
		alert("我的第一个jQuery脚本！");
	});
});
</script>
</head>
<body>
<body>
<a href="#">弹出提示对话框</a>
</body>
</html>
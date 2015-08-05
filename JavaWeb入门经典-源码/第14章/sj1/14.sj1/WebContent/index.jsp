<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>实时显示公告信息</title>
<script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>
<script language="javascript">

function getInfo(){
	$.get("getInfo.jsp?nocache="+new Date().getTime(),function(data){
		$("#showInfo").html(data);
	});
}
$(document).ready(function(){
	getInfo();	//调用getInfo()方法获取公告信息
	window.setInterval("getInfo()", 600000);	//每隔10分钟调用一次getInfo()方法
});
</script>
<style type="text/css">
<!--

section{
	width: 175px; /*设置宽度*/
	height: 155px; /*设置高度*/
	background-image: url(images/bg.png); /*设置背景图片*/
	margin: 0 auto auto auto; /*设置外边距*/
	padding-left:0px;	/*设置左内边距*/
	padding-right:17px;/*设置右内边距*/
	padding-top:41px;/*设置顶内边距*/
	font-size: 12px; /*设置文字的大小*/
}
li {
	padding: 10px; /*设置内边距*/
	color: #8e6723; /*设置文字颜色*/
}
marquee{
	width:200px; /*设置宽度*/
	height:140px; /*设置高度*/
}
-->
</style>
</head>
<body>
<body>
<section>
	<marquee direction="up" scrollamount="3">
		<div id="showInfo"></div>
	</marquee>
</section>
</body>
</html>
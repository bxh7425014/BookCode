<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<style>
header {
	margin: 0 auto auto auto; /*设置居中显示*/
	width: 891px; /*设置宽度*/
	height: 30px; /*设置高度*/
	background-image: url("images/bg.jpg"); /*设置背景图片*/
	padding-top: 98px; /*设置顶内边距*/
	font-weight: bold; /*设置文字加粗显示*/
	color: white; /*设置文字颜色为白色*/
	padding-left: 10px; /*设置左内边距*/
}
</style>
<header>${param.typeList}</header>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>应用&lt;c:remove&gt;标签移除变量</title>
<style>
li {	padding: 5px;}
</style>
</head>
<body>
	<ul>
		<li>定义request范围内的变量username</li>
		<c:set var="username" value="明日科技" scope="request" />
		username的值为：
		<c:out value="${username}" />
		<li>移除request范围内的变量username</li>
		<c:remove var="username" scope="request" />
		username的值为：
		<c:out value="${username}" default="空" />
	</ul>
</body>
</html>

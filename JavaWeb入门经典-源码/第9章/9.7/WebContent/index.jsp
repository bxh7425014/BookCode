<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>重定向页面并传递参数</title>
</head>
<body>
<c:redirect url="main.jsp">
	<c:param name="user" value="wgh"/>
</c:redirect>
</body>
</html>

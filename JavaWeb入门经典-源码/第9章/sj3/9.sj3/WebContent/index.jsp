<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>JSTL在电子商城网站中的应用</title>
<link href="CSS/style.css" rel="stylesheet">
</head>
<body>
<section>
	<c:import url="top.jsp"/>
	<c:import url="login.jsp"/>

	<div style="width:100%; text-align:center">
		<img src="images/newGoods.jpg" width="794" height="208">
	</div>
	<c:import url="copyright.jsp"/>	
	<c:set var="user" value="mr"/>
</section>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="person" class="com.wgh.Person" scope="page">
	<jsp:setProperty name="person" property="*" />
</jsp:useBean>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>显示录入的员工信息页面</title>
<style type="text/css">
ul {
	list-style: none; /*设置不显示项目符号*/
	margin: 0px; /*设置外边距*/
	padding: 5px; /*设置内边距*/
}

li {
	padding: 5px; /*设置内边距*/
}
</style>
</head>
<body>
	<ul>
		<li>姓 名：<jsp:getProperty property="name" name="person" /></li>
		<li>年 龄：<jsp:getProperty property="age" name="person" /></li>
		<li>性 别：<jsp:getProperty property="sex" name="person" /></li>
		<li>住 址：<jsp:getProperty property="address" name="person" /></li>
	</ul>
</body>
</html>
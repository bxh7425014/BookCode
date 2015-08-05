<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.Bean.UserInfo" %>
<%
  String username=((UserInfo)request.getAttribute("us")).getName();
  String userjob=((UserInfo)request.getAttribute("us")).getJob();
%>
<html>
<head>
<title>注册成功</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div style="text-align: center;">
<ul>
	<li>注册成功！</li>
	<li><b>用户名：</b><%=username%>&nbsp;&nbsp;<b>职&nbsp;&nbsp;务：</b><%=userjob%></li>
	<li><a href="index.jsp">[ 返回 ]</a></li>
</ul>
</div>
</body>
</html>

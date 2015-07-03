<%--
网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
author  yeeku.H.lee kongyeeku@163.com
version  1.0
Copyright (C), 2001-2012, yeeku.H.Lee
This program is protected by copyright laws.
Program Name:
Date: 
--%>

<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
Object user = session.getAttribute("user");
if(user != null && user.toString().trim().equals("crazyit.org"))
{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title> 安全资源 </title>
	<meta name="website" content="http://www.crazyit.org"/>
</head>
<body>
	安全资源，只有登录用户<br/>
	且用户名是crazyit.org才可访问该资源
</body>
</html>
<%}
else
{
	out.println("您没有被授权访问该页面");	
}%>


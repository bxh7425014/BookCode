<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>处理页</title>
</head>
<body>
<%
	// 获取用户名
	String username = request.getParameter("username");
	// 获取密码
	String password = request.getParameter("password");
	// 判断用户名是否为mr，密码是否为mrsoft
	if("mr".equals(username) && "mrsoft".equals(password)){
		// 登录成功
		out.print("<h3>恭喜，登录成功！</h3>");
	}else{
		// 登录失败
		out.print("<h3>对不起，登录失败！</h3>");
	}
%>
</body>
</html>
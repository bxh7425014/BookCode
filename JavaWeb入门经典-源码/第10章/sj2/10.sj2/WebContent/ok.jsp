<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成功页</title>
</head>

<body>
	<b>欢迎<font color="blue">
		 <%
		 request.setCharacterEncoding("UTF-8");
		 String logName = request.getParameter("name") ;
			out.println(logName+"，【您的信息注册成功！】");
		 %>
	</font></b>
</body>
</html>
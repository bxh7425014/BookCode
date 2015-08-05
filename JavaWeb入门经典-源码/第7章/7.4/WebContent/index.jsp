<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通过Servlet处理表单提交的请求</title>
</head>
<body>
<form action="DealServlet" method="post">
	<p>请输入您的姓名：<input type="text" name="name"></p>
	<p><input type="submit" value="提　交"/></p>
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>结果页</title>
</head>
<body>
<%String message=request.getAttribute("result").toString(); %>
<%=message %>
</body>
</html>

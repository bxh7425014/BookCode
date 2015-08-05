<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/stringDeal" prefix="wghfn"%>
<%request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>显示结果</title>
</head>
<body>
内容为：<br>
${wghfn:shiftEnter(param.content)}
</body>
</html>
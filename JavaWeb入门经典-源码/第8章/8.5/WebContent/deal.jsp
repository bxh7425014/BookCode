<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>应用paramValues对象获取复选框的值</title>
</head>
<body>
<%request.setCharacterEncoding("UTF-8");%>
爱好为：
${paramValues.affect[0]}${paramValues.affect[1]}${paramValues.affect[2]}${paramValues.affect[3]}
</body>
</html>
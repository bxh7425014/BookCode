<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="page" class="com.wgh.UserInfo" type="com.wgh.UserInfo">
	<jsp:setProperty name="user" property="name" value="琦琦"/>
</jsp:useBean>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>通过pageScope隐含对象读取page范围内的JavaBean的属性值</title>
</head>
<body>
用户名为：${pageScope.user.name}

</body>
</html>
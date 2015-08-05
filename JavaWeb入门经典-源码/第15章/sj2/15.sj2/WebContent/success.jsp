<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册成功</title>
</head>
<body>
	<h3>恭喜您，注册成功！</h3>
	<ul>
		<li>用户名：<s:property value="username" /></li>
		<li>性　别：<s:property value="sex" /></li>
		<li>省　份：<s:property value="province" /></li>
		<li>爱　好：<s:property value="hobby" /></li>
		<li>描　述：<s:property value="description" /></li>
	</ul>
</body>
</html>
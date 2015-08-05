<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户注册</title>
</head>
<body>
	<h2>用户注册</h2>
	<s:form action="userAction" method="post">
		<s:textfield name="name" label="用户名" required="true" requiredposition="left"></s:textfield>
		<s:password name="password" label="密　码" required="true" requiredposition="left"></s:password>
		<s:radio name="sex" list="#{1 : '男', 0 : '女'}" label="性　别"  required="true" requiredposition="left"></s:radio>
		<s:select list="{'请选择省份','吉林','广东','山东','河南'}" name="province" label="省　份"></s:select>
		<s:checkboxlist list="{'足球','羽毛球','乒乓球','篮球'}" name="hobby" label="爱　好"></s:checkboxlist>
		<s:textarea name="description" cols="30" rows="5" label="描　述"></s:textarea>
		<s:submit value="注册"></s:submit>
		<s:reset value="重置"></s:reset>
	</s:form>
</body>
</html>
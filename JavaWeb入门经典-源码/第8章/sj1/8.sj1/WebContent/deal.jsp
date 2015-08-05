<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="userForm" class="com.wgh.UserForm" scope="page"/>
<jsp:setProperty name="userForm" property="*"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>显示用户填写的注册信息</title>
<style>
ul{
	list-style: none;
}
li{
	padding:5px;
}
</style>
</head>
<body>
<ul>
	<li>用户昵称：${userForm.username}</li>
	<li>密　　码：${userForm.pwd}</li>
    <li>性　　别：${userForm.sex}</li>
      <li>爱　　好：${userForm.affect[0]} ${userForm.affect[1]} ${userForm.affect[2]} ${userForm.affect[3]}</li>
      <li><input name="Button" type="button" class="btn_grey" value="返回" onClick="window.location.href='index.jsp'"></li>
</ul>
</body>
</html>
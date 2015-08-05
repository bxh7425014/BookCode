<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>应用&lt;c:set&gt;标签的应用</title>
<style>
li{padding:5px;}
</style>
</head>
<body>
<ul>
<li>定义request范围内的变量username</li>
<c:set var="username" value="明日科技" scope="request"/>
<c:out value="username的值为：${username}"/>
<li>设置UserInfo对象的name属性</li>
<jsp:useBean class="com.wgh.UserInfo" id="userInfo"/>
<c:set target="${userInfo}" property="name">wgh</c:set>
<c:out value="UserInfo的name属性值为：${userInfo.name}"></c:out>
</ul>
</body>
</html>

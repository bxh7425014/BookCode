<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="width:100%; text-align:center">
	<div id="login">
		<div id="loginForm">
		<c:choose>
			<c:when test="${empty sessionScope.user}">
			<form action="deal.jsp" method="post" name="form1">
				<ul>
				<li>用户昵称：<input name="user" type="text" id="user" /></li>
				<li>密　　码：<input name="pwd" type="password" id="pwd" /></li>
				<li><input name="Submit" type="submit" value="登录" />&nbsp;
				<input name="Submit2" type="reset" value="重置" /></li>
				</ul>
			 </form>
			</c:when>
			<c:otherwise>
				<ul>
				<li style="padding-top:30px;">
				欢迎您！${sessionScope.user} [<a href="logout.jsp">退出</a>]
				</li>
				</ul>
			</c:otherwise>
		</c:choose>
	  </div>
	</div>
</div>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%request.setCharacterEncoding("UTF-8");%>

<c:choose>
	<c:when test="${param.user == 'mr' && param.pwd == 'mrsoft'}">
		<c:set var="user" scope="session" value="${param.user}"/>	
		<c:redirect url="index.jsp"/>	
	</c:when>
	<c:when test="${param.user=='tsoft' && param.pwd=='111'}">
		<c:set var="user" scope="session" value="${param.user}"/>
		<c:redirect url="index.jsp"/>		
	</c:when>
	<c:otherwise>
		<script language="javascript">alert("您输入的用户名或密码不正确！");window.location.href="index.jsp";</script>			
	</c:otherwise>
</c:choose>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="width:100%; text-align:center">
	<div id="top">
		<div id="greeting">
			<jsp:useBean id="now" class="java.util.Date"/>
			<c:choose>
				<c:when test="${now.hours>=0 && now.hours<5}">
					凌晨好！
				</c:when>
				<c:when test="${now.hours>=5 && now.hours<8}">
					早上好！
				</c:when>	
				<c:when test="${now.hours>=8 && now.hours<11}">
					上午好！
				</c:when>
				<c:when test="${now.hours>=11 && now.hours<13}">
					中午好！
				</c:when>
				<c:when test="${now.hours>=13 && now.hours<17}">
					下午好！
				</c:when>	
				<c:otherwise>
				晚上好！
				</c:otherwise>
			</c:choose>
			现在时间是：${now.hours}时${now.minutes}分${now.seconds}秒
		</div>
	</div>
</div>
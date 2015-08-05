<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>JSTL根据参数不同显示不同的页面</title>
  </head>
  
  <body>
  <fieldset>
			<c:if test="${ param.action == 'mon' }">
					周一了：工作的第一天，要加油哦
			</c:if>
			<c:if test="${ param.action == 'tues' }">		
					周二了：工作的两天了，要适当补充体力哦
			</c:if>
			<c:if test="${ param.action == 'wed' }">
					周三了：忙碌的生活要学会调节
			</c:if>
			<c:if test="${ param.action == 'thu' }">
					周四了：偶尔偷下懒儿，不算过分哦
			</c:if>
			<c:if test="${ param.action == 'fri' }">
				周五了:加油明天就要休息了，HOHO
			</c:if>
			<c:if test="${ param.action == 'sat' }">
				周六了：和死党们出去HAPPY吧
			</c:if>
			<c:if test="${ param.action == 'sun' }">
				周日：要收敛一下活动，明个要上班呢
			</c:if>
	</fieldset>
  </body>
</html>

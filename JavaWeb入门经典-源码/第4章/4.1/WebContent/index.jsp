<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>一个简单的JSP页面——显示系统时间</title>
</head>
<body>
<%
	Date date = new Date(); 								//获取日期对象
	//设置日期时间格式
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String today = df.format(date); 						//获取当前系统日期
%>
当前时间：<%=today%>										<!-- 输出系统时间 -->
</body>
</html>

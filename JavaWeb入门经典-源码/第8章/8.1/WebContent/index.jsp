<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通过EL输出数组的全部元素</title>
</head>
<body>
<%
String[] arr={"Java Web开发典型模块大全","Java范例完全自学手册","JSP项目开发全程实录"};	//定义一维数组
request.setAttribute("book",arr);		//将数组保存到request对象中
%>
<%
String[] arr1=(String[])request.getAttribute("book");	//获取保存到request范围内的变量
//通过循环和EL输出一维数组的内容
for(int i=0;i<arr1.length;i++){
	request.setAttribute("requestI",i);
%>
	
	${requestI}：${book[requestI]}<br>	<!-- 输出数组中第i个元素 -->
<%} %>
</body>
</html>
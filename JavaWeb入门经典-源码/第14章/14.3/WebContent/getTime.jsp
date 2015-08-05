<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%
	out.println(new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
		.format(new Date()));	//输出系统时间
%>

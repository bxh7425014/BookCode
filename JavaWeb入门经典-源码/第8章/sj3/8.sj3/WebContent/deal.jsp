<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
request.setCharacterEncoding("GBK");
String user=request.getParameter("user");
session.setAttribute("user",user);
response.sendRedirect("index.jsp");
%>

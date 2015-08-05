<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.util.*" errorPage="" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%out.println(request.getAttribute("messages").toString());
%>
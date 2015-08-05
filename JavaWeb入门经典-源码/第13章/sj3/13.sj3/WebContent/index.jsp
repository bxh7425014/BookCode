<%--index.jsp  中转页--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
  <meta charset="utf-8">
    <title>中转页 </title>
  </head>
  <body>
<c:redirect url="/PlacardServlet">
    <c:param name="action" value="query"/>
</c:redirect>
  </body>
</html>

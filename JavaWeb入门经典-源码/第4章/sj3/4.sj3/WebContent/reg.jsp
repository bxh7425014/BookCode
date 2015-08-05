<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.javabean.Image" %>
<% Image reg=(Image)request.getAttribute("regimg"); %>
<html>
<head>
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
 <center>
   <table border="1"  style="margin-top:150" width="250" height="90">
     <tr height="20" bgcolor="lightgrey"><td align="center">欢迎注册！</td>
     <tr>
        <td align="center">
          您单击了“用户注册”图片！<br>
          鼠标单击时的位置为&nbsp;&nbsp;
          X：<%=reg.getX() %>
          Y：<%=reg.getY() %>
        </td>
     </tr>
   </table>
   <a href="index.jsp">返回</a>
 </center>
</body>
</html>
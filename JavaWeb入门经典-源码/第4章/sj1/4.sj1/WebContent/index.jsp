<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>应用Java程序片段动态生成表格</title>
	<meta charset="UTF-8">
	<style type="text/css">
		table{
			font-size: 16px;
			font-family: 隶书;
		}
	</style>
  </head>
  
  <body>
  
  <%
  	String[] bookName = {"Java Web开发典型模块大全","Java　Web开发实战宝典" ,"JSP项目开发全程实录"};
   %>
   
  <table border="1" align="center">
  	<tr>
  		<td align="center">编号</td>
  		<td align="center">书名</td>
  	</tr>
  	<%
  		for(int i=0;i<bookName.length;i++){
  	 %>
  	<tr>
  		<td align="center"><%=i %></td>
  		<td align="center"><%=bookName[i]%></td>
  	</tr>
  	<%} %>
  </table>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wgh.UserBean"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户信息列表</title>
<style type="text/css">
	td{
	font-size: 12px;
	padding:3px;
	}
</style>
</head>
<body>
<h2 style="width:350px;text-align: center;">用户信息列表</h2>
	<table align="center" border="1" width="400">
		<tr align="center" style="font-weight: bold;">
			<td>姓名</td>
			<td>性别</td>
			<td>家庭住址</td>
		</tr>
		<%
			List<UserBean> list = (List<UserBean>)application.getAttribute("users");
			if(list != null){
				for(UserBean user : list){
		%>
			<tr align="center">
				<td><%=user.getName()%></td>
				<td><%=user.getSex()%></td>
				<td><%=user.getAddress()%></td>
			</tr>
		<%
				}
			}
		%>
		<tr>
			<td align="center" colspan="3">
				<a href="index.jsp">继续添加</a>
			</td>
		</tr>
	</table>

</body>
</html>
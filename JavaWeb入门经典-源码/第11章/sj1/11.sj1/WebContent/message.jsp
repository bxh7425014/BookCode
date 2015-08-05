<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>返回的结果页</title>
<style type="text/css">
table {
	font-size: 13px;
}

input {
	font-size: 12px;
}
</style>
</head>

<body>
	<table border=0 background="bg.jpg" width="300" height="240">
		<tr>
			<td height="50" colspan="2"></td>
		</tr>
		<tr>
			<td>上传情况：</td>
			<td><%=request.getAttribute("result")%></td>
		</tr>
		<tr>
			<td>文件描述：</td>
			<td><%=request.getAttribute("upDe")%></td>
		</tr>
		<tr>
			<td>上传时间：</td>
			<td><%=request.getAttribute("dtme")%></td>
		</tr>
		<tr>
			<td height="50" colspan="2"></td>
		</tr>
	</table>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
</head>
<body>
	<jsp:useBean id="batch" class="com.wgh.Batch"></jsp:useBean>
	<%
		// 执行批量插入操作
		int row = batch.saveBatch();
		out.print("批量插入了【" + row + "】条数据！");
	%>
</body>
</html>
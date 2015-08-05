<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工信息查询</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/QueryEmployee" method="post" target="employee">
		<input type="submit" value="搜索员工信息"/>
		<input type="text" name="name" style="width:180px"/>
	</form>
	<iframe src="<%=request.getContextPath()%>/QueryEmployee?name=" 
			frameborder="0" name="employee" width="800px" height="500px">
	</iframe>
</body>
</html>
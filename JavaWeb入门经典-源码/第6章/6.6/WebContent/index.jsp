<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>录入员工信息页面</title>
<style type="text/css">
ul {
	list-style: none; /*设置不显示项目符号*/
	margin:0px;		/*设置外边距*/
	padding:5px;		/*设置内边距*/
}

li {
	padding:5px; /*设置内边距*/
}
</style>
</head>
<body>
	<form action="register.jsp" method="post">
		<ul>
			<li>姓　名：<input type="text" name="name"></li>
			<li>年　龄：<input type="text" name="age"></li>
			<li>性　别：<input type="text" name="sex"></li>
			<li>住　址：<input type="text" name="address" size="35"></li>
			<li><input type="submit" value="添　加"></li>
		</ul>
	</form>
</body>
</html>
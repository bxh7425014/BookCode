<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加图书信息</title>
<style type="text/css">
ul {
	list-style: none;
}
li{padding:5px;}

</style>
</head>
<body>
	<section>
		<h2>　　　添加图书信息</h2>
		<form action="AddServlet" method="post">
			<ul>
				<li>图书编号：<input type="text" name="id"></li>
				<li>图书名称：<input type="text" name="name"></li>
				<li>作　　者：<input type="text" name="author"></li>
				<li>价　　格：<input type="text" name="price"></li>
				<li>　　　　　<input type="submit" value="添　加"></li>
			</ul>
		</form>
	</section>
</body>
</html>
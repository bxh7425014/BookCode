<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登录</title>
<style type="text/css">
	li{list-style: none;}
</style>
</head>

<body>
	<div>
		<form action="login.jsp" method="post">
			<ul>
				<li><h3>用户登录</h3></li>
			</ul>
			<ul>
				<li>用户名：<input type="text" name="username"></li>
			</ul>
			<ul>
				<li>密　码：<input type="password" name="password"></li>
			</ul>
			<ul>
				<li>
					<input type="submit" value="登　录">
				</li>
			</ul>
		</form>
	</div>
</body>
</html>
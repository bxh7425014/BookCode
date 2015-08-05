<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加用户信息</title>
<style type="text/css">
ul{list-style: none;}
li{
	padding:5px;
	font-size: 12px;
}
</style>
<script type="text/javascript">
	function check(form){
		with(form){
			if(name.value == ""){
				alert("姓名不能为空！");
				return false;
			}
			if(address.value == ""){
				alert("家庭住址不能为空！");
				return false;
			}
		}
	}
</script>

</head>
<body>
	<form action="AddressServlet" method="post" onsubmit="return check(this);">
		<h2 style="width:350px;text-align: center;">添加用户信息</h2>
		<ul>
			<li>姓 名：<input type="text" name="name"></li>
			<li>性 别： <input type="radio" name="sex" value="男"
				checked="checked">男 <input type="radio" name="sex" value="女">女
			</li>
			<li>家庭住址：<textarea rows="5" cols="30" name="address"></textarea></li>
			<li><input type="submit" value="添　加"></li>
		</ul>
	</form>

</body>
</html>
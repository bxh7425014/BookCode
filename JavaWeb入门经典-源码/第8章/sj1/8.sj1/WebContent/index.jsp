<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>应用EL访问JavaBean属性</title>
<style>
ul{
	list-style: none;
}
li{
	padding:5px;
}
</style>
</head>
<body>
<form name="form1" method="post" action="deal.jsp">
<ul>
	<li>用户昵称：<input name="username" type="text" id="username"></li>
	<li>密　　码：<input name="pwd" type="password" id="pwd"> </li>
    <li>确认密码：<input name="repwd" type="password" id="repwd"></li>
    <li>性　　别：<input name="sex" type="radio" value="男" checked="checked">
        男 
        <input name="sex" type="radio" value="女">
        女</li>
      <li>爱　　好：<input name="affect" type="checkbox" id="affect" value="体育">
体育 
<input name="affect" type="checkbox" id="affect" value="美术">
美术 
<input name="affect" type="checkbox" id="affect" value="音乐">
音乐
<input name="affect" type="checkbox" id="affect" value="旅游">
旅游 </li>
      <li><input name="Submit" type="submit" value="提交">&nbsp;
			<input name="Submit2" type="reset" value="重置"></li>
</ul>
</form>
</body>
</html>
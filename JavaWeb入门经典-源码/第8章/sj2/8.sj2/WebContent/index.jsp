<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>应用EL表达式显示投票结果</title>
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
<h3>您最需要哪方面的编程类图书？</h3>
<form name="form1" method="post" action="PollServlet">
<ul>
	<li><input name="item" type="radio" class="noborder" value="基础教程类" checked>基础教程类</li>
	<li><input name="item" type="radio" class="noborder" value="实例集锦类">实例集锦类 </li>
      <li><input name="item" type="radio" class="noborder" value="经验技巧类">经验技巧类</li>
      <li> <input name="item" type="radio" class="noborder" value="速查手册类">速查手册类</li>
      <li><input name="item" type="radio" class="noborder" value="案例剖析类">案例剖析类</li>
      <li> <input name="Submit" type="submit" class="btn_grey" value="投票">
&nbsp;<input name="Submit2" type="button" class="btn_grey" value="查看投票结果" onClick="window.location.href='showResult.jsp'"></li>
</ul>
</form>
</body>
</html>
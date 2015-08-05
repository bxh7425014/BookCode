<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>显示投票结果页面</title>
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
	<li>基础教程类：&nbsp;<img src="bar.gif" width='${220*(applicationScope.pollResult["基础教程类"]/(applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]))}' height="13">
	  （${empty applicationScope.pollResult["基础教程类"]? 0 :applicationScope.pollResult["基础教程类"]}）</li>
	<li>实例集锦类：&nbsp;<img src="bar.gif" width='${220*(applicationScope.pollResult["实例集锦类"]/(applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]))}' height="13">
	  （${empty applicationScope.pollResult["实例集锦类"] ? 0 :applicationScope.pollResult["实例集锦类"]}）</li>
      <li>经验技巧类：&nbsp;<img src="bar.gif" width='${220*(applicationScope.pollResult["经验技巧类"]/(applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]))}' height="13">
	  （${empty applicationScope.pollResult["经验技巧类"] ? 0 :applicationScope.pollResult["经验技巧类"]}）</li>
      <li>速查手册类：&nbsp;<img src="bar.gif" width='${220*(applicationScope.pollResult["速查手册类"]/(applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]))}' height="13">
	  （${empty applicationScope.pollResult["速查手册类"] ? 0 : applicationScope.pollResult["速查手册类"]}）</li>
      <li>案例剖析类：&nbsp;<img src="bar.gif" width='${220*(applicationScope.pollResult["案例剖析类"]/(applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]))}' height="13">
	  （${empty applicationScope.pollResult["案例剖析类"] ? 0 :applicationScope.pollResult["案例剖析类"]}）</li>
      <li> 合计：${applicationScope.pollResult["基础教程类"]+applicationScope.pollResult["实例集锦类"]+applicationScope.pollResult["经验技巧类"]+applicationScope.pollResult["速查手册类"]+applicationScope.pollResult["案例剖析类"]}人投票！
        <input name="Button" type="button" class="btn_grey" value="返回" onClick="window.location.href='index.jsp'"></li>
</ul>
</form>
</body>
</html>
<%--modify.jsp  修改公告信息页面--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
  <meta charset="utf-8">
    <title>修改公告</title>
<style type="text/css">
body{
	font-size: 12px;
	text-align: center;
}
li{
	padding:5px;
}
ul{
	list-style: none;
	text-align: center;
}  
</style>
	<script type="text/javascript">
		function check(form){
			if(form.title.value==""){
				alert("请输入公告标题！");form.title.focus();return false;
			}
			if(form.content.value==""){
				alert("请输入公告内容！");form.content.focus();return false;
			}
		}
	</script>
  </head>
 
  <body><form name="form1" method="post" action="PlacardServlet?action=modify" onSubmit="return check(this)">
	<c:set var="form" value="${placardContent}"/>	
  <ul>
  	<li>标题：<input name="title" type="text" id="title" value="${form.title}" size="46">
      <input name="id" type="hidden" id="id" value="${form.id}"></li>
  	<li>内容：<textarea name="content" cols="36" rows="8" id="content">${form.content}</textarea></li>
  	<li><input name="Submit" type="submit" value="保存">
      &nbsp;
      <input name="Submit2" type="reset" value="重置">
&nbsp;
<input name="Submit3" type="button" value="返回" onClick="history.back(-1)"></li>
  </ul>
  </form>
  </body>
</html>

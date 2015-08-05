<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>保存公告信息到XML文件</title>
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
<body>
<form name="form1" method="post" action="PlacardServlet" target="_blank" onSubmit="return check(this)">
     <h3> 添加公告信息</h3>
   <ul>
   		<li>公告标题：<input name="title" type="text" id="title" size="46"></li>
   		<li>公告内容：<textarea name="content" cols="36" rows="9" id="content"></textarea></li>
   		<li><input name="Submit" type="submit" value="保存">
      &nbsp;
      <input name="Submit2" type="reset" value="重置"></li>
   </ul>
 </form>  
</body>
</html>

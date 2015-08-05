<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>验证用户名是否有效</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		table{
			border: 1px solid;
			border-color: green;
			font-size: 12px;
			color:gray;
			
		}
		input{
			font-size: 12px;
			color:gray;
		}
		font{
			color:orangered;
			font-size:12px;
		}
	</style>
  </head>
  
  <body>
  
  	<form action="check.jsp" method="post">
     <table>
     	<tr>
     		<td align="right">请输入用户名：</td>
     		<td><input type="text" name="name" />
     			<font>只能由字母、数字或下划线组成</font>
     		</td>
     	</tr> 
     	<tr>
     		<td colspan="2" align="center"><input type="submit" value="验 证" /></td>
     	</tr>
     </table>	
     </form>
  </body>
</html>

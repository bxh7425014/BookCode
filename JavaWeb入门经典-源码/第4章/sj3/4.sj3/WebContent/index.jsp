<%@ page language="java" contentType="text/html; charset=gb2312"%>
<html>
<head>
<title>提交图片</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
 <center>
   <form action="dosubmit.jsp" method="post">
   <table border="1"  style="margin-top:150" width="180">
     <tr height="20" bgcolor="lightgrey"><td colspan="2" align="center">选择操作</td>
     <tr>
       <td align="center"><input type="image" name="logon" value="ll" src="image/logon.gif" title="用户登录"></td>
       <td align="center"><input type="image" name="reg" src="image/reg.gif" title="用户注册"></td>
     </tr>
     <tr>
       <td align="center">登录</td>
       <td align="center">注册</td>
     </tr>
   </table>
   </form>
 </center>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>返回的结果页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		table{
			font-size: 13px;
			
		}
		input{
			font-size: 12px;
			
		}
	</style>
  </head>
  
  <body><table border=0 background="bg.jpg" width="300" height="240">
  <tr><td height="50" colspan="2"></td></tr>
  <tr><td>上传情况：</td><td>
     <%=request.getAttribute("result")%></td></tr>
     <tr><td>文件描述：</td><td>
      <%=request.getAttribute("upDe")%></td></tr>
     <tr><td>上传时间：</td><td>
       <%=request.getAttribute("dtme")%></td></tr>
       <tr><td height="50" colspan="2"></td></tr>
       </table>
  </body>
</html>

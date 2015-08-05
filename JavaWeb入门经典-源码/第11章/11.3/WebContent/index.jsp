<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>下载列表</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
  </head>
  
  <body>
	     <table style="margin-top:10px;" width="500"  border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolorlight="#FFFFFF" bordercolordark="#FFCCCC">
        <tr>
          <td width="29%" height="27" align="center">标题</td>
          <td width="14%" height="27" align="center">上传人</td>
          <td width="23%" height="27" align="center">上传时间</td>        
          <td width="6%" height="27" align="center">下载</td>
          </tr>
        <tr>
          <td height="29" align="center">新歌</td>
          <td align="center">小雨</td>
          <td align="center">2012年7月24日</td>        
          <td align="center">
		      <a href="DownServlet?path=<%=getServletContext().getRealPath("新歌.mp3") %>">
			  下载</a>
          </td>
          </tr>          
      </table>
  </body>
</html>

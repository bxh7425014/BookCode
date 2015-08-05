<%--placardList.jsp  公告列表页面--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
  <meta charset="utf-8">
    <title>公告列表</title>
    <style type="text/css">
body{
	font-size:12px;
}
h3{
	text-align:center;
}
footer{
	padding-top:5px;
	text-align:center;
}
    </style>
</head>
  
  <body>
  <h3>[${description}]${createTime}</h3>
  <table width="350" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
    <tr>
      <td height="27" align="center" bgcolor="#FFFFFF">标题</td>
      <td align="center" bgcolor="#FFFFFF">修改</td>
      <td align="center" bgcolor="#FFFFFF">删除</td>
    </tr>
<c:if test="${rssContent==null}">
	<tr>
		<td height="27" colspan="3" align="center" bgcolor="#FFFFFF">暂无公告！</td>
	</tr>
</c:if>
<c:forEach var="form" items="${rssContent}">	
    <tr>
      <td height="27" bgcolor="#FFFFFF">&nbsp;${form.title}</td>
      <td align="center" bgcolor="#FFFFFF"><a href="PlacardServlet?action=modify_query&id=${form.id}"><img src="images/modify.gif" width="20" height="18" border="0"></a></td>
      <td align="center" bgcolor="#FFFFFF"><a href="PlacardServlet?action=del&id=${form.id}"><img src="images/del.gif" width="23" height="22" border="0"></a></td>
    </tr>
</c:forEach>	
  </table>
<c:if test="${rssContent!=null}">
  <footer><a href="PlacardServlet?action=clearAll">[删除全部公告]</a></footer>
</c:if>

  </body>
</html>

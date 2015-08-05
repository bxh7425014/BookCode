<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<html>
  <head>
    <title>应用EL表达式判断用户是否登录</title>
  </head>
  
  <body>
    ${empty sessionScope.user ? "您还没有登录<a href='login.jsp'>登录</a>" : sessionScope.user}
	${!empty sessionScope.user ? "欢迎您来到本网站[<a href='logout.jsp'>退出</a>]":""}
  </body>
</html>

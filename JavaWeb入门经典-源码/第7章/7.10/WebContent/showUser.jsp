<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ page import="com.listener.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用监听查看在线用户</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<%
UserInfoList list = UserInfoList.getInstance();	//获得UserInfoList类的对象
UserInfoTrace ut = new UserInfoTrace();			//创建UserInfoTrace类的对象
request.setCharacterEncoding("UTF-8");			//设置编码为UTF-8，解决中文乱码
String name = request.getParameter("user");		//获取输入的用户名
ut.setUser(name);								//设置用户名
session.setAttribute("list", ut);	//将UserInfoTrace对象绑定到Session中
list.addUserInfo(ut.getUser());	//添加用户到UserInfo类的对象中
session.setMaxInactiveInterval(30);//设置Session的过期时间为30秒
%>
<body>
<div align="center">


<table width="304" height="210" border="0" cellpadding="0" cellspacing="0" background="image/background2.jpg">
  <tr>
    <td align="center"><br>
 
 <textarea rows="8" cols="20">
<%
Vector vector=list.getList();
if(vector!=null&&vector.size()>0){
	for(int i=0;i<vector.size();i++){
		out.println(vector.elementAt(i));
	}
}
%>
</textarea><br><br>
 <a href="loginOut.jsp">返回</a>
 
 </td>
  </tr>
</table>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="com.wgh.user.UserInfo"%>
<%@ page import="com.wgh.user.UserListener"%>
<%
request.setCharacterEncoding("UTF-8");
String username=request.getParameter("username");	//获得登录用户名
UserInfo user=UserInfo.getInstance();		//获得UserInfo类的对象
session.setMaxInactiveInterval(600);		//设置Session的过期时间为10分钟
Vector vector=user.getList();
boolean flag=true;		//标记是否登录的变量
//判断用户是否登录
if(vector!=null&&vector.size()>0){
	for(int i=0;i<vector.size();i++){
		if(user.equals(vector.elementAt(i))){
			out.println("<script language='javascript'>alert('该用户已经登录');window.location.href='index.jsp';</script>");
			flag=false;
			break;
		}
	}
}
//保存用户信息
if(flag){
	UserListener ul=new UserListener();
	ul.setUser(username);
	session.setAttribute("user",ul);
	session.setAttribute("username",username);
	user.addUser(ul.getUser());	
	//保存当前登录的用户名
	session.setAttribute("loginTime",new Date().toLocaleString());		//保存登录时间
	response.sendRedirect("MessagesAction?action=loginRoom");
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wgh.model.User,com.wgh.dao.UserDao" %>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");//获取注册用户名
	String pwd = request.getParameter("pwd");  //获取注册用户的密码
	String age = request.getParameter("age");  //获取注册用户的年龄
	String sex = request.getParameter("sex");  //获取注册用户的性别
	boolean nameRes = UserDao.getInstance().checkUserName(name);//判断用户名是否存在
	if(!nameRes){//如果用户名不存在
		User user = new User();//创建用户对象，保存用户注册信息
		user.setName(name);
		user.setPwd(pwd);
		if(age!=null&&!age.equals("")) user.setAge(Integer.parseInt(age));
		user.setSex(sex);
		boolean res = UserDao.getInstance().saveUser(user);//注册信息保存数据库
		if(res){
			request.getRequestDispatcher("ok.jsp").forward(request,response);
		}
		else{
			response.sendRedirect("error.jsp");
		}
	}
	else{
		response.sendRedirect("error.jsp");
	}
%>
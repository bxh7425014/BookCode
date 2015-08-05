<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连接MySQL数据库</title>
</head>
<body>
<%
	try {
		Class.forName("com.mysql.jdbc.Driver");			// 加载数据库驱动，注册到驱动管理器
		String url = "jdbc:mysql://localhost:3306/test";		// 数据库连接字符串
		String username = "root";						// 数据库用户名
		String password = "111";						// 数据库密码
		Connection conn = DriverManager.getConnection(url,username,password);		// 创建Connection连接
		// 判断数据库连接是否为空
		if(conn != null){
			out.println("数据库连接成功！");				// 输出连接信息
			conn.close();							// 关闭数据库连接
		}else{
			out.println("数据库连接失败！");				// 输出连接信息
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
%>

</body>
</html>
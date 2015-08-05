<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>批量添加</title>
</head>
<body>
<%
String[] names = { "A", "B", "C" };	//定义要插入的数据
Class.forName("com.mysql.jdbc.Driver");
Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_database10","root","111");
try {
    Statement stmt = conn.createStatement();
    stmt.clearBatch();      					// 清除位于Batch中的所有SQL语句
    for (int i = 0; i < names.length; i++) {      		// 通过循环向Batch中添加欲执行的SQL语句
        stmt.addBatch("insert into tb_batch(name) values('" + names[i] + "')");
    }
    stmt.executeBatch();      				// 批量执行Batch中的SQL语句
    stmt.close();
    conn.close();
    out.println("批量添加成功！");
} catch (SQLException e) {
	out.println("批量添加失败！");
    e.printStackTrace();
}

%>
</body>
</html>
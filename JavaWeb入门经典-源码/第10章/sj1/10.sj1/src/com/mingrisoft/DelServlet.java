package com.mingrisoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet实现类UpdateServlet
 */
@WebServlet("/DelServlet")	//配置Servlet
public class DelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DelServlet() {
		super();
	}

	/**
	 * 处理POST请求
	 */
protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	try {
		Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
		String url = "jdbc:mysql://localhost:3306/db_database10";// 数据库连接字符串
		String username = "root"; // 数据库用户名
		String password = "111"; // 数据库密码
		// 创建Connection连接
		Connection conn = DriverManager.getConnection(url, username,
				password);
		String sql = "DELETE FROM tb_book WHERE id=?";// 更新SQL语句
		PreparedStatement ps = conn.prepareStatement(sql);// 获取PreparedStatement
		String ID[]=request.getParameterValues("delid");		//获取要删除的图书编号
		if (ID.length>0){
			for(int i=0;i<ID.length;i++){
				ps.setInt(1,Integer.parseInt(ID[i])); 	// 对SQL语句中的第1个参数赋值
				ps.addBatch();				// 添加批处理命令
			}
		}
		ps.executeBatch();	// 执行批处理操作
		ps.close(); // 关闭PreparedStatement
		conn.close(); // 关闭Connection
	} catch (Exception e) {
		e.printStackTrace();
	}
	response.sendRedirect("FindServlet"); // 重定向到FindServlet
}

}

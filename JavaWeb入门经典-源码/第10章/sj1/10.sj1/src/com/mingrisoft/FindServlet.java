package com.mingrisoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet实现类FindServlet
 */
@WebServlet("/")		//配置Servlet为默认执行页
public class FindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public FindServlet() {
        super();
    }

	/**
	 * 执行POST请求的方法
	 */
	protected void doPostt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * 执行GET请求的方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			Class.forName("com.mysql.jdbc.Driver");		// 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://localhost:3306/db_database10";// 数据库连接字符串
			String username = "root";						// 数据库用户名	
			String password = "111";						// 数据库密码	
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url,username,password);
			Statement stmt = conn.createStatement();		// 获取Statement
			String sql = "select * from tb_book";			// 添加图书信息的SQL语句	
			ResultSet rs = stmt.executeQuery(sql);			// 执行查询	
			List<BookBean> list = new ArrayList<>();		// 实例化List对象	
			while(rs.next()){								// 光标向后移动，并判断是否有效
				BookBean book = new BookBean();					// 实例化Book对象
				book.setId(rs.getInt("id"));				// 对id属性赋值
				book.setName(rs.getString("name"));		// 对name属性赋值
				book.setPrice(rs.getDouble("price"));		// 对price属性赋值
				book.setBookCount(rs.getInt("bookCount"));	// 对bookCount属性赋值
				book.setAuthor(rs.getString("author"));		// 对author属性赋值
				list.add(book); 							// 将图书对象添加到集合中
			}
			request.setAttribute("list", list); 			// 将图书集合放置到request中
			rs.close();									// 关闭ResultSet
			stmt.close();									// 关闭Statement
			conn.close();									// 关闭Connection
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 请求转发到bookList.jsp
		request.getRequestDispatcher("bookList.jsp").forward(request, response);

	}

}

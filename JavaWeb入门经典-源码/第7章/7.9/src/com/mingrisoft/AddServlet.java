package com.mingrisoft;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public AddServlet() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);// 处理GET请求
	}

	/**
	 * 处理POST请求的方法
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 处理POST请求
		PrintWriter out = response.getWriter(); // 获取 PrintWriter
		String id = request.getParameter("id"); // 获取图书编号
		String name = request.getParameter("name"); // 获取名称
		String author = request.getParameter("author"); // 获取作者
		String price = request.getParameter("price"); // 获取价格
		out.print("<h2>图书信息添加成功</h2><hr>"); // 输出图书信息
		out.print("图书编号：" + id + "<br>");
		out.print("图书名称：" + name + "<br>");
		out.print("作者：" + author + "<br>");
		out.print("价格：" + price + "<br>");
		out.flush(); // 刷新流
		out.close(); // 关闭流

	}

}

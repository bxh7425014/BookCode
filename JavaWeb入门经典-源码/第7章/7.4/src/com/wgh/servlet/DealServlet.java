package com.wgh.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DealServlet
 */
@WebServlet("/DealServlet")
public class DealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DealServlet() {
		super();
	}

	// 处理POST请求
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 获取name参数值
		String name = request.getParameter("name");
		// 从response中获取PrintWriter对象
		PrintWriter writer = response.getWriter();
		// 判断name是否为空
		if (name != null) {
			// 如果name不为空，则通过writer输出name
			writer.write("Hello " + name + " !");
		}
		writer.flush(); // 刷新writer
		writer.close(); // 关闭writer
	}
}

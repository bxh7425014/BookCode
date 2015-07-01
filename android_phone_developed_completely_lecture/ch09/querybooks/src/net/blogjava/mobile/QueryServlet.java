package net.blogjava.mobile;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueryServlet
 */
public class QueryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8"); 
		String queryStr = "";
		if ("post".equals(request.getMethod().toLowerCase()))
			queryStr = "POST请求；查询字符串：" + new String(request.getParameter("bookname").getBytes(
					"iso-8859-1"), "utf-8");
		else if ("get".equals(request.getMethod().toLowerCase()))
			queryStr = "GET请求；查询字符串：" + request.getParameter("bookname");

		String s =queryStr
				+ "[Java Web开发速学宝典;Java开发指南思想（第4版）;Java EE开发宝典；C#开发宝典]";
		PrintWriter out = response.getWriter();
		out.println(s);
	}
}

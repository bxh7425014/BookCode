package com.lh.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/counter")
public class CounterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6611089831326888620L;

	/**
	 * Constructor of the object.
	 */
	public CounterServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获得ServletContext对象
		ServletContext context = getServletContext();
		//从ServletContext中获得计数器对象
		Integer count = (Integer)context.getAttribute("counter");
		if(count==null){//如果为空，则在ServletContext中设置一个计数器的属性
			count=1;
			context.setAttribute("counter", count);
		}else{			//如果不为空，则设置该计数器的属性值加1
			context.setAttribute("counter", count+1);
		}
		response.setContentType("text/html");	//响应正文的MIME类型
		response.setCharacterEncoding("UTF-8");	//响应的编码格式
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>统计网站访问次数</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    <h2><font color='gray'> ");
		out.print("您是第  "+context.getAttribute("counter")+" 位访客！");
		out.println("</font></h2>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

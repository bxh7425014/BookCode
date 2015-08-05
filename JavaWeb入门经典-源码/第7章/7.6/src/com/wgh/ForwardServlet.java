package com.wgh;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet实现类ForwardServlet
 */
@WebServlet("/ForwardServlet")	//通过注解配置ForwardServlet
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForwardServlet() {
        super();
    }

	/**
	 * 执行HTTP GET请求
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("main.jsp");	//创建
		requestDispatcher.forward(request, response);		//转发页面

	}
}

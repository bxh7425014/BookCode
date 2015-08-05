package com.wgh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wgh.dao.*;
@WebServlet("/Insert_Data")
public class Insert_Data extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	      doPost(request, response);

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
		PrintWriter out = response.getWriter();
		UserDao dao=new UserDao();
		if ((String)request.getParameter("tname")!="" && (String)request.getParameter("tpassword")!=""){
			String userName=request.getParameter("tname");
			String passWord=request.getParameter("tpassword");
			SimpleDateFormat date_time = new SimpleDateFormat("yyyy-MM-dd HH;mm;ss");
		    String datetime = date_time.format(new Date());
		    String info="用户登录";
		  	String sql="insert into tb_login (name,pwd,loginTime,info) values('"+userName+"','"+passWord+"','"+datetime+"','"+info+"')";
			dao.executeUpdate(sql);
			out.println("<script >alert('用户登录成功!'); window.location.href='index.jsp';</script>");
		}

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

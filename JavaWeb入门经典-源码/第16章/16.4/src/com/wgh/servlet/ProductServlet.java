package com.wgh.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.wgh.hibernate.HibernateUtil;
import com.wgh.product.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");		//设置内容类型，防止中文乱码
		Session session = null;			//声明Session对象
		try {
			//Hibernate的持久化操作
			session = HibernateUtil.getSession();//获取Session
			Product product = (Product) session.load(Product.class, new Integer("1"));	//加载对象
			PrintWriter out= response.getWriter();
			out.println("<ul>");
			out.println("<li>产品ID："+product.getId()+"</li>");
			out.println("<li>产品名称："+product.getName()+"</li>");
			out.println("<li>产品价格："+product.getPrice()+"元</li>");			
			out.println("<li>生产厂商："+product.getFactory()+"</li>");	
			out.println("<li>产品备注："+product.getRemark()+"</li>");	
			out.println("</ul>");
		} catch (Exception e) {
			System.out.println("对象装载失败");
			e.printStackTrace();
		} finally{
			HibernateUtil.closeSession();		//关闭Session
		}
	}

}

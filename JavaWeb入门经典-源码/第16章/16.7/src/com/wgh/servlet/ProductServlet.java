package com.wgh.servlet;

import java.io.IOException;
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
		Session session = null;			//声明Session对象
		try {
			//Hibernate的持久化操作
			session = HibernateUtil.getSession();//获取Session
			Product product = (Product) session.get(Product.class, new Integer("1"));	//加载对象
			System.out.println("第一次加载对象");
			Product product2 = (Product) session.get(Product.class, new Integer("1"));	//加载对象
			System.out.println("第二次加载对象");
		} catch (Exception e) {
			System.out.println("对象装载失败");
			e.printStackTrace();
		} finally{
			HibernateUtil.closeSession();		//关闭Session
		}
	}

}

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
		Session session = null;			//声明第一个Session对象
		try {
			//Hibernate的持久化操作
			session = HibernateUtil.getSession();//获取Session
			session.beginTransaction();//事务开启
			Product product = (Product) session.get(Product.class, new Integer("1"));//装载对象
			System.out.println("产品名称："+product.getName());
			System.out.println("产品价格："+product.getPrice()+"元");
			System.out.println("生产商："+product.getFactory().getFactoryName());
			session.getTransaction().commit();//事务提交
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();//事务回滚
		} finally{
			HibernateUtil.closeSession();//关闭Session
		}
	}

}

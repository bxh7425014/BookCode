package com.wgh.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.wgh.factory.Factory;
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
		Session session = null;								//声明一个Session对象
		try {
			//Hibernate的持久化操作
			session = HibernateUtil.getSession();		//获取Session
			session.beginTransaction();						//事务开启
			Factory factoty = (Factory) session.get(Factory.class, new Integer("1"));	//加载对象
			System.out.println("生产商："+factoty.getFactoryName());	//打印生产商名称
			Set<Product> products = factoty.getProducts();			//获取集合对象
			//通过迭代输出产品信息
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = (Product) it.next();
				System.out.println("产品名称：" + product.getName()+"||产品价格："+product.getPrice());
			}
			session.getTransaction().commit();					//事务提交
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();					//事务回滚
		} finally{
			HibernateUtil.closeSession();					//关闭Session
		}

	}

}

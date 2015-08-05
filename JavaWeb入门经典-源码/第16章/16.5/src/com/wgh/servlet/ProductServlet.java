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
			session.beginTransaction();					// 开启事务
			Product product = (Product) session.get(Product.class, new Integer("1"));	//加载对象
			session.delete(product);	//删除持久化对象
			session.flush();	//强制刷新提交
			session.getTransaction().commit();				// 事务提交
			System.out.println("对象删除成功！");
		} catch (Exception e) {
			session.getTransaction().rollback();			// 事务回滚
			System.out.println("对象装载失败");
			e.printStackTrace();
		} finally{
			HibernateUtil.closeSession();		//关闭Session
		}
	}

}

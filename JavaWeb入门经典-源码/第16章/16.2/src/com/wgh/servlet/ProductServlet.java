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
		Session session = null; // 声明Session对象
		response.setContentType("text/html;charset=utf-8");		//设置内容类型，防止中文乱码
		Product product = new Product();// 实例化持久化类
		// 为持久化类属性赋值
		product.setName("Java Web编程宝典");// 设置产品名称
		product.setPrice(79.00);// 设置产品价格
		product.setFactory("明日科技");// 设置生产商
		product.setRemark("无");// 设置备注
		// Hibernate的持久化操作
		try {
			session = HibernateUtil.getSession();// 获取Session
			session.beginTransaction();// 开启事务
			session.save(product);// 执行数据库添加操作
			session.getTransaction().commit();// 事务提交
			PrintWriter out;
			out = response.getWriter();
			out.println("<script>alert('数据添加成功！');</script>");
		} catch (Exception e) {
			session.getTransaction().rollback();// 事务回滚
			System.out.println("数据添加失败");
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();// 关闭Session对象
		}
	}

}

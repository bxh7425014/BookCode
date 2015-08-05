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

import com.wgh.hibernate.HibernateUtil;
import com.wgh.idcard.IDcard;
import com.wgh.people.People;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/")
public class PeopleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PeopleServlet() {
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
			People people = (People) session.get(People.class, new Integer("4"));	//加载对象
			session.delete(people);							// 删除加载的公民对象
			session.getTransaction().commit();					//事务提交
			System.out.println("数据删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();					//事务回滚
		} finally{
			HibernateUtil.closeSession();					//关闭Session
		}

	}

}

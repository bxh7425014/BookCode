package com.wgh.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.wgh.hibernate.HibernateUtil;
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
		List<People> list=null;
		try {
			//Hibernate的持久化操作
			session = HibernateUtil.getSession();		//获取Session
			session.beginTransaction();						//事务开启
			String hql = "from People p order by p.age asc,p.sex desc";
			list=session.createQuery(hql).list();
			session.getTransaction().commit();					//事务提交
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();					//事务回滚
		} finally{
			HibernateUtil.closeSession();					//关闭Session
		}
        request.setAttribute("peoplelist", list);
        //跳转到员工信息的列表页面
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/list.jsp");
        rd.forward(request, response);		
	}

}

package com.mr.dao;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;



import com.mr.user.User;
/**
 * 执行信息批量添加
 */
public class DAOSupport {
	private SessionFactory sessionFactory; // 定义SessionFactory属性

	/**
	 * 获取Session对象
	 */
	protected Session getSession() {
		return sessionFactory.openSession();
	}
	public void InsertPatchInfo(int count,HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");//用户名称
		String business = request.getParameter("business");//职务
		for(int i = 0; i < count ; i++){//批量执行添加方法 
			User userVO = new User();//实例化对象 
			userVO.setName(name+i);//设置用户名
			userVO.setBusiness(business);//设置职务
			userVO.setAddTime(new Date());//设置添加时间
			this.getSession().save(userVO);//执行添加方法
		}	
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
}

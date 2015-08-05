package com.mr.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.mr.user.User;

public class UserDAO {
	private SessionFactory sessionFactory; // 定义SessionFactory属性

	// 保存用户的方法
	public void insert(User user) {
		this.getSession().save(user);
	}

	/**
	 * 获取Session对象
	 */
	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

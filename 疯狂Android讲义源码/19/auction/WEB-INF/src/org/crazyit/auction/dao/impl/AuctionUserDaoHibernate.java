package org.crazyit.auction.dao.impl;

import java.util.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.crazyit.auction.model.*;
import org.crazyit.auction.business.*;
import org.crazyit.auction.dao.*;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AuctionUserDaoHibernate
	extends HibernateDaoSupport implements AuctionUserDao  
{
	/**
	 * 根据id查找用户
	 * @param id 需要查找的用户id
	 */
	public AuctionUser get(Integer id)
	{
		return (AuctionUser)getHibernateTemplate()
			.get(AuctionUser.class , id);
	}

	/**
	 * 增加用户
	 * @param user 需要增加的用户
	 */
	public void save(AuctionUser user)
	{
		getHibernateTemplate().save(user);
	}

	/**
	 * 修改用户
	 * @param user 需要修改的用户
	 */
	public void update(AuctionUser user)
	{
		getHibernateTemplate().saveOrUpdate(user);
	}

	/**
	 * 删除用户
	 * @param id 需要删除的用户id
	 */ 
	public void delete(Integer id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除用户
	 * @param user 需要删除的用户
	 */
	public void delete(AuctionUser user)
	{
		getHibernateTemplate().delete(user);
	}

	/**
	 * 查询全部用户
 	 * @return 获得全部用户
	 */ 
	public List<AuctionUser> findAll()
	{
		return (List<AuctionUser>)getHibernateTemplate()
			.find("from AuctionUser");
	}

	/**
	 * 根据用户名，密码查找用户
	 * @param username 查询所需的用户名
	 * @param pass 查询所需的密码
	 * @return 指定用户名、密码对应的用户
	 */
	public AuctionUser findUserByNameAndPass(String username , String pass)
	{
		//执行HQL查询
		List<AuctionUser> ul = (List<AuctionUser>)getHibernateTemplate()
			.find("from AuctionUser au where au.username = ? and au.userpass = ?" ,
			username , pass);
		//返回查询得到的第一个AuctionUser对象
		if (ul.size() == 1)
		{
			return (AuctionUser)ul.get(0);
		}
		return null;
	}
}
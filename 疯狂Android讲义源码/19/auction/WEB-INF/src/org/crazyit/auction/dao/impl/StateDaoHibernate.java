package org.crazyit.auction.dao.impl;

import java.util.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.Hibernate;

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
public class StateDaoHibernate
	extends HibernateDaoSupport implements StateDao  
{
	/**
	 * 根据id查找状态
	 * @param id 需要查找的状态id
	 */
	public State get(Integer id)
	{
		return (State)getHibernateTemplate().get(State.class , id);
	}
	/**
	 * 增加状态
	 * @param state 需要增加的状态
	 */  
	public void save(State state)
	{
		getHibernateTemplate().save(state);
	}

	/**
	 * 修改状态
	 * @param state 需要修改的状态
	 */
	public void update(State state)
	{
		getHibernateTemplate().saveOrUpdate(state);
	}

	/**
	 * 删除状态
	 * @param id 需要删除的状态id
	 */
	public void delete(Integer id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除状态
	 * @param state 需要删除的状态
	 */
	public void delete(State state)
	{
		getHibernateTemplate().delete(state);
	}

	/**
	 * 查询全部状态
	 * @return 获得全部状态
	 */
	public List<State> findAll()
	{
		return (List<State>)getHibernateTemplate().find("from State");
	}
}
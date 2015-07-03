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
public class KindDaoHibernate 
	extends HibernateDaoSupport implements KindDao  
{
	/**
	 * 根据id查找种类
	 * @param id 需要查找的种类的id
	 */
	public Kind get(Integer id)
	{
		return (Kind)getHibernateTemplate().get(Kind.class , id);
	}

	/**
	 * 增加种类
	 * @param kind 需要增加的种类
	 */
	public void save(Kind kind)
	{
		getHibernateTemplate().save(kind);  
	}

	/**
	 * 修改种类
	 * @param kind 需要修改的种类
	 */ 
	public void update(Kind kind)
	{
		getHibernateTemplate().saveOrUpdate(kind);  
	}

	/**
	 * 删除种类
	 * @param id 需要删除的种类id
	 */ 
	public void delete(Integer id)
	{
		getHibernateTemplate().delete(get(id));  
	}

	/**
	 * 删除种类
	 * @param kind 需要删除的种类
	 */
	public void delete(Kind kind)
	{
		getHibernateTemplate().delete(kind);  
	}

	/**
	 * 查询全部种类
	 * @return 获得全部种类
	 */
	public List<Kind> findAll()
	{
		return (List<Kind>)getHibernateTemplate().find("from Kind");
	}
}

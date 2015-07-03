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
public class BidDaoHibernate 
	extends HibernateDaoSupport implements BidDao  
{
	/**
	 * 根据主键查找竞价记录
	 * @param bidId 竞价id;
	 * @return id对应的竞价记录
	 */
	public Bid get(Integer bidId)
	{
		return(Bid)getHibernateTemplate().get(Bid.class , bidId);
	}

	/**
	 * 保存竞价记录
	 * @param bid 需要保存的竞价记录
	 */
	public void save(Bid bid)
	{
		getHibernateTemplate().save(bid);
	}

	/**
	 * 修改竞价记录
	 * @param bid 需要修改的竞价记录
	 */
	public void update(Bid bid)
	{
		getHibernateTemplate().saveOrUpdate(bid);
	}

	/**
	 * 删除竞价记录
	 * @param id 需要删除的竞价id
	 */
	public void delete(Integer id)
	{
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * 删除竞价
	 * @param bid 需要删除的竞价
	 */
	public void delete(Bid bid)
	{
		getHibernateTemplate().delete(bid);
	}

	/**
	 * 根据用户查找竞价
	 * @param id 用户id
	 * @return 用户对应的全部
	 * @return 用户对应的全部竞价
	 */
	public List<Bid> findByUser(Integer userId)
	{
		return (List<Bid>)getHibernateTemplate()
			.find("from Bid as bid where bid.bidUser.id = ?" , userId);
	}
	/**
	 * 根据物品id，以及出价查询用户
	 * @param itemId 物品id;
	 * @param price 竞价的价格
	 * @return 对指定物品、指定竞价对应的用户
	 */
	public AuctionUser findUserByItemAndPrice(Integer itemId , Double price)
	{
		//执行HQL查询
		List<Bid> l = (List<Bid>)getHibernateTemplate()
			.find("from Bid as bid where bid.bidItem.id = ? and bid.bidPrice = ?"
			, new Object[]{itemId , price});
		//返回查询得到的第一个Bid对象关联的AuctionUser对象
		if (l.size() >= 1)
		{
			Bid b = (Bid)l.get(0);
			return b.getBidUser();
		}
		return null;
	}
}

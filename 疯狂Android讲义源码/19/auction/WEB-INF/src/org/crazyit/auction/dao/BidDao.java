package org.crazyit.auction.dao;

import java.util.List;

import org.crazyit.auction.model.*;
import org.crazyit.auction.business.*;
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
public interface BidDao  
{
	/**
	 * 根据主键查找竞价记录
	 * @param bidId 竞价id;
	 * @return id对应的竞价记录
	 */
	Bid get(Integer bidId);

	/**
	 * 保存竞价记录
	 * @param bid 需要保存的竞价记录
	 */    
	void save(Bid bid);

	/**
	 * 修改竞价记录
	 * @param bid 需要修改的竞价记录
	 */
	void update(Bid bid);

	/**
	 * 删除竞价记录
	 * @param id 需要删除的竞价id
	 */
	void delete(Integer id);

	/**
	 * 删除竞价
	 * @param bid 需要删除的竞价
	 */
	void delete(Bid bid);

	/**
	 * 根据用户查找竞价
	 * @param id 用户id
	 * @return 用户对应的全部
	 * @return 用户对应的全部竞价
	 */
	List<Bid> findByUser(Integer userId);

	/**
	 * 根据物品id，以及出价查询用户
	 * @param itemId 物品id;
	 * @param price 竞价的价格
	 * @return 对指定物品、指定竞价对应的用户
	 */
	AuctionUser findUserByItemAndPrice(Integer itemId , Double price);
}

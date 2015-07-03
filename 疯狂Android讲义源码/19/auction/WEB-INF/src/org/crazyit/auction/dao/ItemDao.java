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
public interface ItemDao  
{
	/**
	 * 根据主键查找物品
	 * @param itemId 想查找到物品的id;
	 * @return id对应的物品
	 */
	Item get(Integer itemId);

	/**
	 * 保存物品
	 * @param item 需要保存的物品
	 */
	void save(Item item);

	/**
	 * 修改物品
	 * @param item 需要修改的物品
	 */
	void update(Item item);

	/**
	 * 删除物品
	 * @param id 需要删除的物品id
	 */
	void delete(Integer id);

	/**
	 * 删除物品
	 * @param item 需要删除的物品
	 */
	void delete(Item item);

	/**
	 * 根据产品分类，获取当前拍卖的全部商品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
	 */
	List<Item> findItemByKind(Integer kindId);

	/**
	 * 根据所有者查找处于拍卖中的物品
	 * @param useId 所有者Id;
	 * @return 指定用户处于拍卖中的全部物品
	 */
	List<Item> findItemByOwner(Integer userId);

	/**
	 * 根据赢取者查找物品
	 * @param userId 赢取者Id;
	 * @return 指定用户赢取的全部物品
	 */
	List<Item> findItemByWiner(Integer userId);

	/**
	 * 根据物品状态查找物品
	 * @param stateId 状态Id;
	 * @return 该状态下的全部物品
	 */
	List<Item> findItemByState(Integer stateId);
}
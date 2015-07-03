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
public interface StateDao  
{
	/**
	 * 根据id查找状态
	 * @param id 需要查找的状态id
	 */ 
	State get(Integer id);

	/**
	 * 增加状态
	 * @param state 需要增加的状态
	 */      
	void save(State state);

	/**
	 * 修改状态
	 * @param state 需要修改的状态
	 */
	void update(State state);

	/**
	 * 删除状态
	 * @param id 需要删除的状态id
	 */ 
	void delete(Integer id);

	/**
	 * 删除状态
	 * @param state 需要删除的状态
	 */
	void delete(State state);

	/**
	 * 查询全部状态
	 * @return 获得全部状态
	 */ 
	List<State> findAll();
}

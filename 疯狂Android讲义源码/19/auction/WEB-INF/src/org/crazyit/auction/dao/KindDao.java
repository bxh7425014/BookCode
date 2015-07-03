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
public interface KindDao  
{
	/**
	 * 根据id查找种类
	 * @param id 需要查找的种类的id
	 */
	Kind get(Integer id);

	/**
	 * 增加种类
	 * @param kind 需要增加的种类
	 */
	void save(Kind kind);

	/**
	 * 修改种类
	 * @param kind 需要修改的种类
	 */
	void update(Kind kind);

	/**
	 * 删除种类
	 * @param id 需要删除的种类id
	 */
	void delete(Integer id);

	/**
	 * 删除种类
	 * @param kind 需要删除的种类
	 */
	void delete(Kind kind);

	/**
	 * 查询全部种类
	 * @return 获得全部种类
	 */
	List<Kind> findAll();
}

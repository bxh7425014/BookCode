package org.crazyit.service;

import org.crazyit.service.Pet;
import org.crazyit.service.Person;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
interface IPet
{
	// 定义一个Person对象作为传入参数
	List<Pet> getPets(in Person owner);
}
package org.crazyit.auction.schedule;

import java.util.TimerTask;

import org.crazyit.auction.service.AuctionManager;
import org.crazyit.auction.exception.AuctionException;
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
public class CheckWiner extends TimerTask
{
	//该任务所依赖业务逻辑组件
	private AuctionManager mgr;
	//依赖注入业务逻辑组件必须的setter方法
	public void setMgr(AuctionManager mgr)
	{
		this.mgr = mgr;
	}
	//该任务的执行体
	public void run()
	{
		try
		{
			mgr.updateWiner();
		}
		catch (AuctionException ae)
		{
			ae.printStackTrace();
		}
	}
}

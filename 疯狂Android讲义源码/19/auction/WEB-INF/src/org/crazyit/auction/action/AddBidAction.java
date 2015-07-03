package org.crazyit.auction.action;

import java.util.*;
import com.opensymphony.xwork2.ActionContext;

import org.crazyit.auction.service.AuctionManager;
import org.crazyit.auction.exception.AuctionException;
import org.crazyit.auction.action.base.BaseAction;

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
public class AddBidAction extends BaseAction
{
	//封装请求参数的属性
	private int itemId;
	private double bidPrice;
	private double maxPrice;
	private String vercode;
	//重写validate方法完成自定义输入校验
	@Override
	public void validate()
	{
		//用户竞价必须大于物品的当前最高价
		if(bidPrice <= maxPrice)
		{
			addFieldError("bidPrice", "您输入的竞价必须高于当前最高价！");
		}
	}
	//处理用户竞价
	public String execute() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
			//取出Session中的userId和刚刚生成的随机验证码
		Integer userId = (Integer)session.get("userId");
		String ver2 = (String)session.get("rand");
		session.put("rand" , null);
		//如果用户输入的验证码和Session中的随机验证码相同
		if (vercode.equalsIgnoreCase(ver2))
		{
			mgr.addBid(itemId , bidPrice ,userId);  
			return SUCCESS;
		}
		else
		{
			addActionError("验证码不匹配,请重新输入");
			return INPUT;
		}
	}

	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}
	public int getItemId()
	{
		 return this.itemId;
	}

	public void setBidPrice(double bidPrice)
	{
		this.bidPrice = bidPrice;
	}
	public double getBidPrice()
	{
		 return this.bidPrice;
	}

	public void setMaxPrice(double maxPrice)
	{
		this.maxPrice = maxPrice;
	}
	public double getMaxPrice()
	{
		 return this.maxPrice;
	}

	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}
	public String getVercode()
	{
		 return this.vercode;
	}

}
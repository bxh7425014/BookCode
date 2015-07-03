package org.crazyit.auction.model;

import java.util.*;

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
public class Item
{
	//标识属性
	private Integer id;
	//物品Remark
	private String itemRemark;
	//物品名称
	private String itemName;
	//物品描述
	private String itemDesc;
	//物品添加时间
	private Date addtime;
	//物品结束拍卖时间
	private Date endtime;
	//物品的起拍价
	private double initPrice;
	//物品的最高价
	private double maxPrice;
	//该物品的所有者
	private AuctionUser owner;
	//该物品所属的种类
	private Kind kind;
	//该物品的赢取者
	private AuctionUser winer;
	//该物品所处的状态
	private State itemState;
	//该物品对应的全部竞价记录
	private Set<Bid> bids = new HashSet<Bid>();

	//无参数的构造器
	public Item()
	{
	}
	//初始化全部基本属性的构造器
	public Item(Integer id , String itemRemark , String itemName , 
		String itemDesc , Date addtime , Date endtime , 
		double initPrice , double maxPrice , AuctionUser owner)
	{
		this.id = id;
		this.itemRemark = itemRemark;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.addtime = addtime;
		this.endtime = endtime;
		this.initPrice = initPrice;
		this.maxPrice = maxPrice;
		this.owner = owner;
	}

	//id属性的setter和getter方法
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return this.id;
	}

	//itemRemark属性的setter和getter方法
	public void setItemRemark(String itemRemark)
	{
		this.itemRemark = itemRemark;
	}
	public String getItemRemark()
	{
		return this.itemRemark;
	}

	//itemName属性的setter和getter方法
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getItemName()
	{
		return this.itemName;
	}

	//itemDesc属性的setter和getter方法
	public void setItemDesc(String itemDesc)
	{
		this.itemDesc = itemDesc;
	}
	public String getItemDesc()
	{
		return this.itemDesc;
	}

	//addtime属性的setter和getter方法
	public void setAddtime(Date addtime)
	{
		this.addtime = addtime;
	}
	public Date getAddtime()
	{
		return this.addtime;
	}

	//endtime属性的setter和getter方法
	public void setEndtime(Date endtime)
	{
		this.endtime = endtime;
	}
	public Date getEndtime()
	{
		return this.endtime;
	}

	//initPrice属性的setter和getter方法
	public void setInitPrice(double initPrice)
	{
		this.initPrice = initPrice;
	}
	public double getInitPrice()
	{
		return this.initPrice;
	}

	//maxPrice属性的setter和getter方法
	public void setMaxPrice(double maxPrice)
	{
		this.maxPrice = maxPrice;
	}
	public double getMaxPrice()
	{
		return this.maxPrice;
	}

	//owner属性的setter和getter方法
	public void setOwner(AuctionUser owner)
	{
		this.owner = owner;
	}
	public AuctionUser getOwner()
	{
		return this.owner;
	}

	//kind属性的setter和getter方法
	public void setKind(Kind kind)
	{
		this.kind = kind;
	}
	public Kind getKind()
	{
		return this.kind;
	}

	//winer属性的setter和getter方法
	public void setWiner(AuctionUser winer)
	{
		this.winer = winer;
	}
	public AuctionUser getWiner()
	{
		return this.winer;
	}

	//itemState属性的setter和getter方法
	public void setItemState(State itemState)
	{
		this.itemState = itemState;
	}
	public State getItemState()
	{
		return this.itemState;
	}

	//bids属性的setter和getter方法
	public void setBids(Set<Bid> bids)
	{
		this.bids = bids;
	}
	public Set<Bid> getBids()
	{
		return this.bids;
	}
}
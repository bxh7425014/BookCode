package org.crazyit.auction.action;

import java.util.*;
import com.opensymphony.xwork2.ActionContext;

import org.crazyit.auction.service.AuctionManager;
import org.crazyit.auction.exception.AuctionException;
import org.crazyit.auction.action.base.BaseAction;
/**
 * @author  crazyit.auction.H.lee kongcrazyit.auction@163.com
 * @version  1.0
 * <br>Copyright (C), 2005-2008, crazyit.auction.H.Lee
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date: 
 */
public class ViewKindAction extends BaseAction
{
	private List kinds;
	private String errMsg;

	public String execute()throws Exception
	{
		setKinds(mgr.getAllKind());
		return SUCCESS;
	}

	public void setKinds(List kinds)
	{
		this.kinds = kinds;
	}

	public List getKinds()
	{
		 return this.kinds;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
	public String getErrMsg()
	{
		 return this.errMsg;
	}
}
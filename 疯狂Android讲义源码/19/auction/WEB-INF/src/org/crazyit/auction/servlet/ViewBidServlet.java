package org.crazyit.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.crazyit.auction.servlet.base.BaseServlet;
import org.crazyit.auction.service.AuctionManager;
import org.crazyit.auction.business.*;
import java.io.*;
import java.util.*;
import org.json.*;

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
@WebServlet(urlPatterns="/android/viewBid.jsp")
public class ViewBidServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		// 获取userId
		Integer userId = (Integer)request.getSession(true)
			.getAttribute("userId");
		// 获取业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 获取该用户所参与的全部竞价
		List<BidBean> bids = auctionManager.getBidByUser(userId);
		JSONArray jsonArr= new JSONArray(bids);
		response.setContentType("text/html; charset=GBK");
		response.getWriter().println(jsonArr.toString()); 			
	}
}
package org.crazyit.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.crazyit.auction.servlet.base.BaseServlet;
import org.crazyit.auction.service.AuctionManager;
import java.io.*;
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
@WebServlet(urlPatterns="/android/addKind.jsp")
public class AddKindServlet extends BaseServlet
{
    public void service(HttpServletRequest request , 
		HttpServletResponse response)
		throws IOException , ServletException
	{
		request.setCharacterEncoding("gbk");
		// 获取请求参数
		String name = request.getParameter("kindName");
		String desc = request.getParameter("kindDesc");
		// 获取系统业务逻辑组件
		AuctionManager auctionManager = (AuctionManager)getCtx().getBean("mgr");
		// 调用业务逻辑组件的业务方法添加种类
		int kindId = auctionManager.addKind(name , desc);
		response.setContentType("text/html; charset=GBK");
		// 添加成功
		if (kindId > 0)
		{
			response.getWriter().println("恭喜您，种类添加成功!");
		}
		else
		{
			response.getWriter().println("对不起，种类添加失败!");
		}
	}
}
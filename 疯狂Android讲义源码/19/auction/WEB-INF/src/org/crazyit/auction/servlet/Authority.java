package org.crazyit.auction.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;

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
@WebFilter(urlPatterns="/android/*")
public class Authority implements Filter
{
	public void init(FilterConfig config)
		throws ServletException
	{
	}

    public void doFilter(ServletRequest request, 
		ServletResponse response, FilterChain chain) 
		throws IOException , ServletException
	{
		HttpServletRequest hrequest = (HttpServletRequest)request;
		// 获取HttpSession对象
		HttpSession session = hrequest.getSession(true);
		Integer userId = (Integer)session.getAttribute("userId");
		// 如果用户已经登录，或用户正在登录
		if ((userId != null && userId > 0)
			|| hrequest.getRequestURI().endsWith("/login.jsp"))
		{
			// “放行”请求
			chain.doFilter(request , response);
		}
		else
		{
			response.setContentType("text/html; charset=GBK");
			// 生成错误提示。
			response.getWriter().println("您还没有登录系统，请先系统！"); 
		}
	}

    public void destroy()
    {
    }
}
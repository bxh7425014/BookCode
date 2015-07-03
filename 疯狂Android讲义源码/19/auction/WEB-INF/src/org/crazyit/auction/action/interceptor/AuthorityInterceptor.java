package org.crazyit.auction.action.interceptor;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.*;
import java.util.Map;

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
public class AuthorityInterceptor extends AbstractInterceptor
{
	//拦截用户请求
	public String intercept(ActionInvocation invocation) throws Exception
	{
		//取得跟踪用户的HTTP Session
		Map session = ActionContext.getContext().getSession();
		Object userId = session.get("userId");
		//如果用户Session中userId属性为null，即用户还未登录
		if (userId == null)
		{
			return "login";
		}
		//否则，继续执行目标Action的execute方法
		else
		{
			return invocation.invoke();
		}
	}
}
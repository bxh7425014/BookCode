package com.mingrisoft;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet过滤器实现类CountFilter
 */
@WebFilter(
		urlPatterns = { "/index.jsp" }, 
		initParams = { 
				@WebInitParam(name = "count", value = "5000")
		})
public class CountFilter implements Filter {
		private int count;	// 来访数量

    /**
     * 默认构造方法 
     */
    public CountFilter() { }

	/**
	 * 销毁方法
	 */
	public void destroy() {	}

	/**
	 * 过滤处理方法
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		count ++;											// 访问数量自增
		// 将ServletRequest转换成HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取ServletContext
		ServletContext context = req.getServletContext();	
		context.setAttribute("count", count); 			// 将来访数量值放入到ServletContext中
		chain.doFilter(request, response); 			// 向下传递过滤器
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String param = fConfig.getInitParameter("count");	// 获取初始化参数
		count = Integer.valueOf(param); 					// 将字符串转换为int
	}

}

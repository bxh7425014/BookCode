package com.mingrisoft;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Servlet过滤器实现类CharactorFilter
 */
@WebFilter(
		urlPatterns = { "/*" }, 
		initParams = { 
				@WebInitParam(name = "encoding", value = "UTF-8")
		})	//配置过滤器
public class CharactorFilter implements Filter {
	String encoding = null;									 // 字符编码
    public CharactorFilter() {
    }

	/**
	 * 销毁方法
	 */
	public void destroy() {
		encoding = null;
	}

	/**
	 * 过滤处理方法
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(encoding != null){ 							// 判断字符编码是否为空
			request.setCharacterEncoding(encoding);		// 设置request的编码格式					// 设置response字符编码
     		response.setContentType("text/html; charset="+encoding);
		}
		chain.doFilter(request, response); 				// 传递给下一过滤器
	}

	/**
	 * 初始化方法
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");	// 获取初始化参数
	}

}

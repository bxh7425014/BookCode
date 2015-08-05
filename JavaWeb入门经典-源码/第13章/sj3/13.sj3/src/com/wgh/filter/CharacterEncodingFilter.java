package com.wgh.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
		urlPatterns = { "/*" }, 
		initParams = { 
				@WebInitParam(name = "encoding", value = "UTF-8")
		})														//配置过滤器
public class CharacterEncodingFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
	@Override
	public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
		
	}
	/**
	 * 过滤处理方法
	 */

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        if (encoding != null) {// 判断字符编码是否为空
            request.setCharacterEncoding(encoding);	// 设置请求的编码格式
            response.setContentType("text/html; charset="+encoding);// 设置响应字符编码
        }
        chain.doFilter(request, response);		// 传递给下一过滤器
	}
	/**
	 * 初始化方法
	 */

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");		// 获取初始化参数
	}



}

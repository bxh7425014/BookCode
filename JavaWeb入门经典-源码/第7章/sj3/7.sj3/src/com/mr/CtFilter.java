package com.mr;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.*;
@WebFilter(urlPatterns={"/*"})
public class CtFilter extends HttpServlet implements Filter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws ServletException,
            IOException {

  response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Cr wrapper = new Cr((HttpServletResponse)response);
        filterChain.doFilter(request, wrapper);

       String resStr = wrapper.toString().trim();
     String newStr = "";
       if (resStr.indexOf("笨蛋") > 0) {
            newStr = resStr.replace("笨蛋","***");
       }
      out.println(newStr);
    }
}

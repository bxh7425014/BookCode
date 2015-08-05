package com.wgh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PollServlet")
public class PollServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 处理POST请求的方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	//设置请求的编码方式
		String item=request.getParameter("item");	//获取投票项
		ServletContext servletContext=request.getSession().getServletContext();	//获取ServletContext对象该对象在application范围内有效
		Map map=null;
		if(servletContext.getAttribute("pollResult")!=null){
			map=(Map)servletContext.getAttribute("pollResult");	//获取投票结果
			map.put(item,Integer.parseInt(map.get(item).toString())+1);	//将当前的投票项加1
		}else{		//初始化一个保存投票信息的Map集合，并将选定投票项的投票数设置为1，其他为0
			String[] arr={"基础教程类","实例集锦类","经验技巧类","速查手册类","案例剖析类"};
			map=new HashMap();
			for(int i=0;i<arr.length;i++){
				if(item.equals(arr[i])){	//判断是否为选定的投票项
					map.put(arr[i], 1);
				}else{
					map.put(arr[i], 0);
				}
			}
		}
		servletContext.setAttribute("pollResult", map);	//保存投票结果到ServletContext对象中
		response.setContentType("text/html;charset=UTF-8");	//设置响应的类型和编码方式，如果不设置弹出的对话框中的文字将乱码
		PrintWriter out=response.getWriter();
		out.println("<script>alert('投票成功！');window.location.href='showResult.jsp';</script>");
		
	}

}

package com.wgh.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.servlet.ServletUtilities;

import com.wgh.util.ChartUtil;
/**
 * 制图Servlet类，用于获取图片
 */
@WebServlet("/ChartServlet")
public class ChartServlet extends HttpServlet {
	private static final long serialVersionUID = -3308103803568844498L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 显示样式（是否为3D效果）
		String style = request.getParameter("style");
		String fileName = null;		// 生成图片的文件名
		if(style != null && "3d".equals(style)){
			// 获取生成图片的名称
			fileName = ServletUtilities.saveChartAsJPEG(
					ChartUtil.createChart(true), 500, 300, request.getSession());
		}else{
			fileName = ServletUtilities.saveChartAsJPEG(
					ChartUtil.createChart(false), 500, 300, request.getSession());
		}
		// 获取图片的路径
		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
		// 将路径放到request对象中
		request.setAttribute("graphURL", graphURL);
		// 页面转发到result.jsp
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}

}

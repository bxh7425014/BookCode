package com.wgh.sevlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/DownServlet")
public class DownServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DownServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}


public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	String path=request.getParameter("path");		//获取上传文件的路径
	path=new String(path.getBytes("iso-8859-1"),"UTF-8");
	File file = new File(path);						//根据该路径创建文件对象
	InputStream in = new FileInputStream(file);		//创建文件字节输入流
	OutputStream os = response.getOutputStream();	//创建输出流对象
	response.addHeader("Content-Disposition", "attachment;filename="
			+ new String(file.getName().getBytes("GBK"),"iso-8859-1"));	//设置应答头信息
	response.addHeader("Content-Length", file.length() + "");
	response.setCharacterEncoding("UTF-8");		
	response.setContentType("application/octet-stream");
	int data = 0;
	while ((data = in.read()) != -1) {				//循环读取文件
		os.write(data);								//向指定目录中写文件
	}
	os.close();										//关闭流
	in.close();
}
}

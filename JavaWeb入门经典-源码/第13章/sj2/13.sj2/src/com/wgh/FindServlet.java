package com.wgh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
@WebServlet("/")
public class FindServlet extends HttpServlet {
	private static final long serialVersionUID = 2497221668021907912L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SAXReader reader = new SAXReader();
		try {
			String file = getServletContext().getRealPath("/Student.xml");//获取要遍历XML文档的路径
			Document document = reader.read(new File(file));// 获取XML文件对应的XML文档对象

			Element root = document.getRootElement();	//获取根元素
			List<Element> list = root.elements("student");	//获取student子元素
			List<Student> students = new ArrayList<Student>();	//创建用于保存学生信息的List集合对象
			//通过for each语句遍历获取到的student子元素
			for (Element element : list) {
				Student s = new Student();
				s.setId(Integer.valueOf(element.attributeValue("id")));	//获取学号
				s.setName(element.elementText("name"));	//获取姓名
				s.setSex(element.elementText("sex"));	//获取性别
				s.setAge(Integer.valueOf(element.elementText("age")));	//获取年龄
				students.add(s);
			}
			request.setAttribute("list", students);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

}

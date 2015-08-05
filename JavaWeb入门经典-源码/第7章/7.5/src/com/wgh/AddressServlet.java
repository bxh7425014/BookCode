package com.wgh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet实现类AddressServlet
 */
@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置request的编码格式
		request.setCharacterEncoding("UTF-8");
		// 获取用户姓名
		String name = request.getParameter("name");
		// 获取性别
		String sex = request.getParameter("sex");
		// 获取家庭住址
		String address = request.getParameter("address");
		// 实例化User
		UserBean user = new UserBean();
		// 为姓名赋值
		user.setName(name);
		// 为性别赋值
		user.setSex(sex);
		// 为家庭住址赋值
		user.setAddress(address);
		// 获取ServletContext对象
		ServletContext application = getServletContext();
		// 从ServletContext中获取users
		List<UserBean> list = (List<UserBean>) application.getAttribute("users");
		// 判断List是否为null
		if(list == null){
			// 实例化list
			list = new ArrayList<UserBean>();
		}
		// 将user添加到List集合中
		list.add(user);
		// 将List放置于Application范围中
		application.setAttribute("users", list);
		// 创建RequestDispatcher对象
		RequestDispatcher dispatcher = request.getRequestDispatcher("manager.jsp");
		// 请求转发到manager.jsp页面
		dispatcher.forward(request, response);
	}

}

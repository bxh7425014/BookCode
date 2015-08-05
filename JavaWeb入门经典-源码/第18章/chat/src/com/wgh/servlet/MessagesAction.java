package com.wgh.servlet;

import java.io.*; //导入java.io包中的全部类文件
import java.text.*; //导入java.text包中的全部类文件
import java.util.*; //导入java.util包中的全部类文件

import javax.servlet.annotation.WebServlet; //导入servlet注解
import javax.servlet.http.HttpServlet; //导入HttpServlet类
import javax.servlet.http.HttpServletRequest; //导入HttpServletRequest类
import javax.servlet.http.HttpServletResponse; //导入HttpServletResponse类
import javax.servlet.http.HttpSession; //导入HttpSession类
import javax.servlet.http.HttpSessionBindingEvent; //导入HttpSessionBindingEvent类

import org.dom4j.Document; //导入dom4j包中的Document类
import org.dom4j.Element; //导入dom4j包中的Element类
import org.dom4j.io.OutputFormat; //导入dom4j包中的OutputFormat类
import org.dom4j.io.SAXReader; //导入dom4j包中的SAXReader类
import org.dom4j.io.XMLWriter; //导入dom4j包中的XMLWriter类

import com.wgh.user.UserInfo;	//导入对在线用户具体操作的类
import com.wgh.user.UserListener;	//导入用户监听器

@WebServlet("/MessagesAction")
public class MessagesAction extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		if ("getMessages".equals(action)) { // 从XML文件中读取聊天信息
			this.getMessages(request, response);
		} else if ("sendMessage".equals(action)) { // 发送聊天信息
			this.sendMessages(request, response);
		} else if ("loginRoom".equals(action)) { // 登录时，写入系统公告
			this.loginRoom(request, response);
		} else if ("exitRoom".equals(action)) {
			this.exitRoom(request, response); // 退出时，写入系统公告
		}
	}

	// 读取保存聊天信息的XML文件
	public void getMessages(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = request.getSession().getServletContext()
				.getRealPath("xml/" + newTime + ".xml");
		createFile(fileURL); // 当文件不存在时创建该文件
		/******************* 开始解析保存聊天内容的XML文件 **********************/
		try {
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			Document feedDoc = reader.read(new File(fileURL));// 获取XML文件对应的XML文档对象
			Element root = feedDoc.getRootElement(); // 获取根节点
			Element channel = root.element("messages"); // 获取messages节点
			Iterator items = channel.elementIterator("message"); // 获取message节点
			String messages = "";
			// 获取当前用户
			HttpSession session = request.getSession();
			String userName = "";
			if (null == session.getAttribute("username")) {
				request.setAttribute("messages", "error"); // 保存标记信息，表示用户账户已经过期
			} else {
				userName = session.getAttribute("username").toString();
				DateFormat df = DateFormat.getDateTimeInstance();
				while (items.hasNext()) {
					Element item = (Element) items.next();
					String sendTime = item.elementText("sendTime"); // 获取发言时间
					try {
						if (df.parse(sendTime).after(
								df.parse(session.getAttribute("loginTime")
										.toString()))
								|| sendTime.equals(session.getAttribute(
										"loginTime").toString())) {
							String from = item.elementText("from"); // 获取发言人
							String face = item.elementText("face"); // 获取表情
							String to = item.elementText("to"); // 获取接收者
							String content = item.elementText("content"); // 获取发言内容
							boolean isPrivate = Boolean.valueOf(item
									.elementText("isPrivate"));
							if (isPrivate) { // 获取私聊内容
								if (userName.equals(to)
										|| userName.equals(from)) {
									messages += "<font color='red'>[私人对话]</font><font color='blue'><b>"
											+ from
											+ "</b></font><font color='#CC0000'>"
											+ face
											+ "</font>对<font color='green'>["
											+ to
											+ "]</font>说："
											+ content
											+ "&nbsp;<font color='gray'>["
											+ sendTime + "]</font><br>";
								}
							} else if ("[系统公告]".equals(from)) { // 获取系统公告信息
								messages += "[系统公告]：" + content
										+ "&nbsp;<font color='gray'>["
										+ sendTime + "]</font><br>";
							} else { // 获取普通发言信息
								messages += "<font color='blue'><b>" + from
										+ "</b></font><font color='#CC0000'>"
										+ face
										+ "</font>对<font color='green'>[" + to
										+ "]</font>说：" + content
										+ "&nbsp;<font color='gray'>["
										+ sendTime + "]</font><br>";
							}
						}
					} catch (Exception e) {
						System.out.println("" + e.getMessage());
					}
				}
				request.setAttribute("messages", messages); // 保存获取的聊天信息
			}
			request.getRequestDispatcher("content.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登录时，写入系统公告

	public void loginRoom(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String username = request.getParameter("username"); // 获得登录用户名
		UserInfo user = UserInfo.getInstance(); // 获得UserInfo类的对象
		session.setMaxInactiveInterval(600); // 设置Session的过期时间为10分钟
		Vector vector = user.getList();
		boolean flag = true; // 标记是否登录的变量
		// 判断用户是否登录
		if (vector != null && vector.size() > 0) {
			if (vector.contains(username)) {
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println("<script language='javascript'>alert('该用户已经登录');window.location.href='index.jsp';</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				flag = false;
			}
		}
		// 保存用户信息
		if (flag) {
			UserListener ul = new UserListener(); // 创建UserListener的对象
			ul.setUser(username); // 添加用户
			user.addUser(ul.getUser()); // 添加用户到UserInfo类的对象中
			session.setAttribute("user", ul); // 将UserListener对象绑定到Session中
			session.setAttribute("username", username); // 保存当前登录的用户名
			session.setAttribute("loginTime", new Date().toLocaleString()); // 保存登录时间
			/** *******************开始系统公告********************************** */
			String newTime = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			String fileURL = request.getSession().getServletContext()
					.getRealPath("xml/" + newTime + ".xml");
			createFile(fileURL);// 判断XML文件是否存在，如果不存在则创建该文件
			// 获取当前用户
			try {
				SAXReader reader = new SAXReader(); // 实例化SAXReader对象
				Document feedDoc = reader.read(new File(fileURL));// 获取XML文件对应的XML文档对象
				Element root = feedDoc.getRootElement(); // 获取根节点
				Element messages = root.element("messages"); // 获取messages节点
				Element message = messages.addElement("message"); // 创建子节点message
				message.addElement("from").setText("[系统公告]"); // 创建子节点from
				message.addElement("face").setText(""); // 创建子节点face
				message.addElement("to").setText(""); // 创建子节点to
				message.addElement("content").addCDATA(
						"<font color='gray'>" + username + "走进了聊天室！</font>"); // 创建子节点content
				message.addElement("sendTime").setText(
						new Date().toLocaleString()); // 创建子节点sendTime
				message.addElement("isPrivate").setText("false"); // 创建子节点
				request.getRequestDispatcher("login_ok.jsp").forward(request,
						response);
				OutputFormat format = new OutputFormat(); // 创建OutputFormat对象
				XMLWriter writer = new XMLWriter(new FileWriter(fileURL),
						format);
				writer.write(feedDoc); // 向流写入数据
				writer.close(); // 关闭XMLWriter

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 退出时，写入系统公告
	public void exitRoom(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();// 获取HttpSession
		session.invalidate(); // 销毁session
		try {
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);// 重定向页面到登录页面
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 当用户下线时向XML文件中写入系统公告
	public void writeInfo(String user, HttpSessionBindingEvent arg0) {
		HttpSession session = arg0.getSession(); // 获取HttpSession
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = session.getServletContext().getRealPath(
				"xml/" + newTime + ".xml"); // 获取保存聊天内容的XML文件的文件名
		createFile(fileURL);// 判断XML文件是否存在，如果不存在则创建该文件
		// 获取当前用户
		try {
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			Document feedDoc = reader.read(new File(fileURL));// 获取XML文件对应的XML文档对象
			Element root = feedDoc.getRootElement(); // 获取根节点
			Element messages = root.element("messages"); // 获取messages节点
			Element message = messages.addElement("message"); // 创建子节点message
			message.addElement("from").setText("[系统公告]"); // 创建子节点from
			message.addElement("face").setText(""); // 创建子节点face
			message.addElement("to").setText(""); // 创建子节点to
			message.addElement("content").addCDATA(
					"<font color='gray'>" + user + "离开聊天室！</font>"); // 创建子节点content
			message.addElement("sendTime").setText(new Date().toLocaleString()); // 创建子节点sendTime
			message.addElement("isPrivate").setText("false"); // 创建子节点
			OutputFormat format = new OutputFormat(); // 创建OutputFormat对象
			XMLWriter writer = new XMLWriter(new FileWriter(fileURL), format);
			writer.write(feedDoc); // 向流写入数据
			writer.close(); // 关闭XMLWriter
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 发送聊天信息
	public void sendMessages(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		Random random = new Random();
		String from = request.getParameter("from"); // 发言人
		String face = request.getParameter("face"); // 表情
		String to = request.getParameter("to"); // 接收者
		String color = request.getParameter("color"); // 字体颜色
		String content = request.getParameter("content"); // 发言内容
		String isPrivate = request.getParameter("isPrivate"); // 是否为悄悄话
		String sendTime = new Date().toLocaleString(); // 发言时间
		/** *******************开始添加聊天信息********************************** */
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = request.getSession().getServletContext()
				.getRealPath("xml/" + newTime + ".xml");
		createFile(fileURL); // 判断文件是否存在，当文件不存在时创建该文件
		try {
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			Document feedDoc = reader.read(new File(fileURL));// 获取XML文件对应的XML文档对象
			Element root = feedDoc.getRootElement(); // 获取根节点
			Element messages = root.element("messages"); // 获取messages节点
			Element message = messages.addElement("message"); // 创建子节点message
			message.addElement("from").setText(from); // 创建子节点from
			message.addElement("face").setText(face); // 创建子节点face
			message.addElement("to").setText(to); // 创建子节点to
			message.addElement("content").addCDATA(
					"<font color='" + color + "'>" + content + "</font>"); // 创建子节点content
			message.addElement("sendTime").setText(sendTime); // 创建子节点sendTime
			message.addElement("isPrivate").setText(isPrivate); // 创建子节点

			OutputFormat format = new OutputFormat(); // 创建OutputFormat对象
			request.getRequestDispatcher(
					"MessagesAction?action=getMessages&nocache="
							+ random.nextInt(10000)).forward(request, response);
			XMLWriter writer = new XMLWriter(new FileWriter(fileURL), format);
			writer.write(feedDoc); // 向流写入数据
			writer.close(); // 关闭XMLWriter
			/** *****************添加结束******************************* */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据现在日期生成XML文件名，并判断该文件是否存在，如果不存在将创建该文件
	public void createFile(String fileURL) {
		/** **************判断XML文件是否存在，如果不存在则创建该文件********** */
		File file = new File(fileURL);
		if (!file.exists()) { // 判断文件是否存在，如果不存在，则创建该文件
			try {
				file.createNewFile(); // 创建文件
				String dataStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
				dataStr = dataStr + "<chat>\r\n";
				dataStr = dataStr + "<messages></messages>";
				dataStr = dataStr + "</chat>\r\n";
				byte[] content = dataStr.getBytes();
				FileOutputStream fout = new FileOutputStream(file);
				fout.write(content); // 将数据写入输出流
				fout.flush(); // 刷新缓冲区
				fout.close(); // 关闭输出流
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

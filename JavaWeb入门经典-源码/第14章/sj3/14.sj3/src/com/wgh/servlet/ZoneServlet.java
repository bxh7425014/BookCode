package com.wgh.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Servlet implementation class ZoneServlet
 */
@WebServlet("/ZoneServlet")
public class ZoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ZoneServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action"); // 获取action参数的值
		if ("getProvince".equals(action)) { // 获取省份和直辖市信息
			this.getProvince(request, response);
		} else if ("getCity".equals(action)) { // 获取地级市信息
			this.getCity(request, response);
		} else if ("getArea".equals(action)) {
			this.getArea(request, response);//获取县、县级市或区
		}
	}

	/**
	 * 获取省份和直辖市
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getProvince(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); // 设置响应的编码方式
		String fileURL = request.getRealPath("/xml/zone.xml"); // 获取XML文件的路径
		File file = new File(fileURL);
		Document document = null; // 声明Document对象
		Element country = null; // 声明表示根节点的Element对象
		String result = "";
		if (file.exists()) { // // 判断文件是否存在，如果存在，则读取该文件
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			try {
				document = reader.read(new File(fileURL)); // 获取XML文件对应的XML文档对象
				country = document.getRootElement(); // 获取根节点
				List<Element> provinceList = country.elements("province"); // 获取表示省份和直辖市的节点
				Element provinceElement = null;
				for (int i = 0; i < provinceList.size(); i++) {// 将获取的省份连接为一个以逗号分隔的字符串
					provinceElement = provinceList.get(i);
					result = result + provinceElement.attributeValue("name")
							+ ",";
				}
				result = result.substring(0, result.length() - 1); // 去除最后一个逗号
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result); // 输出获取的市县字符串
		out.flush();
		out.close();
	}

	/**
	 * 获取地级市
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getCity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); // 设置响应的编码方式
		String fileURL = request.getRealPath("/xml/zone.xml"); // 获取XML文件的路径
		File file = new File(fileURL);
		Document document = null; // 声明Document对象
		String result = "";
		if (file.exists()) { // 判断文件是否存在，如果存在，则读取该文件
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			try {
				document = reader.read(new File(fileURL)); // 获取XML文件对应的XML文档对象
				Element country = document.getRootElement(); // 获取根节点
				String selProvince = request.getParameter("parProvince"); // 获取选择的省份
				selProvince = new String(selProvince.getBytes("ISO-8859-1"),
						"UTF-8");
				Element item = (Element) country
						.selectSingleNode("/country/province[@name='"
								+ selProvince + "']");//获取指定name属性的省份节点
				List<Element> cityList = item.elements("city"); // 获取表示地级市的节点集合
				Element cityElement = null;
				for (int i = 0; i < cityList.size(); i++) {//将获取的地级市连接成以逗号分隔的字符串
					cityElement = cityList.get(i);
					result = result + cityElement.attributeValue("name") + ",";
				}
				result = result.substring(0, result.length() - 1); // 去除最后一个逗号

			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result); // 输出获取的地级市字符串
		out.flush();
		out.close();
	}

	/**
	 * 获取县、县级市或区
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getArea(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); // 设置响应的编码方式
		String fileURL = request.getRealPath("/xml/zone.xml"); // 获取XML文件的路径
		File file = new File(fileURL);
		Document document = null; // 声明Document对象
		String result = "";
		if (file.exists()) { //// 判断文件是否存在，如果存在，则读取该文件
			SAXReader reader = new SAXReader(); // 实例化SAXReader对象
			try {
				document = reader.read(new File(fileURL)); // 获取XML文件对应的XML文档对象
				Element country = document.getRootElement(); // 获取根节点

				String selProvince = request.getParameter("parProvince"); // 获取选择的省份
				String selCity = request.getParameter("parCity");	//获取选择的地级市
				selProvince = new String(selProvince.getBytes("ISO-8859-1"),
						"UTF-8");
				selCity = new String(selCity.getBytes("ISO-8859-1"), "UTF-8");
				Element item = (Element) country
						.selectSingleNode("/country/province[@name='"
								+ selProvince + "']");
				List<Element> cityList = item.elements("city"); // 获取表示地级市的节点集合
				Element itemArea = (Element) item
						.selectSingleNode("city[@name='" + selCity + "']");//获取指定的地级市节点
				result = itemArea.attributeValue("area");//获取县、县级市或区
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result); // 输出获取的县、县级市或区字符串
		out.flush();
		out.close();
	}
}

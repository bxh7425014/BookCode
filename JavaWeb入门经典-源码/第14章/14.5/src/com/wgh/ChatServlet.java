package com.wgh;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet实现类ChatServlet
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ChatServlet() {
        super();
    }

	/**
	 * 处理GET请求的方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * 处理POST请求的方法
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
        if ("send".equals(action)) {	//发送留言
            this.send(request, response);
        }else if("get".equals(action)){
        	this.get(request,response);
        }
		
	}
	public void send(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
		ServletContext application=getServletContext();		//获取application对象
		/*********************保存聊天信息****************************/
		response.setContentType("text/html;charset=UTF-8");
		String user=request.getParameter("user");	//获取用户昵称
		String speak=request.getParameter("speak");	//获取说话内容
		Vector<String> v=null;
		String message="["+user+"]说："+speak;			//组合说话内容
		if(null==application.getAttribute("message")){
			v=new Vector<String>();
		}else{
			v=(Vector<String>)application.getAttribute("message");
		}
		v.add(message);	
		application.setAttribute("message", v);		//将聊天内容保存到application中
        Random random = new Random();
		request.getRequestDispatcher("ChatServlet?action=get&nocache=" + random.nextInt(10000)).forward(request, response);
	}
	public void get(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html;charset=UTF-8");	//设置响应的内容类型及编码方式
		response.setHeader("Cache-Control", "no-cache");	//禁止页面缓存
		PrintWriter out = response.getWriter();	//获取输出流对象

		/*********************获取聊天信息****************************/
		ServletContext application=getServletContext();		//获取application对象
		String msg="";
		if(null!=application.getAttribute("message")){
			Vector<String> v_temp=(Vector<String>)application.getAttribute("message");
			for(int i=v_temp.size()-1;i>=0;i--){
				msg=msg+"<br>"+v_temp.get(i);
			}
		}else{
			msg="欢迎光临碧海聆音聊天室！";
		}
		out.println(msg);	//输出生成后的聊天信息
		out.close();	//关闭输出流对象
	}
}

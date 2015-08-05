package com.wgh.user;

import javax.servlet.http.HttpSessionBindingEvent;

import com.wgh.servlet.MessagesAction;

public class UserListener implements
		javax.servlet.http.HttpSessionBindingListener {
	private String user;
	private UserInfo container = UserInfo.getInstance();

	public UserListener() {
		user = "";
	}

	// 设置在线监听人员
	public void setUser(String user) {
		this.user = user;
	}

	// 获取在线监听
	public String getUser() {
		return this.user;
	}

	// 当Session有对象加入时执行的方法
	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("上线用户：" + this.user);

	}

	// 当Session有对象移除时执行的方法
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("下线用户：" + this.user);
		MessagesAction message=new MessagesAction();//实例化MessagesAction的对象
		message.writeInfo(this.user,arg0);	//将用户下线信息写入系统公告
		if (user != "") {
			container.removeUser(user);
		}
	}
}

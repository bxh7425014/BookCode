package com.wgh;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -287087073025401584L;
	private String info; // 提示信息属性

	// 添加用户信息的方法
	public String add() throws Exception {
		setInfo("添加用户信息");
		return "add";
	}

	// 修改用户信息的方法
	public String update() throws Exception {
		setInfo("修改用户信息");
		return "update";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}

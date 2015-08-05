package com.wgh.action;

import com.opensymphony.xwork2.ActionSupport;


public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -836802722839663844L;
	private String info;
	
	public String add(){
		info="添加用户信息";
		return "success";
	}
	
	public String update(){
		info="修改用户信息";
		return "update";
	}

	public String del(){
		info="删除用户信息";
		return "success";
	}
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}

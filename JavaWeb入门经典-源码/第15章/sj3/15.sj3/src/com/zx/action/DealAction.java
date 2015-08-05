package com.zx.action;
import com.opensymphony.xwork2.ActionSupport;

public class DealAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5914262228721952468L;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String execute(){
		return SUCCESS;	
		
	}
}

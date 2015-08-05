package com.wgh.factory;

import java.util.Set;

import com.wgh.product.Product;

public class Factory {

	private Integer factoryId;//生产商的id
	
	private String factoryName;//生产商名称
	private Set<Product> products;					//Set集合，一个厂商所对应的所有图书

	public Integer getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}
	public Set<Product> getProducts(){
		return products;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public void setProducts(Set<Product> product){
		this.products=product;
	}
	
}

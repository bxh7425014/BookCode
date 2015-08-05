package com.model;

public class CommodityForm {
private int id=0;
private String goodsName="";
private String introduce="";
private float price=0.0f;
public void setId(int id) {
	this.id = id;
}
public int getId() {
	return id;
}
public void setGoodsName(String goodsName) {
	this.goodsName = goodsName;
}
public String getGoodsName() {
	return goodsName;
}
public void setIntroduce(String introduce) {
	this.introduce = introduce;
}
public String getIntroduce() {
	return introduce;
}
public void setPrice(float price) {
	this.price = price;
}
public float getPrice() {
	return price;
}
}

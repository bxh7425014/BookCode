package service;

import java.io.Serializable;

public class Product 
{
    private String name;
    private int productNumber;
    private float price;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getProductNumber()
	{
		return productNumber;
	}
	public void setProductNumber(int productNumber)
	{
		this.productNumber = productNumber;
	}
	public float getPrice()
	{
		return price;
	}
	public void setPrice(float price)
	{
		this.price = price;
	}    
    
}


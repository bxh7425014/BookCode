package lee;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */

public class User
{
	private Integer id;
	private String name;
	private String gender;
	private double height;

	//无参数的构造器
	public User()
	{
	}
	//初始化全部属性的构造器
	public User(Integer id , String name , String gender , double height)
	{
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.height = height;
	}

	//id属性的setter和getter方法
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getId()
	{
		return this.id;
	}

	//name属性的setter和getter方法
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	//gender属性的setter和getter方法
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	public String getGender()
	{
		return this.gender;
	}

	//height属性的setter和getter方法
	public void setHeight(double height)
	{
		this.height = height;
	}
	public double getHeight()
	{
		return this.height;
	}
	//重写toString() 方法
	public String toString()
	{
		return "Person[name=" + name + ",gender=" + gender
			+ ",height=" + height + "]";
	}

}
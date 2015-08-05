package com.wgh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wgh.model.User;

public class UserDao {
	private static UserDao instance = null;
	/**
	 * 静态方法，返回本类的实例
	 * @return
	 */
	public static UserDao getInstance(){
		if(instance==null) instance = new UserDao();
		return instance;
	}
	/**
	 * 根据登录页输入的用户名和密码
	 * 查找数据库中匹配的用户信息
	 * @param name
	 * @param pwd
	 * @return 存在该用户信息返回true,否则返回false
	 */
	public boolean checkLogin(String name,String pwd){
		boolean result = false;
		Connection con = null;
		try{
			con = DBCon.getConn();
			String sql="select * from tb_user where name=? and pwd=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, pwd);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				result = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 判断用户名是否存在
	 * @param name
	 * @return 存在返回true ,否则返回false
	 */
	
public boolean checkUserName(String name){
	boolean result = false;
	Connection con = null;
	try{
		con = DBCon.getConn();
		String sql="select id from tb_user where name=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) result = true; //如果存在下一行，则说明用户名已存在
	}catch(Exception ex){
		ex.printStackTrace();
	}finally{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
	/**
	 * 保存用户注册信息
	 * @param user
	 * @return 保存成功返回true，否则返回false
	 */
	
public boolean saveUser(User user){
	boolean result = false;
	Connection con = null;
	try{
		con = DBCon.getConn();
		String sql="insert into tb_user(name,pwd,age,sex) values(?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPwd());
		stmt.setInt(3, user.getAge());
		stmt.setString(4, user.getSex());
		int count = stmt.executeUpdate();//执行SQL命令后，返回所影响的行数
		if(count==1){
			result = true;
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}finally{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
}

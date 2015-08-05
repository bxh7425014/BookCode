package com.wgh.dao;

import java.sql.*;

/**
 * @author Administrator
 */
public class UserDao {

	
	String url = "jdbc:mysql://localhost:3306/db_database10";
	String username="root"; 
	String password="111";  
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public UserDao() {  //通过构造方法加载数据库驱动
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("数据库加载失败");
        }
    }

    public boolean Connection() {        //创建数据库连接
        try {
            con = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("creatConnectionError!");
        }
        return true;
    }

    //对数据库的查询操作
    public ResultSet selectStatic(String sql) throws SQLException {
         ResultSet rs=null;
        if (con == null) {
            Connection();
        }
               try {
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);
         } catch (SQLException e) {
                     e.printStackTrace();
                    }
         closeConnection();
        return rs;
    }
    //更新数据库

    public boolean executeUpdate(String sql) {
        if (con == null) {
            Connection();
        }
        try {
            stmt = con.createStatement();
            int iCount = stmt.executeUpdate(sql);
            System.out.println("操作成功，所影响的记录数为" + String.valueOf(iCount));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        closeConnection();
        return true;
    }

    //关闭数据库的操作
    public void closeConnection() {
       if (con != null && stmt != null && rs != null) {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to close connection!");
            } finally {
                con = null;
            }
        }
    }
}

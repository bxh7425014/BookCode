package com.guo.myball;

import java.util.Vector;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//数据库操作类
public class SQLiteUtil 
{
	static SQLiteDatabase sld;
	//创建或打开数据库的方法
    public static void createOrOpenDatabase()
    {
    	try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.guo.myball/mydb", //当前应用程序只能在自己的包下创建数据库
	    			null, 								//CursorFactory
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //读写、若不存在则创建
	    	);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    //关闭数据库
    public static void closeDatabase()
    {
    	try
    	{
	    	sld.close();    
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
    }
    //建表
    public static void createTable(String sql)
    {
    	createOrOpenDatabase();//打开数据库
    	try
    	{
        	sld.execSQL(sql);//建表
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
    	closeDatabase();//关闭数据库
    }
  //插入记录的方法
    public static void insert(String sql)
    {
    	createOrOpenDatabase();//打开数据库
    	try
    	{
        	sld.execSQL(sql);
    	}
		catch(Exception e)
		{
            e.printStackTrace();
		}
		closeDatabase();//关闭数据库
    }
    //删除记录的方法
    public static  void delete(String sql)
    {
    	createOrOpenDatabase();//打开数据库
    	try
    	{
        	sld.execSQL(sql);
      	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//关闭数据库
    }
    //修改记录的方法
    public static void update(String sql)
    {   
    	createOrOpenDatabase();//打开数据库
    	try
    	{
        	sld.execSQL(sql);    	
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//关闭数据库
    }
    //查询的方法
    public static Vector<Vector<String>> query(String sql)
    {
    	createOrOpenDatabase();//打开数据库
    	Vector<Vector<String>> vector=new Vector<Vector<String>>();//新建存放查询结果的向量
    	try
    	{
           Cursor cur=sld.rawQuery(sql, new String[]{});
        	while(cur.moveToNext())
        	{
        		Vector<String> v=new Vector<String>();
        		int col=cur.getColumnCount();		//返回每一行都多少字段
        		for( int i=0;i<col;i++)
				{
					v.add(cur.getString(i));					
				}				
				vector.add(v);
        	}
        	cur.close();		
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		closeDatabase();//关闭数据库
		return vector;
    }  
}
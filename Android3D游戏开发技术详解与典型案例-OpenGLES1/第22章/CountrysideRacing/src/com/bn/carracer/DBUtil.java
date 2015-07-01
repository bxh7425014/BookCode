package com.bn.carracer;
import java.util.ArrayList;
import java.util.Date;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class DBUtil{
	static SQLiteDatabase sld;	
	public static void createOrOpenDatabase()
	{
		try
    	{
	    	sld=SQLiteDatabase.openDatabase
	    	(
	    			"/data/data/com.bn.carracer/mydb2", //数据库所在路径
	    			null, 								//游标工厂
	    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //读写、若不存在则创建
	    	);	    		
	    	String sql="create table if not exists history(time num(5),playdate varchar2(40))";
	    	sld.execSQL(sql); 		
    	}
    	catch(Exception e)
    	{
    		Log.d("wrong", "wrong");
    		e.printStackTrace();
    	}
	}
	//关闭数据库的方法
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
    //与数据库中数据比较
    public static boolean getNewRecord(double time)
    {	
    	double minTime=0;    	
    	try
    	{
    		createOrOpenDatabase();
    		String sql="select min(time) from history";   	
        	Cursor cur=sld.rawQuery(sql, new String[]{});
        	cur.moveToNext();
        	minTime=cur.getDouble(0);
        	if(minTime==0){minTime=Double.MAX_VALUE;}   	
        	cur.close();
        	closeDatabase();   
        	
        	insert(time);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}  
    	if(time<minTime)
    	{
    		return true;
    	}else
    	{
    		return false;
    	}
    	
    }

  //插入记录的方法
    public static void insert(double time)
    {
    	Date date=new Date();
    	StringBuilder sb=new StringBuilder();
    	sb.append(date.getYear()+1900);
    	sb.append("-");
    	sb.append((date.getMonth()>8)?(date.getMonth()+1):"0"+(date.getMonth()+1));
    	sb.append("-");
    	sb.append((date.getDate()>9)?(date.getDate()):("0"+date.getDate()));
    	sb.append(" ");
    	sb.append((date.getHours()>9)?(date.getHours()):("0"+date.getHours()));
    	sb.append(":");
    	sb.append((date.getMinutes()>9)?(date.getMinutes()):("0"+date.getMinutes()));
    	sb.append(":");
    	sb.append((date.getSeconds()>9)?(date.getSeconds()):("0"+date.getSeconds()));
    	
    	Log.d("datetemp", sb.toString()+"");
    	try
    	{
    		createOrOpenDatabase();
    		String sql="insert into history values("+time+",'"+sb.toString()+"')";
        	Log.d("sql", sql+"");
        	sld.execSQL(sql);
        	closeDatabase();  
    	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    }
    //获取整个列表
  //查询的方法
    public static ArrayList<String[]> getResult()
    {
    	ArrayList<String[]> al=new ArrayList<String[]>();    	
    	try
    	{
    		createOrOpenDatabase();//打开数据库
    		String sql="select time,playdate from history order by time";
    		Cursor cur=sld.rawQuery(sql, new String[]{});
    		while(cur.moveToNext())
    		{
    			String[] ta=new String[2];
    			int time=cur.getInt(0);
    			ta[0]=((time/60>9)?time/60:"0"+(time/60))+":"+((time%60>9)?time%60:"0"+(time%60));
                ta[1]=cur.getString(1);
    			al.add(ta);
    		}
    		cur.close();
        	closeDatabase();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}    	
    	return al;
    }
}

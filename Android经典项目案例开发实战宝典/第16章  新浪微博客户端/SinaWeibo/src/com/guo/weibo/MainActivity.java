package com.guo.weibo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */	
	DataHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //获取账号列表  
        dbHelper=new DataHelper(this);  
        //获取数据库用户列表
        final List<UserInfo> userList= dbHelper.GetUserList(true);  
        //关闭数据库
        dbHelper.Close();
        //为了看到载入界面的效果，我们延迟1s执行判断
        TimerTask task=new TimerTask(){
        		public void run()
        		{
        			  //如果为空说明第一次使用，弹出对话框引导用户进行授权
        			 if(userList.isEmpty())
        		        {  
        		        	Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
        		        	mBuilder.setTitle("提示");
        		        	mBuilder.setMessage("您还未创建任何账户，是否现在创建？");
        		        	mBuilder.setPositiveButton("确定", new OnClickListener(){      		
        						@Override
        						public void onClick(DialogInterface dialog, int which) {
        							// TODO Auto-generated method stub
        							Intent intent = new Intent();  
        							//跳到AuthorizeActivity页面进行OAuth认证  
        					        intent.setClass(MainActivity.this, AuthorizeActivity.class);  
        					        startActivity(intent);  
        					        //关闭当前界面
        					        MainActivity.this.finish();
        						}     
        		        	}).setNegativeButton("取消", new OnClickListener(){
        						@Override
        						public void onClick(DialogInterface dialog, int which) {
        							// TODO Auto-generated method stub
        							//取消则关闭本程序
        							MainActivity.this.finish();
        						}      		
        		        	});
        		        	mBuilder.create().show();        
        		        }  
        		        else  
        		        {  
        			        Intent it=new Intent();
        			        //如果不为空，则跳转到登陆界面
        			        it.setClass(MainActivity.this, LoginActivity.class);
        			        startActivity(it);
        			        //关闭本程序
        			        MainActivity.this.finish();
        		        }
        		}};
	    Timer timer=new Timer();
	    timer.schedule(task,1000);
    }
}
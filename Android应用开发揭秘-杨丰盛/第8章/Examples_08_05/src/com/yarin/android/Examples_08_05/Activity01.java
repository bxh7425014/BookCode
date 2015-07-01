package com.yarin.android.Examples_08_05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity01 extends Activity
{
	private final String		DEBUG_TAG	= "Activity01";
	//服务器IP、端口
	private static final String SERVERIP = "192.168.1.110";
	private static final int SERVERPORT = 54321;
	private Thread mThread = null;
	private Socket				mSocket		= null;
	private Button				mButton_In	= null;
	private Button				mButton_Send= null;
	private EditText			mEditText01 	= null;
	private EditText			mEditText02 	= null;
	private BufferedReader		mBufferedReader	= null;
	private PrintWriter mPrintWriter = null;
	private  String mStrMSG = "";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mButton_In = (Button)findViewById(R.id.Button_In);
		mButton_Send = (Button)findViewById(R.id.Button_Send);
		mEditText01=(EditText)findViewById(R.id.EditText01);
		mEditText02=(EditText)findViewById(R.id.EditText02);
		
		//登陆
		mButton_In.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				try 
				{
					//连接服务器
					mSocket = new Socket(SERVERIP, SERVERPORT);	
					//取得输入、输出流
					mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
					mPrintWriter=new PrintWriter(mSocket.getOutputStream(), true);   
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}
			}
		});
		//发送消息
		mButton_Send.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				try 
				{
					//取得编辑框中我们输入的内容
			    	String str = mEditText02.getText().toString() + "\n";
			    	//发送给服务器
			    	mPrintWriter.print(str);
			    	mPrintWriter.flush();
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}
			}
		});
		
		mThread = new Thread(mRunnable);
		mThread.start();
	}
	
	//线程:监听服务器发来的消息
	private Runnable	mRunnable	= new Runnable() 
	{
		public void run()
		{
			while (true)
			{
				try
				{
					if ( (mStrMSG = mBufferedReader.readLine()) != null )
					{
						//消息换行
						mStrMSG+="\n";
						mHandler.sendMessage(mHandler.obtainMessage());
					}
					// 发送消息
				}
				catch (Exception e)
				{
					Log.e(DEBUG_TAG, e.toString());
				}
			}
		}
	};
	
	Handler		mHandler	= new Handler() 
	{										
		  public void handleMessage(Message msg)										
		  {											
			  super.handleMessage(msg);		
			  // 刷新											
			  try											
			  {	
				  //将聊天记录添加进来
				  mEditText01.append(mStrMSG);			
			  }											
			  catch (Exception e)											
			  {																					
				  Log.e(DEBUG_TAG, e.toString());											
			  }										
		  }									
	 };
} 


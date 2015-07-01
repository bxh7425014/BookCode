package com.yarin.android.Examples_08_04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity01 extends Activity
{
	private final String		DEBUG_TAG	= "Activity01";
	
	private TextView	mTextView=null;
	private EditText	mEditText=null;
	private Button		mButton=null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mButton = (Button)findViewById(R.id.Button01);
		mTextView=(TextView)findViewById(R.id.TextView01);
		mEditText=(EditText)findViewById(R.id.EditText01);
		
		//登陆
		mButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Socket socket = null;
				String message = mEditText.getText().toString() + "\r\n"; 
				try 
				{	
					//创建Socket
					socket = new Socket("192.168.1.110",54321); 
					//向服务器发送消息
					PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);      
					out.println(message); 
					
					//接收来自服务器的消息
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String msg = br.readLine(); 
					
					if ( msg != null )
					{
						mTextView.setText(msg);
					}
					else
					{
						mTextView.setText("数据错误!");
					}
					//关闭流
					out.close();
					br.close();
					//关闭Socket
					socket.close(); 
				}
				catch (Exception e) 
				{
					// TODO: handle exception
					Log.e(DEBUG_TAG, e.toString());
				}
			}
		});
	}
}

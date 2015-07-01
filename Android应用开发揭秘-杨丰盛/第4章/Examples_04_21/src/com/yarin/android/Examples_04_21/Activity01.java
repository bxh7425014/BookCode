package com.yarin.android.Examples_04_21;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

public class Activity01 extends Activity
{
	//声明ProgressBar对象
	private ProgressBar m_ProgressBar;
	private ProgressBar m_ProgressBar2;
	private Button mButton01;
	protected static final int GUI_STOP_NOTIFIER = 0x108;
	protected static final int GUI_THREADING_NOTIFIER = 0x109;
	public int intCounter=0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//设置窗口模式，，因为需要显示进度条在标题栏
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setProgressBarVisibility(true);
		setContentView(R.layout.main);
		
		//取得ProgressBar
		m_ProgressBar = (ProgressBar) findViewById(R.id.ProgressBar01);
		m_ProgressBar2= (ProgressBar) findViewById(R.id.ProgressBar02);
		mButton01 = (Button)findViewById(R.id.Button01); 
		
		m_ProgressBar.setIndeterminate(false);
		m_ProgressBar2.setIndeterminate(false);
		
		//当按钮按下时开始执行,
	    mButton01.setOnClickListener(new Button.OnClickListener()
	    {
	      @Override
	      public void onClick(View v)
	      {
	        // TODO Auto-generated method stub
	    	  
	    	  //设置ProgressBar为可见状态
	    	  m_ProgressBar.setVisibility(View.VISIBLE);
	    	  m_ProgressBar2.setVisibility(View.VISIBLE);
	    	  //设置ProgressBar的最大值
	    	  m_ProgressBar.setMax(100);
	    	  //设置ProgressBar当前值
	    	  m_ProgressBar.setProgress(0);
	    	  m_ProgressBar2.setProgress(0);

	    	  //通过线程来改变ProgressBar的值
				new Thread(new Runnable() {
					public void run()
					{
						for (int i = 0; i < 10; i++)
						{
							try
							{
								intCounter = (i + 1) * 20;
								Thread.sleep(1000);

								if (i == 4)
								{
									Message m = new Message();

									m.what = Activity01.GUI_STOP_NOTIFIER;
									Activity01.this.myMessageHandler.sendMessage(m);
									break;
								}
								else
								{
									Message m = new Message();
									m.what = Activity01.GUI_THREADING_NOTIFIER;
									Activity01.this.myMessageHandler.sendMessage(m);
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});
	}

	  Handler myMessageHandler = new Handler()
	  {
	    // @Override 
		  public void handleMessage(Message msg)
		  {
			  switch (msg.what)
			  {
			  //ProgressBar已经是对大值
			  case Activity01.GUI_STOP_NOTIFIER:
				  m_ProgressBar.setVisibility(View.GONE);
				  m_ProgressBar2.setVisibility(View.GONE);
				  Thread.currentThread().interrupt();
				  break;
			  case Activity01.GUI_THREADING_NOTIFIER:
				  if (!Thread.currentThread().isInterrupted())
				  {
					  // 改变ProgressBar的当前值
					  m_ProgressBar.setProgress(intCounter);
					  m_ProgressBar2.setProgress(intCounter);
					  
					  // 设置标题栏中前景的一个进度条进度值
					  setProgress(intCounter*100);
					  // 设置标题栏中后面的一个进度条进度值
					  setSecondaryProgress(intCounter*100);//
				  }
				  break;
			  }
			  super.handleMessage(msg);
		 }
	  };
}

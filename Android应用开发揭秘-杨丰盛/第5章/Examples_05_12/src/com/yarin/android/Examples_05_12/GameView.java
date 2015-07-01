package com.yarin.android.Examples_05_12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Bitmap对象 */
	Bitmap	mBitQQ	= null;
	
	Paint   mPaint = null;
	
	/* 创建一个缓冲区 */
	Bitmap	mSCBitmap = null;
	
	/* 创建Canvas对象 */
	Canvas mCanvas = null;   
	
	public GameView(Context context)
	{
		super(context);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
		
		/* 创建屏幕大小的缓冲区 */
		mSCBitmap=Bitmap.createBitmap(320, 480, Config.ARGB_8888);  
		
		/* 创建Canvas */
		mCanvas = new Canvas();  
		
		/* 设置将内容绘制在mSCBitmap上 */
		mCanvas.setBitmap(mSCBitmap); 
		
		mPaint = new Paint();
		
		/* 将mBitQQ绘制到mSCBitmap上 */
		mCanvas.drawBitmap(mBitQQ, 0, 0, mPaint);
		
		/* 开启线程 */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 将mSCBitmap显示到屏幕上 */
		canvas.drawBitmap(mSCBitmap, 0, 0, mPaint);
	}
	
	// 触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}


	// 按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}
	
	
	/**
	 * 线程处理
	 */
	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			//使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}


package com.yarin.android.Examples_05_06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Paint对象 */
	private Paint mPaint = null;
	private int   mICount = 0;
	
	/* 声明TextUtil对象 */
	private TextUtil mTextUtil = null;
	public GameView(Context context)
	{
		super(context);
		/* 构建对象 */
		mPaint = new Paint();
		
		String string = "测试自动换行\n\n设置文字自动换行abcdefgh\niklmnopqrst换行123347465\n43756245Android\n设置文字自动换行abcdefgh\niklmnopqrst换行123347465\n43756245Android";
		
		/* 实例化TextUtil */
		mTextUtil = new TextUtil(string,5,50,300,80,0x0,0xffffff,255,16);
		
		/* 初始化TextUtil */
		mTextUtil.InitText(string,5,50,300,80,0x0,0xffffff,255,16);
		
		/* 开启线程  */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 设置背景颜色 */
		canvas.drawColor(Color.BLACK);
		
		mPaint.setAntiAlias(true);
		
		if ( mICount < 100 )
		{
			mICount++;
		}
		
		mPaint.setColor(Color.RED);
		
		canvas.drawText("装在进度："+mICount+"%......", 10, 20, mPaint);

		/* 绘制TextUtil：实现自动换行 */
		mTextUtil.DrawText(canvas);
	}
	
	// 触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return mTextUtil.KeyDown(keyCode, event);
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
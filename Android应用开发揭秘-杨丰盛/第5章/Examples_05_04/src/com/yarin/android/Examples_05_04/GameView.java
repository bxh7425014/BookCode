package com.yarin.android.Examples_05_04;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Paint对象 */
	private Paint				mPaint	= null;

	public GameView(Context context)
	{
		super(context);
		/* 构建对象 */
		mPaint = new Paint();

		/* 开启线程 */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 设置画布的颜色 */
		canvas.drawColor(Color.BLACK);
		
		/* 设置取消锯齿效果 */
		mPaint.setAntiAlias(true);
		
		/* 设置裁剪区域 */
		canvas.clipRect(10, 10, 280, 260);
		
		/* 线锁定画布 */
		canvas.save();
		/* 旋转画布 */
		canvas.rotate(45.0f); 
		
		/* 设置颜色及绘制矩形 */
		mPaint.setColor(Color.RED);
		canvas.drawRect(new Rect(15,15,140,70), mPaint);
		
		/* 解除画布的锁定 */
		canvas.restore(); 
		
		/* 设置颜色及绘制另一个矩形 */
		mPaint.setColor(Color.GREEN);
		canvas.drawRect(new Rect(150,75,260,120), mPaint);
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
			// 使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}


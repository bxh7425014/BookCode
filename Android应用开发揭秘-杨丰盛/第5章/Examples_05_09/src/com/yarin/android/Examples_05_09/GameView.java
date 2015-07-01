package com.yarin.android.Examples_05_09;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Bitmap对象 */
	Bitmap	mBitQQ	= null;
	int		BitQQwidth	= 0;
	int		BitQQheight	= 0;

	float   Scale	= 1.0f;

	/* 构建Matrix对象 */
	Matrix mMatrix = new Matrix();
	
	public GameView(Context context)
	{
		super(context);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();

		/* 得到图片的宽度和高度 */
		BitQQwidth = mBitQQ.getWidth();
		BitQQheight = mBitQQ.getHeight();
		
		/* 开启线程 */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 重置mMatrix */
		mMatrix.reset();
		
		/* 设置缩放 */
		mMatrix.postScale(Scale,Scale);
		
		/* 按mMatrix得旋转构建新的Bitmap */
		Bitmap mBitQQ2 = Bitmap.createBitmap(mBitQQ, 0, 0, BitQQwidth,BitQQheight, mMatrix, true);

		/* 绘制旋转之后的图片 */
		GameView.drawImage(canvas, mBitQQ2, (320-BitQQwidth)/2, 10);
		
		mBitQQ2 = null;
	}
	
	// 触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//左方向键
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
		{
			if ( Scale > 0.3 )
			{
				Scale-=0.1;
			}
		}
		//右方向键
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			if ( Scale < 1.5 )
			{
				Scale+=0.1;	
			}		
		}
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
	
	/**
	 * 绘制一个Bitmap
	 * @param canvas	画布
	 * @param bitmap	图片
	 * @param x			屏幕上的x坐标
	 * @param y			屏幕上的y坐标
	 */
	public static void drawImage(Canvas canvas, Bitmap bitmap, int x, int y)
	{
		/* 绘制图像 */
		canvas.drawBitmap(bitmap, x, y, null);
	}
}


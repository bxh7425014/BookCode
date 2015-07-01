package com.yarin.android.Examples_05_07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Paint对象 */
	private Paint	mPaint		= null;

	/* 创建两个图片对象 */
	Bitmap			mBitQQ		= null;
	Bitmap			mBitDestTop	= null;

	int				miDTX		= 0;

	public GameView(Context context)
	{
		super(context);
		
		mPaint = new Paint();
		
		miDTX		= 0;
		
		/* 从资源文件中装载图片 */
		//getResources()->得到Resources
		//getDrawable()->得到资源中的Drawable对象，参数为资源索引ID
		//getBitmap()->得到Bitmap
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
		
		mBitDestTop = ((BitmapDrawable) getResources().getDrawable(R.drawable.desktop)).getBitmap();
		
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 清屏效果 */
		canvas.drawColor(Color.GRAY);
		
		/* 在屏幕(0,0)处绘制图片mBitQQ */
		GameView.drawImage(canvas, mBitQQ, 0, 0);
		
		/* 在制定位置按指定裁剪的渔区进行绘制 */
		//getWidth()->得到图片的宽度
		//getHeight()->得到图片的高度
		GameView.drawImage(canvas, mBitDestTop, miDTX, mBitQQ.getHeight(), mBitDestTop.getWidth(), mBitDestTop.getHeight()/2, 0, 0);
		
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
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			if (miDTX > 0)
			{
				miDTX--;
			}
		}
		//右方向键
		else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			if (miDTX+mBitDestTop.getWidth() < 320)
			{
				miDTX++;
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
	
	/*------------------------------------
	 * 绘制图片
	 *
	 * @param		x 屏幕上的x坐标	
	 * @param		y 屏幕上的y坐标
	 * @param		w 要绘制的图片的宽度	
	 * @param		h 要绘制的图片的高度
	 * @param		bx图片上的x坐标
	 * @param		by图片上的y坐标
	 *
	 * @return		null
	 ------------------------------------*/
	public static void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w, int h, int bx, int by)
	{
		Rect src = new Rect();// 图片
		Rect dst = new Rect();// 屏幕
		
		src.left = bx;
		src.top = by;
		src.right = bx + w;
		src.bottom = by + h;
		
		dst.left = x;
		dst.top = y;
		dst.right = x + w;
		dst.bottom = y + h;
		canvas.drawBitmap(blt, src, dst, null);
		
		src = null;
		dst = null;
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


package com.yarin.android.Examples_05_02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView 
							 implements SurfaceHolder.Callback,Runnable
{
	//控制循环
	boolean			mbLoop			= false;
	
	//定义SurfaceHolder对象
	SurfaceHolder	mSurfaceHolder	= null;
	int				miCount			= 0;
	int				y				= 50;


	public GameSurfaceView(Context context)
	{
		super(context);

		// 实例化SurfaceHolder
		mSurfaceHolder = this.getHolder();

		// 添加回调
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);

		mbLoop = true;
	}


	// 在surface的大小发生改变时激发
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	// 在surface创建时激发
	public void surfaceCreated(SurfaceHolder holder)
	{
		//开启绘图线程
		new Thread(this).start();
	}

	// 在surface销毁时激发
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// 停止循环
		mbLoop = false;
	}

	// 绘图循环
	public void run()
	{
		while (mbLoop)
		{
			try
			{
				Thread.sleep(200);
			}
			catch (Exception e)
			{
				
			}
			synchronized( mSurfaceHolder )
			{
				Draw();
			}
			
		}
	}

	// 绘图方法
	public void Draw()
	{
		//锁定画布，得到canvas
		Canvas canvas= mSurfaceHolder.lockCanvas();

		if (mSurfaceHolder==null || canvas == null )
		{
			return;
		}
		
		if (miCount < 100)
		{
			miCount++;
		}
		else
		{
			miCount = 0;
		}
		// 绘图
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		//绘制矩形--清屏作用
		canvas.drawRect(0, 0, 320, 480, mPaint);
		switch (miCount % 4)
		{
		case 0:
			mPaint.setColor(Color.BLUE);
			break;
		case 1:
			mPaint.setColor(Color.GREEN);
			break;
		case 2:
			mPaint.setColor(Color.RED);
			break;
		case 3:
			mPaint.setColor(Color.YELLOW);
			break;
		default:
			mPaint.setColor(Color.WHITE);
			break;
		}
		// 绘制矩形--后面我们将详细讲解
		canvas.drawCircle((320 - 25) / 2, y, 50, mPaint);
		// 绘制后解锁，绘制后必须解锁才能显示
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}
}


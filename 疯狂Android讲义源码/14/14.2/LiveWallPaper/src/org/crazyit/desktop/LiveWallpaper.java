package org.crazyit.desktop;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class LiveWallpaper extends WallpaperService
{
	// 实现WallpaperService必须实现的抽象方法
	@Override
	public Engine onCreateEngine()
	{
		// 返回自定义的Engine
		return new MyEngine();
	}

	class MyEngine extends Engine
	{
		// 记录程序界面是否可见
		private boolean mVisible;
		// 记录当前当前用户动作事件的发生位置
		private float mTouchX = -1;
		private float mTouchY = -1;
		// 记录当前圆圈的绘制位置
		private float cx = 15;
		private float cy = 20;
		// 定义画笔
		private Paint mPaint = new Paint();
		// 定义一个Handler
		Handler mHandler = new Handler();
		// 定义一个周期性执行的任务
		private final Runnable drawTarget = new Runnable()
		{
			public void run()
			{
				drawFrame();
			}
		};

		@Override
		public void onCreate(SurfaceHolder surfaceHolder)
		{
			super.onCreate(surfaceHolder);
			// 初始化画笔
			mPaint.setColor(0xffffffff);
			mPaint.setAntiAlias(true);
			mPaint.setStrokeWidth(2);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStyle(Paint.Style.STROKE);
			// 设置处理触摸事件
			setTouchEventsEnabled(true);
		}

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			// 删除回调
			mHandler.removeCallbacks(drawTarget);
		}

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			mVisible = visible;
			// 当界面可见时候，执行drawFrame()方法。
			if (visible)
			{
				// 动态地绘制图形
				drawFrame();
			}
			else
			{
				// 如果界面不可见，删除回调
				mHandler.removeCallbacks(drawTarget);
			}
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
			float yStep, int xPixels, int yPixels)
		{
			drawFrame();
		}

		// // 当屏幕大小改变时候调用该方法
		// @Override
		// public void onSurfaceChanged(SurfaceHolder holder, int format,
		// int width, int height)
		// {
		// super.onSurfaceChanged(holder, format, width, height);
		// drawFrame();
		// }
		//
		// @Override
		// public void onSurfaceCreated(SurfaceHolder holder)
		// {
		// super.onSurfaceCreated(holder);
		// }
		//
		// @Override
		// public void onSurfaceDestroyed(SurfaceHolder holder)
		// {
		// super.onSurfaceDestroyed(holder);
		// mVisible = false;
		// mHandler.removeCallbacks(drawTarget);
		// }

		@Override
		public void onTouchEvent(MotionEvent event)
		{
			// 如果检测到滑动操作
			if (event.getAction() == MotionEvent.ACTION_MOVE)
			{
				mTouchX = event.getX();
				mTouchY = event.getY();
			}
			else
			{
				mTouchX = -1;
				mTouchY = -1;
			}
			super.onTouchEvent(event);
		}

		// 定义绘制图形的工具方法
		private void drawFrame()
		{
			// 获取该壁纸的SurfaceHolder
			final SurfaceHolder holder = getSurfaceHolder();
			Canvas c = null;
			try
			{
				// 对画布加锁
				c = holder.lockCanvas();
				if (c != null)
				{
					c.save();
					// 绘制背景色
					c.drawColor(0xff000000);
					// 在触碰点绘制圆圈
					drawTouchPoint(c);
					// 绘制圆圈
					c.drawCircle(cx, cy, 80, mPaint);
					c.restore();
				}
			}
			finally
			{
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}
			mHandler.removeCallbacks(drawTarget);
			// 调度下一次重绘
			if (mVisible)
			{
				cx += 15;
				cy += 20;
				// 如果cx、cy移出屏幕后从左上角重新开始
				if (cx > 320)
					cx = 15;
				if (cy > 400)
					cy = 20;
				// 指定0.1秒后重新执行mDrawCube一次
				mHandler.postDelayed(drawTarget, 100);
			}
		}

		// 在屏幕触碰点绘制圆圈
		private void drawTouchPoint(Canvas c)
		{
			if (mTouchX >= 0 && mTouchY >= 0)
			{
				c.drawCircle(mTouchX, mTouchY, 40, mPaint);
			}
		}
	}
}
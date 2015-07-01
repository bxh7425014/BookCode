package net.blogjava.mobile.livewallpapers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class LiveWallpaperPainting extends Thread
{

	private SurfaceHolder surfaceHolder;
	private Context context;

	private boolean wait;
	private boolean run;

	/* 尺寸和半径  */
	private int width;
	private int height;
	private int radius;

	/** 触摸点 */
	private List<TouchPoint> points;

	/* 时间轨迹 */
	private long previousTime;

	public LiveWallpaperPainting(SurfaceHolder surfaceHolder, Context context,
			int radius)
	{

		this.surfaceHolder = surfaceHolder;
		this.context = context;
		// 直到surface被创建和显示时才开始动画
		this.wait = true;
		// 初始化触摸点
		this.points = new ArrayList<TouchPoint>();
		// 初始化半径
		this.radius = radius;
	}

	//  通过设置页面可以改变圆的半径
	public void setRadius(int radius)
	{
		this.radius = radius;
	}

    // 暂停实时壁纸的动画
	public void pausePainting()
	{
		this.wait = true;
		synchronized (this)
		{
			this.notify();
		}
	}

	//  恢复在实时壁纸上绘制彩色实心圆	 
	public void resumePainting()
	{
		this.wait = false;
		synchronized (this)
		{
			this.notify();
		}
	}

    //  停止在实时壁纸上绘制彩色实心圆
	public void stopPainting()
	{
		this.run = false;
		synchronized (this)
		{
			this.notify();
		}
	}

	@Override
	public void run()
	{
		this.run = true;
		Canvas canvas = null;
		while (run)
		{
			try
			{
				canvas = this.surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder)
				{
					doDraw(canvas);
				}
			} finally
			{
				if (canvas != null)
				{
					this.surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			// 如果不需要动画则暂停动画
			synchronized (this)
			{
				if (wait)
				{
					try
					{
						wait();
					}
					catch (Exception e)
					{
					}
				}
			}
		}
	}

	public void setSurfaceSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		synchronized (this)
		{
			this.notify();
		}
	}

	public void doTouchEvent(MotionEvent event)
	{
		synchronized (this.points)
		{
			
			int color = new Random().nextInt(Integer.MAX_VALUE);
			points.add(new TouchPoint((int) event.getX(), (int) event.getY(),
					color, Math.min(width, height) / this.radius));
		}
		this.wait = false;
		synchronized (this)
		{
			notify();
		}
	}

	private void doDraw(Canvas canvas)
	{
		long currentTime = System.currentTimeMillis();
		long elapsed = currentTime - previousTime;
		if (elapsed > 20)
		{
			BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.background);
			//  绘制实时壁纸的背景图
			canvas.drawBitmap(bitmapDrawable.getBitmap(), 0, 0, new Paint());
			
			// 绘制触摸点
			Paint paint = new Paint();
			List<TouchPoint> pointsToRemove = new ArrayList<TouchPoint>();
			synchronized (this.points)
			{
				for (TouchPoint point : points)
				{
					paint.setColor(point.color);
					point.radius -= elapsed / 20;
					if (point.radius <= 0)
					{
						pointsToRemove.add(point);
					}
					else
					{
						canvas
								.drawCircle(point.x, point.y, point.radius,
										paint);
					}
				}
				points.removeAll(pointsToRemove);
			}
			previousTime = currentTime;
			if (points.size() == 0)
			{
				wait = true;
			}
		}
	}

	class TouchPoint
	{

		int x;
		int y;
		int color;
		int radius;

		public TouchPoint(int x, int y, int color, int radius)
		{
			this.x = x;
			this.y = y;
			this.radius = radius;
			
			this.color = color;
		}

	}

}

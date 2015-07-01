package com.yarin.android.Examples_05_05;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
	/* 声明Paint对象 */
	private Paint mPaint = null;
	
	private GameView2 mGameView2 = null;
	public GameView(Context context)
	{
		super(context);
		/* 构建对象 */
		mPaint = new Paint();
		
		mGameView2 = new GameView2(context);
		
		/* 开启线程  */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 设置画布为黑色背景 */
		canvas.drawColor(Color.BLACK);
		/* 取消锯齿 */
		mPaint.setAntiAlias(true);
		
		mPaint.setStyle(Paint.Style.STROKE);
		
		{
			/* 定义矩形对象 */
			Rect rect1 = new Rect();
			/* 设置矩形大小 */
			rect1.left = 5;
			rect1.top = 5;
			rect1.bottom = 25;
			rect1.right = 45;
			
			mPaint.setColor(Color.BLUE);
			/* 绘制矩形 */
			canvas.drawRect(rect1, mPaint);
			
			mPaint.setColor(Color.RED);
			/* 绘制矩形 */
			canvas.drawRect(50, 5, 90, 25, mPaint);
			
			mPaint.setColor(Color.YELLOW);
			/* 绘制圆形(圆心x,圆心y,半径r,p) */
			canvas.drawCircle(40, 70, 30, mPaint);
			
			/* 定义椭圆对象 */
			RectF rectf1 = new RectF();
			/* 设置椭圆大小 */
			rectf1.left = 80;
			rectf1.top = 30;
			rectf1.right = 120;
			rectf1.bottom = 70;
			
			mPaint.setColor(Color.LTGRAY);
			/* 绘制椭圆 */
			canvas.drawOval(rectf1, mPaint);
			
			/* 绘制多边形 */
			Path path1 = new Path();
			
			/*设置多边形的点*/
			path1.moveTo(150+5, 80-50);
			path1.lineTo(150+45, 80-50);
			path1.lineTo(150+30, 120-50);
			path1.lineTo(150+20, 120-50);
			/* 使这些点构成封闭的多边形 */
			path1.close();
			
			mPaint.setColor(Color.GRAY);
			/* 绘制这个多边形 */
			canvas.drawPath(path1, mPaint);
			
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(3);
			/* 绘制直线 */
			canvas.drawLine(5, 110, 315, 110, mPaint);
		}
		//
		//下面绘制实心几何体
		//
		mPaint.setStyle(Paint.Style.FILL);
		{
			/* 定义矩形对象 */
			Rect rect1 = new Rect();
			/* 设置矩形大小 */
			rect1.left = 5;
			rect1.top = 130+5;
			rect1.bottom = 130+25;
			rect1.right = 45;
			
			mPaint.setColor(Color.BLUE);
			/* 绘制矩形 */
			canvas.drawRect(rect1, mPaint);
			
			mPaint.setColor(Color.RED);
			/* 绘制矩形 */
			canvas.drawRect(50, 130+5, 90, 130+25, mPaint);
			
			mPaint.setColor(Color.YELLOW);
			/* 绘制圆形(圆心x,圆心y,半径r,p) */
			canvas.drawCircle(40, 130+70, 30, mPaint);
			
			/* 定义椭圆对象 */
			RectF rectf1 = new RectF();
			/* 设置椭圆大小 */
			rectf1.left = 80;
			rectf1.top = 130+30;
			rectf1.right = 120;
			rectf1.bottom = 130+70;
			
			mPaint.setColor(Color.LTGRAY);
			/* 绘制椭圆 */
			canvas.drawOval(rectf1, mPaint);
			
			/* 绘制多边形 */
			Path path1 = new Path();
			
			/*设置多边形的点*/
			path1.moveTo(150+5, 130+80-50);
			path1.lineTo(150+45, 130+80-50);
			path1.lineTo(150+30, 130+120-50);
			path1.lineTo(150+20, 130+120-50);
			/* 使这些点构成封闭的多边形 */
			path1.close();
			
			mPaint.setColor(Color.GRAY);
			/* 绘制这个多边形 */
			canvas.drawPath(path1, mPaint);
			
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(3);
			/* 绘制直线 */
			canvas.drawLine(5, 130+110, 315, 130+110, mPaint);
		}
		
		/* 通过ShapeDrawable来绘制几何图形 */
		mGameView2.DrawShape(canvas);
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
			//使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}
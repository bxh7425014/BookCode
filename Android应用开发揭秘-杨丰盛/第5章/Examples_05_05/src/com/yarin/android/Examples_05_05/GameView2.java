package com.yarin.android.Examples_05_05;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

//通过ShapeDrawable来绘制几何图形
public class GameView2 extends View
{
	/* 声明ShapeDrawable对象 */
	ShapeDrawable	mShapeDrawable	= null;


	public GameView2(Context context)
	{
		super(context);
	}
	
	public void DrawShape(Canvas canvas)
	{
		/* 实例化ShapeDrawable对象并说明是绘制一个矩形 */
		mShapeDrawable = new ShapeDrawable(new RectShape());
		
		//得到画笔paint对象并设置其颜色
		mShapeDrawable.getPaint().setColor(Color.RED);
	
		Rect bounds = new Rect(5, 250, 55, 280);
		
		/* 设置图像显示的区域 */
		mShapeDrawable.setBounds(bounds);
		
		/* 绘制图像 */
		mShapeDrawable.draw(canvas);
		/*=================================*/
		/* 实例化ShapeDrawable对象并说明是绘制一个椭圆 */
		mShapeDrawable = new ShapeDrawable(new OvalShape());
		
		//得到画笔paint对象并设置其颜色
		mShapeDrawable.getPaint().setColor(Color.GREEN);
		
		/* 设置图像显示的区域 */
		mShapeDrawable.setBounds(70, 250, 150, 280);
		
		/* 绘制图像 */
		mShapeDrawable.draw(canvas);
		
		Path path1 = new Path();
		/*设置多边形的点*/
		path1.moveTo(150+5, 80+80-50);
		path1.lineTo(150+45, 80+80-50);
		path1.lineTo(150+30, 80+120-50);
		path1.lineTo(150+20, 80+120-50);
		/* 使这些点构成封闭的多边形 */
		path1.close();
		
		//PathShape后面两个参数分别是宽度和高度
		mShapeDrawable = new ShapeDrawable(new PathShape(path1,150,150));
		
		//得到画笔paint对象并设置其颜色
		mShapeDrawable.getPaint().setColor(Color.BLUE);
		
		/* 设置图像显示的区域 */
		mShapeDrawable.setBounds(100, 170, 200, 280);
		
		/* 绘制图像 */
		mShapeDrawable.draw(canvas);
	}
}


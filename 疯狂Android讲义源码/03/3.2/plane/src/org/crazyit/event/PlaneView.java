/**
 * 
 */
package org.crazyit.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class PlaneView extends View
{
	public float currentX;
	public float currentY;
	Bitmap plane;
	/**
	 * @param context
	 */
	public PlaneView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		//定义飞机图片
		plane = BitmapFactory.decodeResource(context.getResources()
			, R.drawable.plane);
		setFocusable(true);
	}
	@Override
	public void onDraw (Canvas canvas)
	{
		super.onDraw(canvas);
		//创建画笔
		Paint p = new Paint();
		//绘制飞机
		canvas.drawBitmap(plane , currentX , currentY , p);	
	}
}


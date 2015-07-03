/**
 * 
 */
package org.crazyit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
public class DrawView extends View
{
	public float currentX = 40;
	public float currentY = 50;
	/**
	 * @param context
	 */
	public DrawView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onDraw (Canvas canvas)
	{
		super.onDraw(canvas);
		//创建画笔
		Paint p = new Paint();
		//设置画笔的颜色
		p.setColor(Color.RED);
		//绘制一个小圆（作为小球）
		canvas.drawCircle(currentX , currentY , 15 , p);		
	}
}

/**
 * 
 */
package org.crazyit.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
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
public class MyView extends View
{
	// 声明画笔
	public Paint paint;
	public MyView(Context context , AttributeSet set)
	{
		super(context , set);
		paint = new Paint();
		paint.setColor(Color.RED);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 使用指定Paint对象画矩形
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}
}

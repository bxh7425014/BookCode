/**
 * 
 */
package org.crazyit.sensor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
	// 定义水平仪仪表盘图片
	Bitmap back;
	// 定义水平仪中的气泡图标
	Bitmap bubble;
	// 定义水平仪中气泡 的X、Y座标
	int bubbleX, bubbleY;

	public MyView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// 加载水平仪图片和气泡图片
		back = BitmapFactory.decodeResource(getResources()
			, R.drawable.back);
		bubble = BitmapFactory
			.decodeResource(getResources(), R.drawable.bubble);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 绘制水平仪表盘图片
		canvas.drawBitmap(back, 0, 0, null);
		// 根据气泡座标绘制气泡
		canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
	}
}

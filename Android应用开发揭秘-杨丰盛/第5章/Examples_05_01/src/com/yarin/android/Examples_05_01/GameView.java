package com.yarin.android.Examples_05_01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View
{
	int  miCount = 0;
	int  y = 0;
	public GameView(Context context)
	{
		super(context);
	}
	//
	//具体绘图内容我们紧接着就会讲
	public void onDraw(Canvas canvas)
	{
		if (miCount < 100)
		{
			miCount++;
		}
		else 
		{
			miCount = 0;
		}
		//绘图
		Paint mPaint = new Paint();   
        switch (miCount%4)
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
        //绘制矩形--后面我们将详细讲解
        canvas.drawRect((320-80)/2, y, (320-80)/2+80, y+40, mPaint); 
	}
}


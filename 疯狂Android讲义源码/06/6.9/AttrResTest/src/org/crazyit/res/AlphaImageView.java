/**
 * 
 */
package org.crazyit.res;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

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
public class AlphaImageView extends ImageView
{
	// 图像透明度每次改变的大小
	private int alphaDelta = 0;
	//记录图片当前的透明度。
	private int curAlpha = 0;
	//每隔多少毫秒透明度改变一次
	private final int SPEED = 300;
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				//每次增加curAlpha的值
				curAlpha += alphaDelta;
				if (curAlpha > 255)
					curAlpha = 255;
				//修改该ImageView的透明度
				AlphaImageView.this.setAlpha(curAlpha);
			}
		}
	};
	/**
	 * @param context
	 * @param attrs
	 */
	public AlphaImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
			R.styleable.AlphaImageView);
		//获取duration参数
		int duration = typedArray.getInt(
			R.styleable.AlphaImageView_duration , 0);
		//计算图像透明度每次改变的大小
		alphaDelta = 255 * SPEED / duration;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		this.setAlpha(curAlpha);
		super.onDraw(canvas);		
		final Timer timer = new Timer();
		//按固定间隔发送消息，通知系统改变图片的透明度
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{			
				Message msg = new Message();
				msg.what = 0x123;
				if (curAlpha >= 255)
				{
					timer.cancel();
				}
				else
				{
					handler.sendMessage(msg);
				}
			}
		}, 0, SPEED);	
	}
}

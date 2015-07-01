package com.yarin.android.Examples_05_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;

public class GameView extends View
{
	/* 定义AnimationDrawable动画 */
	private AnimationDrawable	frameAnimation	= null;
	Context						mContext		= null;
	
	/* 定义一个Drawable对象 */
	Drawable				mBitAnimation				= null;
	public GameView(Context context)
	{
		super(context);
		
		mContext = context;
		
		/* 实例化AnimationDrawable对象 */
		frameAnimation = new AnimationDrawable();
		
		/* 装载资源 */
		//这里用一个循环了装载所有名字类似的资源
		//如“a1.......15.png”的图片
		//这个方法用处非常大
		for (int i = 1; i <= 15; i++)
		{
			int id = getResources().getIdentifier("a" + i, "drawable", mContext.getPackageName());
			mBitAnimation = getResources().getDrawable(id);
			/* 为动画添加一帧 */
			//参数mBitAnimation是该帧的图片
			//参数500是该帧显示的时间,按毫秒计算
			frameAnimation.addFrame(mBitAnimation, 500);
		}
		
		/* 设置播放模式是否循环false表示循环而true表示不循环 */
		frameAnimation.setOneShot( false );  
		
		/* 设置本类将要显示这个动画 */
		this.setBackgroundDrawable(frameAnimation);
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
		case KeyEvent.KEYCODE_DPAD_UP:		
			/* 开始播放动画 */
			frameAnimation.start();
			break;
		}
		return true;
	}
}


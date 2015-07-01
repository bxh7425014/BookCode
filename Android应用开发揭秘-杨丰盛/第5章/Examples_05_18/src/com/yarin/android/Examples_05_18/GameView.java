package com.yarin.android.Examples_05_18;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class GameView extends View
{
	/* 定义AnimationDrawable动画对象 */
	private AnimationDrawable	frameAnimation	= null;
	Context						mContext		= null;
	public GameView(Context context)
	{
		super(context);
		
		mContext = context;
		
		/* 定义一个ImageView用来显示动画 */
		ImageView img = new ImageView(mContext);
		
		/* 装载动画布局文件 */
		img.setBackgroundResource(R.anim.frameanimation);		
		
		/* 构建动画 */
		frameAnimation = (AnimationDrawable) img.getBackground();
		
		/* 设置是否循环 */
		frameAnimation.setOneShot( false );  
		
		/* 设置该类显示的动画 */
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


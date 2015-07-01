package com.yarin.android.Examples_05_15;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class GameView extends View
{
	/* 定义Alpha动画 */
	private Animation	mAnimationAlpha		= null;
	
	/* 定义Scale动画 */
	private Animation	mAnimationScale		= null;
	
	/* 定义Translate动画 */
	private Animation	mAnimationTranslate	= null;
	
	/* 定义Rotate动画 */
	private Animation	mAnimationRotate	= null;
	
	/* 定义Bitmap对象 */
	Bitmap				mBitQQ				= null;
	
	public GameView(Context context)
	{
		super(context);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 绘制图片 */
		canvas.drawBitmap(mBitQQ, 0, 0, null);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			/* 创建Alpha动画 */
			mAnimationAlpha = new AlphaAnimation(0.1f, 1.0f);
			/* 设置动画的时间 */
			mAnimationAlpha.setDuration(3000);
			/* 开始播放动画 */
			this.startAnimation(mAnimationAlpha);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			/* 创建Scale动画 */
			mAnimationScale =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
												Animation.RELATIVE_TO_SELF, 0.5f, 
												Animation.RELATIVE_TO_SELF, 0.5f);
			/* 设置动画的时间 */
			mAnimationScale.setDuration(500);
			/* 开始播放动画 */
			this.startAnimation(mAnimationScale);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			/* 创建Translate动画 */
			mAnimationTranslate = new TranslateAnimation(10, 100,10, 100);
			/* 设置动画的时间 */
			mAnimationTranslate.setDuration(1000);
			/* 开始播放动画 */
			this.startAnimation(mAnimationTranslate);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			/* 创建Rotate动画 */
			mAnimationRotate=new RotateAnimation(0.0f, +360.0f,
												 Animation.RELATIVE_TO_SELF,0.5f,
												 Animation.RELATIVE_TO_SELF, 0.5f);
			/* 设置动画的时间 */
			mAnimationRotate.setDuration(1000);
			/* 开始播放动画 */
			this.startAnimation(mAnimationRotate);
			break;
		}
		return true;
	}
}


package com.yarin.android.Examples_05_16;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
	
	Context mContext = null;
	public GameView(Context context)
	{
		super(context);
		
		mContext = context;
		
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
			/* 装载动画布局 */
			mAnimationAlpha = AnimationUtils.loadAnimation(mContext,R.anim.alpha_animation);
			/* 开始播放动画 */
			this.startAnimation(mAnimationAlpha);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			/* 装载动画布局 */
			mAnimationScale = AnimationUtils.loadAnimation(mContext,R.anim.scale_animation);
			/* 开始播放动画 */
			this.startAnimation(mAnimationScale);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			/* 装载动画布局 */
			mAnimationTranslate = AnimationUtils.loadAnimation(mContext,R.anim.translate_animation);
			/* 开始播放动画 */
			this.startAnimation(mAnimationTranslate);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			/* 装载动画布局 */
			mAnimationRotate = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animation);
			/* 开始播放动画 */
			this.startAnimation(mAnimationRotate);
			break;
		}
		return true;
	}
}


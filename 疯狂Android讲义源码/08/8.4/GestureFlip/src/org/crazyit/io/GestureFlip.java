package org.crazyit.io;

import org.crazyit.io.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class GestureFlip extends Activity
	implements OnGestureListener
{
	// ViewFlipper实例
	ViewFlipper flipper;
	// 定义手势检测器实例
	GestureDetector detector;
	//定义一个动画数组，用于为ViewFlipper指定切换动画效果
	Animation[] animations = new Animation[4];
	//定义手势动作两点之间的最小距离
	final int FLIP_DISTANCE = 50;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建手势检测器
		detector = new GestureDetector(this);
		// 获得ViewFlipper实例
		flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		// 为ViewFlipper添加5个ImageView组件
		flipper.addView(addImageView(R.drawable.java));
		flipper.addView(addImageView(R.drawable.ee));
		flipper.addView(addImageView(R.drawable.ajax));
		flipper.addView(addImageView(R.drawable.xml));
		flipper.addView(addImageView(R.drawable.classic));
		//初始化Animation数组
		animations[0] = AnimationUtils.loadAnimation(this
			, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this
			, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this
			, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this
			, R.anim.right_out);
	}

	// 定义添加ImageView的工具方法
	private View addImageView(int resId)
	{
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		return imageView;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2,
		float velocityX, float velocityY)
	{
		/*
		 * 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE
		 * 也就是手势从右向左滑。
		 */
		if (event1.getX() - event2.getX() > FLIP_DISTANCE)
		{
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[0]);
			flipper.setOutAnimation(animations[1]);
			flipper.showPrevious();
			return true;
		}
		/*
		 * 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE 
		 * 也就是手势从右向左滑。
		 */
		else if (event2.getX() - event1.getX() > FLIP_DISTANCE)
		{
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[2]);
			flipper.setOutAnimation(animations[3]);
			flipper.showNext();
			return true;
		}
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}	
	
	@Override
	public boolean onDown(MotionEvent arg0)
	{
		return false;
	}
	@Override
	public void onLongPress(MotionEvent event)
	{
	}
	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2,
		float arg2, float arg3)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent event)
	{
	}
	@Override
	public boolean onSingleTapUp(MotionEvent event)
	{
		return false;
	}	
}
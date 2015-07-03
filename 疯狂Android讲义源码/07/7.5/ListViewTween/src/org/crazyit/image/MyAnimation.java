package org.crazyit.image;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class MyAnimation extends Animation
{
	private int centerX;
	private int centerY;
	//定义动画的持续事件
	private int duration;
	private Camera camera = new Camera();
	public MyAnimation(int centerX, int centerY , int duration)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.duration = duration;
	}
	@Override
	public void initialize(int width, int height, int parentWidth,
		int parentHeight)
	{
		super.initialize(width, height, parentWidth, parentHeight);
		//设置动画的持续时间
		setDuration(duration);
		//设置动画结束后效果保留
		setFillAfter(true);
		setInterpolator(new LinearInterpolator());
	}
	/*
	 * 该方法的interpolatedTime代表了抽象的动画持续时间，不管动画实际持续时间多长，
	 * interpolatedTime参数总是从0（动画开始时）～1（动画结束时）
	 * Transformation参数代表了对目标组件所做的变.
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		camera.save();
		//根据interpolatedTime时间来控制X、Y、Z上的偏移
		camera.translate(100.0f - 100.0f * interpolatedTime ,
			150.0f * interpolatedTime - 150 ,
			80.0f - 80.0f * interpolatedTime);
		// 设置根据interpolatedTime时间在Y柚上旋转不同角度。
		camera.rotateY(360 * (interpolatedTime));
		// 设置根据interpolatedTime时间在X柚上旋转不同角度
		camera.rotateX((360 * interpolatedTime));
		//获取Transformation参数的Matrix对象
		Matrix matrix = t.getMatrix();
		camera.getMatrix(matrix);
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
		camera.restore();
	}
}
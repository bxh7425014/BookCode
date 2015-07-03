package com.android.supermario;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
//播放动画视图类
public class RunGif extends View {
	public Movie mMovie;
	public long mMovieStart;
	//动画播放的标志
	public static boolean flag = false;
	//构造函数用于初始化动画
	public RunGif(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mMovie = Movie.decodeStream(getResources().openRawResource(
				R.raw.mediaview_gif1));
	}
	//是否播放动画的标志
	public boolean setFlag(boolean b) {
		flag = b;
		return flag;
	}
	public RunGif(Context context, AttributeSet as) {
		super(context, as);
		// TODO Auto-generated constructor stub
		//带属性的构造函数
		mMovie = Movie.decodeStream(getResources().openRawResource(
				R.raw.mediaview_gif1));
	}
	//绘制动画
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		long now = android.os.SystemClock.uptimeMillis();
		if (flag == true) {
			if (mMovieStart == 0) {
				// 播放第一帧
				mMovieStart = now;
			}
			if (mMovie != null) {
				//取出动画的时长
				int dur = mMovie.duration();
				if (dur == 0) {
					dur = 15000;
				}
				//算出需要显示第几帧
				int relTime = (int) ((now - mMovieStart) % dur);
				//设置要显示的帧
				mMovie.setTime(relTime);
				//显示
				mMovie.draw(canvas, 90, 30);
				// 作用是刷新当前View
				invalidate();
			}
		}
	}
}
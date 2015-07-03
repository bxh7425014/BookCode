package org.crazyit.io;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

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
public class GestureTest extends Activity
	implements OnGestureListener
{
	// 定义手势检测器实例
	GestureDetector detector;	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建手势检测器
		detector = new GestureDetector(this);		
	}
	//将该Activity上的触碰事件交给GestureDetector处理
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		return detector.onTouchEvent(me);
	}	
	@Override
	public boolean onDown(MotionEvent arg0)
	{
		Toast.makeText(this,"onDown" , 8000)
			.show();
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY)
	{
		Toast.makeText(this , "onFling" , 8000)
			.show();
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e)
	{
		Toast.makeText(this ,"onLongPress" , 8000)
			.show();		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
		float distanceY)
	{
		Toast.makeText(this ,"onScroll" , 8000)
			.show();	
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e)
	{
		Toast.makeText(this ,"onShowPress" , 8000)
			.show();		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		Toast.makeText(this ,"onSingleTapUp" , 8000)
			.show();
		return false;
	}
}
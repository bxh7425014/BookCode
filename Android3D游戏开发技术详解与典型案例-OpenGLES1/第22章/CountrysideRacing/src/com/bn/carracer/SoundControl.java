package com.bn.carracer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.carracer.Constant.*;
public class SoundControl extends SurfaceView implements SurfaceHolder.Callback{
	Activity_GL_Racing activity;//声明引用
	Bitmap sound;//声明引用
	float screenWidth=480;
	float x_Offset=Activity_GL_Racing.screenWidth/2-screenWidth/2;
	
	public SoundControl(Activity_GL_Racing activity) {
		super(activity);
		this.activity=activity;
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);//设置生命周期接口
		sound=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound);//加载图片
	}

	@Override 
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(sound, Activity_GL_Racing.screenWidth/2-screenWidth/2,0, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();//获取X坐标
			int y = (int) event.getY();
			if(x>10+x_Offset&&x<97+x_Offset&&y>280&&y<310)
			{
				Activity_GL_Racing.soundFlag=true;//开启声音
				activity.toAnotherView(ENTER_MENU);
			}
			else if(x>380+x_Offset&&x<468+x_Offset&&y>280&&y<310)
			{
				Activity_GL_Racing.soundFlag=false;//关闭声音
				activity.toAnotherView(ENTER_MENU);
			}
				break;
			
		}
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)    //触控事件
	{
		if(keyCode==4)     //返回系统  
		{			
			return false;
		}
		return true;
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas = holder.lockCanvas();//获取画布
		try{
			synchronized(holder){
				onDraw(canvas);//绘制
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

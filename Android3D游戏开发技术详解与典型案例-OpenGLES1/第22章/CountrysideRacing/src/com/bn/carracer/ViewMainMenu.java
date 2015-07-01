package com.bn.carracer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.carracer.Constant.*;
public class ViewMainMenu extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;//声明引用
	Bitmap mainMenu;//声明引用
	Paint paint;//创建画笔
	float screenWidth=480;
	float x_Offset=Activity_GL_Racing.screenWidth/2-screenWidth/2;
	public ViewMainMenu(Activity_GL_Racing activity) {
		super(activity);
		this.activity=activity;
		// TODO Auto-generated constructor stub
		this.getHolder().addCallback(this);   	//设置生命周期接口
		paint=new Paint();						//创建画笔
		paint.setAntiAlias(true);				//打开抗锯齿
		mainMenu=BitmapFactory.decodeResource(this.getResources(), R.drawable.mainmenu);//加载菜单图片
	}
	
	//触摸事件回调方法   
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();//获取X坐标
		float y=e.getY();//获取Y坐标
		
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				
				if(x>5+x_Offset&&x<92+x_Offset&&y>285&&y<314)//点击设置按钮
				{
					activity.toAnotherView(ENTER_SET_VIEW);//进入设置界面 
				}
				if(x>100+x_Offset&&x<187+x_Offset&&y>285&&y<314)//点击帮助按钮 
				{
					activity.toAnotherView(ENTER_HELP_VIEW);//进入帮助界面
				}
				if(x>196+x_Offset&&x<283+x_Offset&&y>285&&y<314)//点击开始游戏按钮
				{
					activity.toAnotherView(CHOOSE);//进入游戏选择界面
				}
				if(x>292+x_Offset&&x<380+x_Offset&&y>285&&y<314)//点击关于按钮
				{
					activity.toAnotherView(ENTER_ABOUT_VIEW);//进入关于界面
				}
				if(x>387+x_Offset&&x<474+x_Offset&&y>285&&y<314)//点击退出按钮
				{
					System.exit(0);//退出
				}
			break;
		}
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(mainMenu,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
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

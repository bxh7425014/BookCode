package com.bn.carracer;

import static com.bn.carracer.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ViewAbout extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;//声明引用
	Bitmap about;
	static int viewFlag=0;//界面的标志位，0表示第一幅关于界面，1表示第二幅关于界面图。
	float screenWidth=480;//图片宽度
	float x_Offset=Activity_GL_Racing.screenWidth/2-screenWidth/2;
//	Bitmap aboutTwo;
	static ThreadAboutView avt;//线程引用
	public ViewAbout(Activity_GL_Racing activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		about=BitmapFactory.decodeResource(this.getResources(), R.drawable.about);
//		aboutTwo=BitmapFactory.decodeResource(this.getResources(), R.drawable.abouttwo);
		avt=new ThreadAboutView(this);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				int x = (int) event.getX();//获取X坐标
				int y = (int) event.getY();//获取Y坐标
				if(viewFlag==0)
				{
//					if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
//					{
//						viewFlag=1;//改变标志位进入第二幅图
//					}
					if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
					{
						activity.toAnotherView(ENTER_MENU);//返回菜单界面
						avt.flag=false;//关闭线程
					}
				}
//				if(viewFlag==1)
//				{
//					if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
//					{
//						viewFlag=0;//返回菜单界面
//					}
//				}
			break;
		}
		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(viewFlag==0)
		{
			canvas.drawBitmap(about, Activity_GL_Racing.screenWidth/2-screenWidth/2,0, null);
		}
//		if(viewFlag==1)
//		{
//			canvas.drawBitmap(aboutTwo, Activity_GL_Racing.screenWidth/2-screenWidth/2,0,null);
//		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		avt.start();//开启线程
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

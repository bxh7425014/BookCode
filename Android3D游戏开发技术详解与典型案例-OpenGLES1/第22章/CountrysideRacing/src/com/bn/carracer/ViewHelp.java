package com.bn.carracer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.carracer.Constant.*;
public class ViewHelp extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;//声明引用
	Paint paint;//画笔
	Bitmap helpOne;//第一个帮助界面
	Bitmap helpTwo;//第二个帮助界面
	Bitmap helpThree;//第三个帮助界面
	Bitmap helpFour;//第四个帮助界面
	Bitmap helpFive;//第五个帮助界面
	Bitmap helpSix;//第六个帮助界面
	Bitmap helpSeven;//第七个帮助界面
	
	
	static int viewFlag=0;//第几副帮助界面的标志位，0 表示第一幅帮助界面，1表示第二幅帮助界面，2表示第三幅帮助界面
	static ThreadHelpView hvt;
	float screenWidth=480;//图片宽度
	float x_Offset=Activity_GL_Racing.screenWidth/2-screenWidth/2;
	public ViewHelp(Activity_GL_Racing activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		paint=new Paint(); 
		getHolder().addCallback(this);
		helpOne=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpone);//初始化第一张帮助界面
		helpTwo=BitmapFactory.decodeResource(this.getResources(), R.drawable.helptwo);//初始化第二张帮助界面
		helpThree=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpthree);//初始化第三张帮助界面
		helpFour=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpfour);//初始化第四张帮助界面
		helpFive=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpfive);//初始化第五张帮助界面
		helpSix=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpsix);//初始化第六张帮助界面
		helpSeven=BitmapFactory.decodeResource(this.getResources(), R.drawable.helpseven);//初始化第七张帮助界面
		
		hvt=new ThreadHelpView(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();//获取X坐标
			int y = (int) event.getY();//获取Y坐标
			if(viewFlag==0)//第一幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=1;//改变标志位进入第二幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					activity.toAnotherView(ENTER_MENU);//返回主菜单
					hvt.flag=false;//关闭线程
				}
				break;
			}
			if(viewFlag==1)//第二幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=2;//改变标志位进入第三幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					viewFlag=0;//改变标志位进入第一幅图
				}
				break;
			}
			if(viewFlag==2)//第三幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=3;//改变标志位进入第四幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					viewFlag=1;//改变标志位进入第二幅图
				}
				break;
			}
			if(viewFlag==3)//第四幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=4;//改变标志位进入第四幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					viewFlag=2;//改变标志位进入第二幅图
				}
				break;
			}
			if(viewFlag==4)//第五幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=5;//改变标志位进入第四幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					viewFlag=3;//改变标志位进入第二幅图
				}
				break;
			}
			if(viewFlag==5)//第六幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击下一页
				{
					viewFlag=6;//改变标志位进入第四幅图
				}
				if(x>382+x_Offset&&x<470+x_Offset&&y>278&&y<305)//点击返回
				{
					viewFlag=4;//改变标志位进入第二幅图
				}
				break;
			}
			if(viewFlag==6)//第七幅图
			{
				if(x>10+x_Offset&&x<97+x_Offset&&y>278&&y<304)//点击返回
				{
					viewFlag=5;//改变标志位进入第三幅图
				}
				break;
			}			
		}		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(viewFlag==0)//绘制第一幅图
		{
			canvas.drawBitmap(helpOne,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
		if(viewFlag==1)//绘制第二幅图
		{
			canvas.drawBitmap(helpTwo,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
		if(viewFlag==2)//绘制第三幅图
		{
			canvas.drawBitmap(helpThree,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
		if(viewFlag==3)//绘制第四幅图
		{
			canvas.drawBitmap(helpFour,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
		if(viewFlag==4)//绘制第五幅图
		{
			canvas.drawBitmap(helpFive,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}	
		if(viewFlag==5)//绘制第六幅图
		{
			canvas.drawBitmap(helpSix,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
		if(viewFlag==6)//绘制第七幅图
		{
			canvas.drawBitmap(helpSeven,  Activity_GL_Racing.screenWidth/2-screenWidth/2,0, paint);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		hvt.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

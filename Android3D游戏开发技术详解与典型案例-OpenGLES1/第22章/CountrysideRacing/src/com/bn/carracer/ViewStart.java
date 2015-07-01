package com.bn.carracer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.carracer.Constant.*;
public class ViewStart extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;
	Paint paint;
	int currentAloha=0;  //当前的透明度
	int screenWidth=480;
	int screenHeight=320;
	int sleepSpan=50;
	
	Bitmap[] logos=new Bitmap[2];     //logo图片数组
	Bitmap currentLogo;               //当前logo图片引用
	Bitmap sound;
	int currentX; 
	int currentY;
	public ViewStart(Activity_GL_Racing activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者		
		paint=new Paint();        //创建画笔
		paint.setAntiAlias(true);    //打开抗锯齿
				
		logos[0]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.baina);//加载百纳图片
		logos[1]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.bnkjs);//加载logo图片
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		try
		{
			//绘制黑色填充矩形清背景
			paint.setColor(Color.BLACK);
			paint.setAlpha(255);
			canvas.drawRect(0, 0,screenWidth, screenHeight ,paint);
			 
			//进行平面贴图 
			if(currentLogo==null)return;
			paint.setAlpha(currentAloha);
			
//			float k=Activity_GL_Racing.screenWidth/2-screenWidth/2;						
			canvas.drawBitmap(currentLogo, Activity_GL_Racing.screen_xoffset,0,paint);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {//创建时调用
		// TODO Auto-generated method stub
		new Thread()
		{
			public void run()
			{
				for(Bitmap bm:logos)
				{
					currentLogo=bm;
					//图片的位置
					currentX=screenWidth/2-bm.getWidth()/2;     //X坐标位置
					currentY=screenHeight/2-bm.getHeight()/2;   //Y坐标位置
					
					for(int i=255;i>-10;i=i-10)  //动态更改图片的透明度值并不断重绘	
					{			
						currentAloha=i;
						if(currentAloha<0)
						{
							currentAloha=0;
						}
						SurfaceHolder myholder=ViewStart.this.getHolder();
						Canvas canvas = myholder.lockCanvas();//获取画布
						try
						{
							synchronized(myholder)
							{
								onDraw(canvas);    //绘制
							}							
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(canvas !=null)
							{
								myholder.unlockCanvasAndPost(canvas);								
							}							
						}
						
						try
						{
							if(i==255)
							{
								Thread.sleep(1000);							
							}
							Thread.sleep(sleepSpan);						
						}catch(Exception e)
						{
							e.printStackTrace();
						}
											
					}//第一个for循环				
				}//第二个for循环
				activity.toAnotherView(ENTER_SOUND);//进入声音控制界面
			}
			
		}.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}

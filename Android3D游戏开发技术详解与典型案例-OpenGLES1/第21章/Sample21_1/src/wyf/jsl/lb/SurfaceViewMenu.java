package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewMenu extends SurfaceView implements SurfaceHolder.Callback {

	Activity_GL_Demo activity;
	Paint paint;//画笔
	Bitmap[] menu=new Bitmap[4];//菜单项图片数组
	Bitmap menubg;//菜单页面背景
	
	int currentIndex=1;//当前选中的菜单数组索引
	float changePercent=0;//动画进行的百分比
	int anmiState=0;//0:没有动画；1：向右走；2：向左走
	//当前选中菜单的宽，高及其左上角坐标
	float currentSelectWidth;
	float currentSelectHeight;
	float currentSelectX;
	float currentSelectY;
	//紧邻当前选中菜单左侧菜单的宽，高及其左上角坐标
	float leftWidth;
	float leftHeight;
	float leftX;
	float leftY;
	//紧邻当前选中菜单右侧菜单的宽，高及其左上角坐标
	float rightWidth;
	float rightHeight;
	float rightX;
	float rightY;
	//上次触控的X,Y坐标
	float previousX;
	float previousY;
	
	public SurfaceViewMenu(Activity_GL_Demo activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		
		initBitmap();
		initMenu();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		//若动画播放中则触控无效 
		if(anmiState!=0)
		{
			return true;
		}
		//获得当前触控点的X,Y坐标
		float x=e.getX();
		float y=e.getY();
		//根据触控的不同动作执行不同的业务逻辑
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN://若动作为按下触控笔则记录XY坐标
				previousX=x;
				previousY=y;
			break;
			case MotionEvent.ACTION_UP://若动作为抬起则根据X位移的不同执行左滑，右滑或选中菜单项的业务逻辑
				float dx=x-previousX;//计算X位移
				if(dx<-MENU_SLIDESPAN)//若X位移小于负阈值则向左移
				{
					if(currentIndex<menu.length-1)// 若当前菜单项不是最后一个菜单项则左移
					{
						int afterCurrentIndex=currentIndex+1;//新的当前菜单项索引
						anmiState=2;//动画状态设为2，表向左移
						new ThreadMenu(this,afterCurrentIndex).start();
					}
				}
				else if(dx>MENU_SLIDESPAN)
				{
					if(currentIndex>0)// 若当前菜单项不是第一个菜单项则右移
					{
						int afterCurrentIndex=currentIndex-1;//新的当前菜单项索引
						anmiState=1;//动画状态设为1，表向右移
						new ThreadMenu(this,afterCurrentIndex).start();
					}
				}
				else
				{
					if
					(
							previousX>=SELECT_X&&previousX<=(SELECT_X+BIGWIDTH)&&
							previousY>=SELECT_Y&&previousY<=SELECT_Y+menu[currentIndex].getHeight()&&//
							x>=SELECT_X&&x<=SELECT_X+BIGWIDTH&&
							y>=SELECT_Y&&y<=SELECT_Y+menu[currentIndex].getHeight()	
					)
					{
						//根据点击的图片不同，发送不同的消息
						if(currentIndex==0)
						{
							activity.hd.sendEmptyMessage(GAME_LOAD);
							inGame=true;
						}
						else if(currentIndex==1)
						{
							activity.hd.sendEmptyMessage(GAME_HELP);
						}
						else if(currentIndex==2)
						{
							activity.hd.sendEmptyMessage(GAME_ABOUT);
						}
						else if(currentIndex==3)
						{
							//activity.hd.sendEmptyMessage(GAME_OVER);
							System.exit(0);
						}
						
					}
				}
				break;
		}
		return true;
	}
	
	
	@Override
	public void onDraw(Canvas canvas)
	{
		//super.onDraw(canvas);
		
		canvas.drawBitmap(menubg, 0, 0, paint);
		
		Bitmap selectbm=menu[currentIndex];
		selectbm=Bitmap.createScaledBitmap
		(
				selectbm,
				(int)currentSelectWidth,//这里一定要是int的
				(int)currentSelectHeight,
				false
		);
		canvas.drawBitmap(selectbm, currentSelectX, currentSelectY, paint);
		
		//若当前菜单项不是第一项，则绘制紧邻当前菜单左侧的菜单项
		if(currentIndex>0)
		{
			Bitmap leftbm=Bitmap.createScaledBitmap
			(
					menu[currentIndex-1],
					(int)leftWidth,
					(int)leftHeight,
					false
			);
			canvas.drawBitmap(leftbm, leftX, leftY, paint);
		}
		if(currentIndex<menu.length-1)
		{
			Bitmap rightbm=Bitmap.createScaledBitmap
			(
					menu[currentIndex+1],
					(int)rightWidth,
					(int)rightHeight,
					false
			);
			canvas.drawBitmap(rightbm, rightX, rightY, paint);
		}
		for(int i=currentIndex-2;i>=0;i--)
		{
			float tempx=leftX-(MENU_SPAN+SMALLWIDTH)*((currentIndex-1)-i);
			if(tempx<-SMALLWIDTH)
			{
				break;
			}
			float tempy=SELECT_Y+(BIGHEIGHT-SMALLHEIGHT);
			
			Bitmap tempbm=Bitmap.createScaledBitmap
			(
					menu[i],
					(int)SMALLWIDTH,
					(int)SMALLHEIGHT,
					false
			);
			canvas.drawBitmap(tempbm, tempx, tempy, paint);
		}
		for(int i=currentIndex+2;i<menu.length;i++)
		{
			float tempx=rightX+(SMALLWIDTH+MENU_SPAN)*(i-(currentIndex+1));
			if(tempx>SCREEN_WIDTH)
			{
				break;
			}
			float tempy=SELECT_Y+(BIGHEIGHT-SMALLHEIGHT);
			
			Bitmap tempbm=Bitmap.createScaledBitmap
			(
					menu[i],
					(int)SMALLWIDTH,
					(int)SMALLHEIGHT,
					false
			);
			canvas.drawBitmap(tempbm, tempx, tempy, paint);
		}
		
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	//重绘界面的方法
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas=holder.lockCanvas();
		try
		{
			synchronized(holder)
			{
				onDraw(canvas);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	//初始化菜单方法，即初始化当前及靠其左右的菜单项的位置大小参数
	public void initMenu()
	{
		//初始化当前菜单大小位置
		 currentSelectWidth=BIGWIDTH;
		 currentSelectHeight=BIGHEIGHT;
		 currentSelectX=SELECT_X;
		 currentSelectY=SELECT_Y;
		 //初始化紧邻当前菜单左侧菜单的大小位置
		 leftWidth=SMALLWIDTH;
		 leftHeight=SMALLHEIGHT;
		 //leftX=SELECT_X-MENU_SPAN-SMALLWIDTH;
	     //leftY=SELECT_Y+BIGHEIGHT-SMALLHEIGHT;
		 leftX=currentSelectX-MENU_SPAN-leftWidth;
		 leftY=currentSelectY+currentSelectHeight-leftHeight;
		 //初始化紧邻当前菜单右侧菜单的大小位置
		 rightWidth=SMALLWIDTH;
		 rightHeight=SMALLHEIGHT;
		 //rightX=SELECT_X+BIGWIDTH+MENU_SPAN;
		 //rightY=SELECT_Y+BIGHEIGHT-SMALLHEIGHT;
		 rightX=currentSelectX+currentSelectWidth+MENU_SPAN;
		 rightY=currentSelectY+currentSelectHeight-rightHeight;
	}
	//初始化位图方法
	public void initBitmap()
	{
		menubg=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		menu[0]=BitmapFactory.decodeResource(getResources(), R.drawable.menu0);
		menu[1]=BitmapFactory.decodeResource(getResources(), R.drawable.menu3);
		menu[2]=BitmapFactory.decodeResource(getResources(), R.drawable.menu2);
		menu[3]=BitmapFactory.decodeResource(getResources(), R.drawable.menu1);
	}

}

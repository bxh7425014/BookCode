package wyf.jsc.tdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.tdb.Constant.*;

  

public class MenuView extends SurfaceView
implements SurfaceHolder.Callback
{
	MyActivity activity;   
	Paint paint;     				//画笔
	Bitmap[] menu=new Bitmap[5];   	//菜单图片数组
	Bitmap bj;     					//背景图片
	Bitmap sound;
	
	int currentIndex=2;				//当前选中的菜单编号	
	float mPreviousX;  				//记录上次触控的X坐标
	float mPreviousY;  				//记录上次触控的Y坐标
	float changePercent=0;   		//动画的百分百
	int anmiState=0;   				//0-没有动画  1-向右走  2-向左走	
	
	int menuWidth;     				//当钱菜单的宽度
	int menuHeight;    		 		//当前菜单的高度
	float currentSelectX;    		//当前菜单项的X坐标位置
	float currentSelectY;    		//当前菜单项的Y坐标位置
	
	float leftWidth;    //左边临近菜单的宽度
	float leftHeight;	//高度
	float leftmenuX;	//X坐标
	float leftmenuY;	//Y坐标
	
	float rightWidth;	//右边临近菜单的宽度
	float rightHeight;  //高度
	float rightmenuX;   //X坐标
	float rightmenuY;   //Y坐标
	
	final int HELP_VIEW=0;			//帮助
	final int ABOUT_VIEW=1;			//关于
	final int START_VIEW=2;			//开始游戏
	final int SETUP_VIEW=3;			//设置
	final int EXIT_VIEW=4;			//退出
	

	public MenuView(MyActivity activity) {
		super(activity);  
		// TODO Auto-generated constructor stub
		this.activity=activity; 
		this.getHolder().addCallback(this);   	//设置生命周期接口
		paint=new Paint();						//创建画笔
		paint.setAntiAlias(true);				//打开抗锯齿		

		
		//初始化图片
		menu[0]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.menu1);
		menu[1]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.menu2);
		menu[2]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.menu3);
		menu[3]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.menu4);
		menu[4]=BitmapFactory.decodeResource(activity.getResources(), R.drawable.menu5);
		bj=BitmapFactory.decodeResource(activity.getResources(), R.drawable.bg);
		
		//初始化当前及紧靠其左右的菜单项的位置大小参数
		init();
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			System.exit(0);
			return true;
		}
		return true;	
	}
	//触摸事件回调方法   
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(anmiState!=0)
		{    
			return true;
		}  
		//获取当前触控点的XY坐标
		float x=e.getX();
		float y=e.getY();  
		
		//根据触控的不同动作执行不同的业务逻辑
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:    //若动作为按下触控笔则记录XY位置
				mPreviousX=x;//记录触控笔X位置
				mPreviousY=y;//记录触控笔Y位置
				break;
			case MotionEvent.ACTION_UP:     //若动作为抬起则根据X位移的不同执行左滑、右滑或选中菜单项的业务逻辑
				
				//计算X位移
				float dx=x-mPreviousX;
				if(dx<-slideSpan)
				{//若X位移小于阈值则向左滑动
					if(currentIndex<menu.length-1)
					{//若当前菜单项不是最后一个菜单则向左滑动
						//计算滑动完成后的当前菜单项的编号
						int afterCurrentIndex=currentIndex+1;
						//动画状态值设置为2 向左走
						anmiState=2;
						//启动线程播放动画并更新状态值
						new MenuAnmiThread(this,afterCurrentIndex).start();
						//(this,afterCurrentIndex)构造器2个参数！！！
					}					
				}
				else if(dx>slideSpan)
				{//若X位移大于阀值则向右滑动
					if(currentIndex>0)
					{//若当前菜单项不是第一个菜单项则向左滑动
	            		  //计算滑动完成后的当前菜单项编号
						int afterCurrentIndex=currentIndex-1;
						//动画状态值设置为2-向右走
						anmiState=1;
						//启动线程播放动画并更新状态值
						new MenuAnmiThread(this,afterCurrentIndex).start();
					}	            
				}
				else
				{//若X位移在阈值内则判断有否选中某菜单项
					if
					(
						mPreviousX>selectX&&mPreviousX<selectX+bigWidth&&
		                mPreviousY>selectY&&mPreviousY<selectY+menu[currentIndex].getHeight()&&
		                x>selectX&&x<selectX+bigWidth&&
		                y>selectY&&y<selectY+menu[currentIndex].getHeight()	
					)
						 //若按下与抬起的触控点都在当前菜单的范围内则执行按下某菜单项的业务逻辑
					{				
						
						switch(currentIndex)
						{
							case HELP_VIEW:
								activity.toAnotherView(ENTER_HELP);		//帮助
								break;
							case ABOUT_VIEW:
								activity.toAnotherView(ENTER_ABOUT);	//关于
								break;
							case START_VIEW:
								activity.toAnotherView(START_ONE); 
								break;
							case SETUP_VIEW:
								activity.toAnotherView(ENTER_SETUP);	//设置	
								break;
							case EXIT_VIEW:
								System.exit(0);			//退出程序
								break;
						}
					}
				}
			 break;
		}	
		return true;	
	}
	
	public void onDraw(Canvas canvas)
	{
		
		//绘制背景		
		canvas.drawBitmap(bj, 0, 0, paint);
		//获取当前菜单项图片
		Bitmap selectBM=menu[currentIndex];

		//根据参数计算出用于绘制当前菜单项的图片
		selectBM=Bitmap.createScaledBitmap
		(
				selectBM, 
				menuWidth, 
				menuHeight, 
				false	
		);
		
		
			
		//绘制当前的菜单项
		canvas.drawBitmap(selectBM, currentSelectX, currentSelectY, paint);

		//若当前菜单项不是第一项则绘制紧邻当前菜单项左侧的菜单项
		if(currentIndex>0)
		{	
			//缩放出绘制用图片
			Bitmap left=Bitmap.createScaledBitmap
			(
					menu[currentIndex-1], 
					(int)leftWidth, 
					(int)leftHeight, 
					false
			);		
			//绘制图片
			canvas.drawBitmap(left, leftmenuX, leftmenuY, paint);
		}			
		
		//若当前菜单项不是最后一项则绘制紧邻当前菜单项右侧的菜单项
		if(currentIndex<menu.length-1)
		{
			//缩放出绘制用图片
			Bitmap right=Bitmap.createScaledBitmap
			(
					menu[currentIndex+1], 
					(int)rightWidth, 
					(int)rightHeight, 
					false
			);	
			//绘制图片
			canvas.drawBitmap(right, rightmenuX, rightmenuY, paint);
		}
		
		//向左绘制其他未选中的菜单
		for(int i=currentIndex-2;i>=0;i--)
		{	
			//计算X值
			float tempx=leftmenuX-(span+smallWidth)*(currentIndex-1-i);
			if(tempx<-smallWidth)
			{//若绘制出来不再屏幕上则不用绘制了
				break;
			}
			//计算Y值
			int tempy=selectY+(bigHeight-smallHeight);	
			
			//缩放出绘制用图片
			Bitmap tempbm=Bitmap.createScaledBitmap
			(
					menu[i], 
					smallWidth, 
					smallHeight, 
					false
			);
			//绘制图片
			canvas.drawBitmap(tempbm, tempx, tempy, paint);			
		}
		
		//向右绘制其他未选中的菜单
		for(int i=currentIndex+2;i<menu.length;i++)
		{	
			//计算X值
            float tempx=rightmenuX+rightWidth+span+(span+smallWidth)*(i-(currentIndex+1)-1);			
			if(tempx>screenWidth)
			{//若绘制出来不再屏幕上则不用绘制了
				break;
			}			
			//计算Y值
			int tempy=selectY+(bigHeight-smallHeight);					
			
			//缩放出绘制用图片
			Bitmap tempbm=Bitmap.createScaledBitmap
			(
					menu[i], 
					smallWidth, 
					smallHeight, 
					false
			);	
			//绘制图片
			canvas.drawBitmap(tempbm, tempx, tempy, paint);		
		}
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		repaint();
	}
	
	//重绘界面的方法
	public void repaint() {
		// TODO Auto-generated method stub
		SurfaceHolder holder=this.getHolder();
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
		

	public void init() {
		// TODO Auto-generated method stub
		menuWidth=bigWidth;//当前选中菜单宽度		
		menuHeight=bigHeight;//当前选中菜单高度
		currentSelectX=selectX;//当前选中菜单X位置
		currentSelectY=selectY;//当前选中菜单Y位置	
		rightWidth=smallWidth;//紧邻右侧的宽度		
		leftWidth=smallWidth;//紧邻左侧的宽度		
		leftHeight=smallHeight;//紧邻左侧的高度	
		rightHeight=smallHeight;//紧邻右侧的高度
		leftmenuX=currentSelectX-(span+leftWidth);//紧邻左侧的X
		leftmenuY=currentSelectY+(menuHeight-leftHeight);//紧邻左侧的Y坐标	
		rightmenuX=currentSelectX+(span+menuWidth);//紧邻右侧的X	
		rightmenuY=currentSelectY+(menuHeight-rightHeight);//紧邻右侧的Y坐标
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

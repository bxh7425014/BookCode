package wyf.tzz.gdl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StartSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {

	GL_Demo myActivity;

	int screenWidth = 320;//屏幕宽度
	int screenHeight = 480;//屏幕高度
	int picWidth=128;//图片宽
	int picHeight=32;//图片高
	int blankWidth=0;//留白
	int blankHeight=220;//高度留白
	int unit_blankHeight=5;//图片之间高度留白
	
	final int KSMS_VIEW=0;//快速模式
	final int OPTION_VIEW=1;//设置
	final int ABOUT_VIEW=2;//关于
	final int HELP_VIEW=3;//帮助
	final int EXIT_VIEW=4;//退出
	
	Bitmap background;//背景
	Bitmap ksms;//快速模式
	Bitmap gameOption;//设置
	Bitmap gameExit;//推出
	Bitmap gameAbout;//关于
	Bitmap gameHelp;//帮助
	//未选中时按钮图片
	Bitmap ksmsShort;//快速模式
	Bitmap gameOptionShort;//设置
	Bitmap gameExitShort;//推出
	Bitmap gameAboutShort;//关于
	Bitmap gameHelpShort;//帮助
	//选中时按钮图片
	Bitmap ksmsLong;//快速模式
	Bitmap gameOptionLong;//设置
	Bitmap gameExitLong;//推出
	Bitmap gameAboutLong;//关于
	Bitmap gameHelpLong;//帮助
	
	int index=KSMS_VIEW;
	
	boolean soundActivity=true;//绘制声音提示的界面的标志
	boolean soundMarker=false;//是否打开声音
	
	public StartSurfaceView(GL_Demo myActivity) {
		super(myActivity);		//
		this.myActivity=myActivity;
		initBitmap();			//调用initBitmap方法
		
		this.getHolder().addCallback(this);//注册回调接口
		
		shiftState(index);	//调用shiftState方法
		
	}
	public void initBitmap()
	{ 
		background=BitmapFactory.decodeResource(getResources(), R.drawable.startbj);//初始化背景
		ksmsShort=BitmapFactory.decodeResource(getResources(), R.drawable.ksms);//快速模式
		gameOptionShort=BitmapFactory.decodeResource(getResources(), R.drawable.option);//设置
		gameExitShort=BitmapFactory.decodeResource(getResources(), R.drawable.exit);//退出
		gameAboutShort=BitmapFactory.decodeResource(getResources(), R.drawable.about);//关于
		gameHelpShort=BitmapFactory.decodeResource(getResources(), R.drawable.help);//帮助
		
		ksmsLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_ksms);//快速模式
		gameOptionLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_option);//设置
		gameExitLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_exit);//退出
		gameAboutLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_about);//关于
		gameHelpLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_help);//帮助
	}
	
	public void shiftState(int index)
	{
		ksms=ksmsShort;//设置快速模式的图片按钮为短图片
		gameOption=gameOptionShort;//设置设置的图片按钮为短图片
		gameAbout=gameAboutShort;//设置关于的图片按钮为短图片
		gameHelp=gameHelpShort;//设置帮助的图片按钮为短图片
		gameExit=gameExitShort;//设置退出的图片按钮为短图片
		
		//让选定按钮变长
		 switch(index)
		  {
			  case KSMS_VIEW:	//选中快速模式
				  ksms=ksmsLong;//设置快速模式图片为长图片
				  break;
			   case OPTION_VIEW://选中设置模式
				   gameOption=gameOptionLong;//设置设置的图片按钮为长图片
				   break;
			   case ABOUT_VIEW://选中关于模式
				   gameAbout=gameAboutLong;//设置关于的图片按钮为长图片
				   break;
			   case HELP_VIEW://选中帮助模式
				   gameHelp=gameHelpLong;////设置帮助的图片按钮为长图片
				   break;
			   case EXIT_VIEW://选中退出模式
				   gameExit=gameExitLong;//设置退出的图片按钮为长图片
				   break;
		  }
		 //重绘
		 Canvas canvas=null;			//	画布
		 SurfaceHolder holder=this.getHolder();//定义并实例化SurfaceHolder对象
			
			try
			{
				canvas=holder.lockCanvas();//锁定画布
				
				synchronized(holder)//给holder加锁
				{
					onDraw(canvas);//重绘画面
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
					holder.unlockCanvasAndPost(canvas);//绘制后解锁，绘制后必须解锁才能显示
				}
			}
	}
	
	@Override
	public void onDraw(Canvas canvas)//重绘
	{
		canvas.drawBitmap(background, 0, 0, null);//绘制背景图片
		canvas.drawBitmap(ksms, blankWidth, blankHeight,null);//绘制快速模式按钮图片
		canvas.drawBitmap(gameOption,blankWidth,blankHeight+1*(picHeight+unit_blankHeight),null);//设置
		canvas.drawBitmap(gameAbout, blankWidth, blankHeight+2*(picHeight+unit_blankHeight),null);//关于
		canvas.drawBitmap(gameHelp, blankWidth, blankHeight+3*(picHeight+unit_blankHeight),null);//帮助
		canvas.drawBitmap(gameExit, blankWidth, blankHeight+4*(picHeight+unit_blankHeight),null);//退出
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();//获得屏幕x坐标
		int y = (int) event.getY();//获得屏幕y坐标
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight&&
					y<blankHeight+picHeight)
			{//快速模式按钮
				index=KSMS_VIEW;
				shiftState(index);		//将按钮切换到选定状态
				shiftView(index);		//将界面切换到选定界面
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+1*(picHeight+unit_blankHeight)&&
					y<blankHeight+1*(picHeight+unit_blankHeight)+picHeight	
			)
			{//设置按钮
				index=OPTION_VIEW;
				shiftState(index);		//将按钮切换到选定状态
				shiftView(index);		//将界面切换到选定界面
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+2*(picHeight+unit_blankHeight)&&
					y<blankHeight+2*(picHeight+unit_blankHeight)+picHeight)
			{//关于按钮
				index=ABOUT_VIEW;
				shiftState(index);		//将按钮切换到选定状态
				shiftView(index);		//将界面切换到选定界面
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+3*(picHeight+unit_blankHeight)&&
					y<blankHeight+3*(picHeight+unit_blankHeight)+picHeight)
			{//帮助按钮
				index=HELP_VIEW;
				shiftState(index);		//将按钮切换到选定状态
				shiftView(index);		//将界面切换到选定界面
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+4*(picHeight+unit_blankHeight)&&
					y<blankHeight+4*(picHeight+unit_blankHeight)+picHeight)
			{//退出按钮
				System.exit(0);
			}
			
			break;
		}
		return super.onTouchEvent(event);
	}
	 @Override
	 public boolean onKeyDown(int keyCode,KeyEvent event)
	   {
			  switch(keyCode)
			  {
				  case KeyEvent.KEYCODE_DPAD_CENTER:
					  //切换到指定界面 
					  shiftView(index);
					   return true;
				   case KeyEvent.KEYCODE_DPAD_DOWN:
					   //选定下一个按钮
					   index=(index+1)%5;//index在0-5之间
					   shiftState(index);//调用shiftState方法，切换按钮图片
					   return true;
				   case KeyEvent.KEYCODE_DPAD_UP:
					   //选定上一个按钮
					   index=(index-1+5)%5;//index在1-5之间变换
					   shiftState(index);//调用shiftState方法，切换按钮图片
					   return true;
			  }
		   return false;
	   }
	 //切换界面
	 public void shiftView(int index)
	 {
		 switch(index)
		  {
			  case KSMS_VIEW:
				  myActivity.setLoadView();//加载界面
				  break;
			   case OPTION_VIEW:
				   myActivity.setSoundView();//设置界面
				   break;
			   case ABOUT_VIEW:
				   myActivity.setAboutView();//关于界面
				   break;
			   case HELP_VIEW:
				   myActivity.setHelpView();//帮助界面
				   break;
			   case EXIT_VIEW:
				   System.exit(0);//退出程序
				   break;
		  }
	 }
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	 @Override
	public void surfaceCreated(SurfaceHolder holder) {
	
		shiftState(index);	//调用shiftState方法
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
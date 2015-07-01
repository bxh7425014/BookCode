package wyf.tzz.gdl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {

	GL_Demo myActivity;

	Paint paint;
	int screenWidth = 320;//屏幕宽度
	int screenHeight = 480;//屏幕高度
	int hintWidth=100;//提示宽度
	int hintHeight=20;//提示高度
	
	Bitmap soundYN;//是否打开声音提示的标志
	Bitmap soundYes;//打开声音
	Bitmap soundNo;//关闭声音
	Bitmap background;//背景

	public SoundSurfaceView(GL_Demo myActivity) {
		super(myActivity);
		this.myActivity=myActivity;
		initBitmap();
		this.getHolder().addCallback(this);
	}
	public void initBitmap()
	{ //初始化图片
		soundYN=BitmapFactory.decodeResource(getResources(), R.drawable .soundyn);
		soundYes=BitmapFactory.decodeResource(getResources(), R.drawable.soundyes);
		soundNo=BitmapFactory.decodeResource(getResources(), R.drawable.soundno);
		background=BitmapFactory.decodeResource(getResources(), R.drawable.sound_bg);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		//绘制声音提示		
		canvas.drawBitmap(background, 0, 0, null);//背景
		canvas.drawBitmap(soundYN,screenWidth/2-hintWidth/2,screenHeight/2-hintHeight/2, null);
		canvas.drawBitmap(soundYes, screenWidth-32, screenHeight-16,null);//绘制打开音效
		canvas.drawBitmap(soundNo, 0, screenHeight-16,null);//绘制关闭音效                                                                                                                                                                                                                                                                                                                             
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();//获得屏幕被触控点x坐标
		int y = (int) event.getY();//获得屏幕被触控点y坐标
		
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		//打开声音
			if(x<screenWidth&&x>screenWidth-32
		    	  &&y<screenHeight&&y>screenHeight-16)
			{//按下
				myActivity.isSound=true;
				myActivity.mpBack.start();//播放游戏背景音乐
				myActivity.setMenuView();//切换至菜单项
			}
			//关闭声音
			if(x<32&&x>0
		  	    	  &&y<screenHeight&&y>screenHeight-16)
			{//按下
				myActivity.isSound=false;
				myActivity.mpBack.pause();//暂停游戏背景音乐
				myActivity.mpWin.pause();//暂停游戏胜利音乐
				myActivity.setMenuView();//切换至主菜单项
			}
			break;
		}
			
		return super.onTouchEvent(event);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		Canvas canvas=null;//初始化画布
		
		try
		{
			canvas=holder.lockCanvas();//画布加锁
			
			synchronized(holder)
			{
				onDraw(canvas);//重绘
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
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	 @Override
	   public boolean onKeyDown(int keyCode,KeyEvent event)		//为按键添加监听
	   { 
			  switch(keyCode)
			  {     
				   case KeyEvent.KEYCODE_BACK:					//如果按下返回键
						
				   myActivity.setMenuView();					//切换到主菜单界面
				
				   return true;
			  }
			  
		   return false;										//false，其他按键交给系统处理
	   }
		   
}
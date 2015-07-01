package wyf.tzz.lta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {
	GL_Demo myActivity;			//activity的引用
	int screenWidth = 480;		//屏幕宽度
	int screenHeight = 320;		//屏幕高度
	Bitmap background;			//背景图片

	public SoundSurfaceView(GL_Demo myActivity) {
		super(myActivity);						//调用父类方法
		this.myActivity=myActivity;
		initBitmap();							//初始化图片
		this.getHolder().addCallback(this);		//设置回调方法
	}
	
	public void initBitmap(){
		//初始化图片
		background=BitmapFactory.decodeResource(getResources(), R.drawable.soundbg);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(background, 0, 0, null);//绘制背景		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();//获得屏幕被触控点x坐标
		int y = (int) event.getY();//获得屏幕被触控点y坐标
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:		//按下时	
				if(x<32&&x>0&&y<screenHeight&&y>screenHeight-32){
					myActivity.isSound=true;	//将是否播放声音标志设为true			
					myActivity.mpBack.start();	//播放游戏背景音乐
					myActivity.setMenuView();	//切换至菜单项
				}
				else if(x<screenWidth&&x>screenWidth-32				//关闭声音
				    	  &&y<screenHeight&&y>screenHeight-32)
				{
					myActivity.isSound=false;	//将是否播放声音标志设为false
					myActivity.mpBack.pause();	//暂停游戏背景音乐
					myActivity.setMenuView();	//切换至主菜单项
				}
				break;
		}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas=null;				//画布
		try{
			canvas=holder.lockCanvas();//画布加锁
			synchronized(holder){
				onDraw(canvas);			//重绘
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(canvas!=null){
				holder.unlockCanvasAndPost(canvas);//绘制后解锁
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}		//view销毁的时候调用
	
	 @Override
	 public boolean onKeyDown(int keyCode,KeyEvent event)		//为按键添加监听
	 { 
		  switch(keyCode)
		  {     
			   case KeyEvent.KEYCODE_BACK:						//如果按下返回键
				   	myActivity.setMenuView();					//切换到主菜单界面				   	
				   	return true;
			   case KeyEvent.KEYCODE_DPAD_CENTER:				//如果按下中键
				   	myActivity.isSound=false;					//将是否播放声音标志设为false
					myActivity.mpBack.pause();					//暂停游戏背景音乐
					myActivity.setMenuView();					//切换至主菜单项
					return true;
		  }
		  return false;											//其他按键交给系统处理
	 }
}
package wyf.tzz.lta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoadSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {
	GL_Demo myActivity;			//activity的引用
	int screenWidth = 480;		//屏幕宽度
	int screenHeight = 320;		//屏幕高度
	int picWidth=112;			//返回按钮图片宽度
	int picHeight=40;			//返回按钮图片高度
	Bitmap bgAbout;				//背景图片
	
	public LoadSurfaceView(GL_Demo myActivity) {		
		super(myActivity);						//调用父类方法
		this.myActivity=myActivity;
		initBitmap();							//加载图片
		this.getHolder().addCallback(this);		//设置回调方法
	} 
	
	public void initBitmap()	//初始化图片
	{ //加载背景图片
		bgAbout=BitmapFactory.decodeResource(getResources(), R.drawable.loadbg);
	}
	
	@Override
	public void onDraw(Canvas canvas){			//绘制图形
		canvas.drawBitmap(bgAbout, 0, 0,null);	//画背景图片
	}
	
	//view改变的时候调用
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	
	//view创建的时候调用
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas=null;					//画布
		try{
			canvas=holder.lockCanvas();		//画图之前先锁定画布
			synchronized(holder){
				onDraw(canvas);				//调用onDraw()方法
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(canvas!=null){
				holder.unlockCanvasAndPost(canvas);	//画图完成之后给画布解锁
			}
		}
	}

	//view销毁的时候调用
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
	
	 @Override
	   public boolean onKeyDown(int keyCode,KeyEvent event){//为按键添加监听
		  switch(keyCode){     
			   case KeyEvent.KEYCODE_BACK:					//如果按下返回键
			   myActivity.setMenuView();					//切换到主菜单界面
			   return true;
		  }
		  return false;										//false，其他按键交给系统处理
	   }
}


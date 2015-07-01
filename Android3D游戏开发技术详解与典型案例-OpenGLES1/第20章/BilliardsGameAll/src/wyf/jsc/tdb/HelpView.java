package wyf.jsc.tdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.tdb.Constant.*;

public class HelpView extends SurfaceView implements SurfaceHolder.Callback {
 
	MyActivity activity;
	Bitmap ishelp;
	

	float mPreviousX;  				//记录上次触控的X坐标
	float mPreviousY;  				//记录上次触控的Y坐标
	
	public HelpView(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		ishelp=BitmapFactory.decodeResource(this.getResources(), R.drawable.help);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(ishelp, 0,0, null);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{		
		//获取当前触控点的XY坐标
        float x = e.getX();
        float y = e.getY();
        //根据触控的动作执行业务逻辑
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:    //若动作为按下触控笔则记录XY位置
				mPreviousX=x;				//记录触控笔X位置
				mPreviousY=y;				//记录触控笔Y位置
				break;
			case MotionEvent.ACTION_UP:      //根据X位移执行业务逻辑
				//计算X位移
				float dx=x-mPreviousX;
				if(dx<-slideSpan)
				{   		//若X位移小于阈值则返回主菜单
					activity.toAnotherView(ENTER_MENU);
				}
				break;
		}
		return true;
	}	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)    //触控事件
	{
		if(keyCode==4)     //返回系统  
		{			
			activity.toAnotherView(ENTER_MENU);
			return true;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
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
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	

}

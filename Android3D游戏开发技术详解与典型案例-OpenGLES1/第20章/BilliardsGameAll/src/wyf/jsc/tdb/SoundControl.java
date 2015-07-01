package wyf.jsc.tdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.tdb.Constant.*;

public class SoundControl extends SurfaceView implements SurfaceHolder.Callback
{
	MyActivity activity;
	Bitmap isSound;

	public SoundControl(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		isSound=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(isSound, 0,0, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				int x = (int) event.getX();
				int y = (int) event.getY();
				if(x>25&&x<85&&y>270&&y<300)
				{
					activity.toAnotherView(ENTER_MENU);
			 		soundFlag=true;
				}
				else if(x>390&&x<455&&y>270&&y<300)
				{
					activity.toAnotherView(ENTER_MENU);
			 		soundFlag=false;
				}break;
			
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}
	public boolean onKeyDown(int keyCode,KeyEvent e)    //触控事件
	{
		if(keyCode==4)     //返回系统  
		{			
			return false;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {      //创建时调用
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
	public void surfaceDestroyed(SurfaceHolder arg0) {   //销毁时调用
		// TODO Auto-generated method stub
		
	}

}

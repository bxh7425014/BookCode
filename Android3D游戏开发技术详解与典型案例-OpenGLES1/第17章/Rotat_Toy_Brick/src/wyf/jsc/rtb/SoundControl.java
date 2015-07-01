package wyf.jsc.rtb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.rtb.Constant.*;
import android.view.MotionEvent;



public class SoundControl extends SurfaceView implements SurfaceHolder.Callback {
	
//	SurfaceViewExampleActivity activity;
	MainActivity activity;
	Bitmap isSound;//声音选择图片

	//Paint paint;


	public SoundControl(MainActivity activity) {
		super(activity);
		getHolder().addCallback(this);
		this.activity=activity;
		isSound=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound);
		//paint=new Paint();
		//paint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{   super.onDraw(canvas);		
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
			if(x>5&&x<35&&y>440&&y<470)
			{
				//Log.d("sss","ddfdfgdfgdgfhgghgjhjhjhjhkjkklkfdf");
				activity.toAnotherView(ENTER_MENU);
				soundFlag=true;
				
			}
			else if(x>285&&x<315&&y>440&&y<470)
			{
				//Log.d("sss","dfdf");
				activity.toAnotherView(ENTER_MENU);
				soundFlag=false;
			}break;
		case MotionEvent.ACTION_UP:break;
		}
		
		return true; 	
	}
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			//activity.toAnotherView(ENTER_MENU);
			return false;
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
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

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用

	}

}

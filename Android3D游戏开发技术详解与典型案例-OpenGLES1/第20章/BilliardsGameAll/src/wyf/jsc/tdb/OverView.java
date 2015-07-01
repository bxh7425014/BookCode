package wyf.jsc.tdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.tdb.Constant.*;
public class OverView extends SurfaceView implements SurfaceHolder.Callback{

	MyActivity activity;

	Bitmap overView; 
	public OverView(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		overView=BitmapFactory.decodeResource(this.getResources(), R.drawable.over);
	}

	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(overView, 0,0, null);
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)    //触控事件
	{
		if(keyCode==4)     //放回系统  
		{			
			activity.toAnotherView(ENTER_MENU);
			return true;
		}
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				int x = (int) event.getX();
				int y = (int) event.getY();
				if(x>145&&x<340&&y>110&&y<145)
				{
					scoreOne=0;
					scoreTwo=0;
					Cue.angleZ=0;
					score=0;
					activity.toAnotherView(ENTER_MENU);
				}						
				else if(x>145&&x<340&&y>170&&y<210)
				{
					System.exit(0);
				}	
				break;
		}
		
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
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
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}

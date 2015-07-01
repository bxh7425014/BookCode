package wyf.jsc.rtb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.rtb.Constant.*;

public class Help extends SurfaceView implements SurfaceHolder.Callback {
	
	MainActivity activity;
	Bitmap background;
	Bitmap help;
	Bitmap about;
	public final float X=40;
	public final float Y=100;
	Paint paint;
	
	
	public Help(MainActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.getHolder().addCallback(this);
		this.activity=activity;
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.bground);
		help=BitmapFactory.decodeResource(this.getResources(),R.drawable.helper);
		about=BitmapFactory.decodeResource(this.getResources(),R.drawable.aboutss);
		paint=new Paint();
		paint.setAntiAlias(true);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x1=0;
		float x2=0;
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			float tempX=e.getX();
			float tempY=e.getY();
			x1=e.getX();
			if(tempX>285&&tempX<315&&tempY>440&&tempY<470)
			{
				activity.toAnotherView(ENTER_MENU);
				threadFlag=true;
			}
			break;
		case MotionEvent.ACTION_UP:
									x2=e.getX();
									if(x2<x1)
									{
										activity.toAnotherView(ENTER_MENU);
										soundFlag=true;
									}
									status=status;
									keyStatus=status;
									break;
		}
		return true;
		
	}
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			return false;
		}
		return true;
		
	}
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(background, 0,0, paint);
		switch(surfaceId)
		{
		case 0:		
			canvas.drawBitmap(help, X,Y,paint);
			break;
		case 1:
			canvas.drawBitmap(about, X,Y,paint);
			break;
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas=holder.lockCanvas();
		try
		{
			synchronized(holder)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
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

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
}


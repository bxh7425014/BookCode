package wyf.jsc.rtb;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.rtb.Constant.*;
public class Setting extends SurfaceView implements SurfaceHolder.Callback {
	
	MainActivity activity;
	Bitmap soundOpen;
	Bitmap soundClose;
	Bitmap background;
	public final float X=50;
	public final float Y=120;
	SettingThread st;
	MainActivity gkd=(MainActivity)this.getContext();
	public Setting(MainActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		soundOpen=BitmapFactory.decodeResource(this.getResources(), R.drawable.soundopen);
		soundClose=BitmapFactory.decodeResource(this.getResources(), R.drawable.soundclose);
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.bground);
		st=new SettingThread(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			float tempX=e.getX();
			float tempY=e.getY();
			if(tempX>285&&tempX<315&&tempY>440&&tempY<470)
			{
				activity.toAnotherView(ENTER_MENU);
				threadFlag=true;
			}
			break;
		case MotionEvent.ACTION_UP:	break;
		}
		return true;
		
	}
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			activity.toAnotherView(ENTER_MENU);
		}
		switch(keyCode)
		{
		case 21:
			Log.d(soundFlag+"", "soundFlag....before....21");
			if(soundSetFlag==1)
			{
				soundSetFlag=2;
				gkd.mpBack.pause();
				break;
			}
			if(soundSetFlag==2)
			{
				soundSetFlag=1;
				gkd.mpBack.start();				
				break;
			}	
			break;
		case 22:
			if(soundSetFlag==1)
			{
				soundSetFlag=2;
				gkd.mpBack.pause();
				break;
			}
			else if(soundSetFlag==2)
			{
				soundSetFlag=1;
				gkd.mpBack.start();
				gkd.mpBack.start();	
				break;
			}
			Log.d(soundFlag+"", "soundFlag....after....22");
			break;
		case 23:break;
		}
		return true;	
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(background, 0, 0, null);
		if(soundFlag)
		{
			canvas.drawBitmap(soundClose, X, Y, null);			
		}
		if(!soundFlag)
		{
			canvas.drawBitmap(soundOpen, X, Y, null);
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
		settingFlag=true;
		try
		{
			synchronized(holder)
			{
				onDraw(canvas);
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
				holder.unlockCanvasAndPost(canvas);
			}
		}
		st.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		settingFlag=false;
	}

}


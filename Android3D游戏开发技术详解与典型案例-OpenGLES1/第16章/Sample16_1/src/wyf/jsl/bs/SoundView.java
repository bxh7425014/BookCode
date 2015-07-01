package wyf.jsl.bs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsl.bs.Constant.*;

public class SoundView extends SurfaceView implements SurfaceHolder.Callback
{
	BasketballActivity activity;
	Paint paint;
	Bitmap background;
	Bitmap soundsOn;
	Bitmap soundsOff;
	Bitmap sounds;
		
	public SoundView(BasketballActivity activity) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		
		paint=new Paint();
		paint.setAntiAlias(true);
		
		initBitmap();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(soundsOff, 276, 420, paint);
		canvas.drawBitmap(soundsOn, 4, 420, paint);
		canvas.drawBitmap(sounds, 30, 70, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_UP:
			if(x>=276&&x<=316&&y>=420&&y<=460)
			{
				activity.hd.sendEmptyMessage(GAME_MENU);
				SOUND_FLAG=false;
				//禁止声音方法
			}
			else if(x>=4&&x<=44&&y>=420&&y<=460)
			{
				activity.hd.sendEmptyMessage(GAME_MENU);
				SOUND_FLAG=true;
				//添加声音方法
			}
			SOUND_MEMORY=SOUND_FLAG;
		}
		return true;		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas=holder.lockCanvas();
		try
		{
			synchronized(holder)
			{
				onDraw(canvas);//绘制
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
	
	public void initBitmap()
	{
		background=BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon);
		soundsOn=BitmapFactory.decodeResource(activity.getResources(), R.drawable.yes);
		soundsOff=BitmapFactory.decodeResource(activity.getResources(), R.drawable.no);
		sounds=BitmapFactory.decodeResource(activity.getResources(), R.drawable.sounds);
	}
	
	
}
package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewSound extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Demo activity;
	Paint paint;
	Bitmap sound;
	Bitmap yes;
	Bitmap no;
	
	public SurfaceViewSound(Activity_GL_Demo activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);
		
		initBitmap();
		
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(sound, SCREEN_WIDTH/2-sound.getWidth()/2, SCREEN_HEIGHT/2-sound.getHeight()/2, paint);
		canvas.drawBitmap(yes, 10, SCREEN_HEIGHT-yes.getHeight(), paint);
		canvas.drawBitmap(no, SCREEN_WIDTH-no.getWidth()-10, SCREEN_HEIGHT-no.getHeight(), paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX(); 
		float y=e.getY();
		
		switch(e.getAction())
		{ 
			case MotionEvent.ACTION_DOWN:
				if(x>=10&&x<=(yes.getWidth()+10)&&y>=(SCREEN_HEIGHT-yes.getHeight())&&y<=SCREEN_HEIGHT)
				{
					SOUND_FLAG=true;//¿ªÆôÉùÒô
					BACK_SOUND_FLAG=true;
					activity.hd.sendEmptyMessage(GAME_MENU);	
				}
				if(x>=(SCREEN_WIDTH-no.getWidth()-10)&&x<=(SCREEN_WIDTH-10)&&y>=(SCREEN_HEIGHT-no.getHeight())&&y<=SCREEN_HEIGHT)
				{
					SOUND_FLAG=false;//²»¿ªÆôÉùÒô
					BACK_SOUND_FLAG=false;
					activity.hd.sendEmptyMessage(GAME_MENU);
				}
		}
		return true;
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas=holder.lockCanvas();
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
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	public void initBitmap()
	{
		sound=BitmapFactory.decodeResource(getResources(), R.drawable.sounds);
		yes=BitmapFactory.decodeResource(getResources(), R.drawable.yes);
		no=BitmapFactory.decodeResource(getResources(), R.drawable.no);
	}

}

package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewAbout extends SurfaceView implements SurfaceHolder.Callback
{
	Activity_GL_Demo activity;
	private Paint paint;
	private Bitmap about;
	private Bitmap ok;
	public SurfaceViewAbout(Activity_GL_Demo activity) {
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
		canvas.drawBitmap(about, 0, 0, paint);
		canvas.drawBitmap(ok, 400, 280, paint);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_UP:
			if(x>=400&&x<=480&&y>=280&&y<=320)
			{
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
		Canvas canvas=holder.lockCanvas();
		try
		{
			synchronized(holder)
			{
				onDraw(canvas);//»æÖÆ
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

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void initBitmap()
	{
		about=BitmapFactory.decodeResource(activity.getResources(), R.drawable.help);
		ok=BitmapFactory.decodeResource(activity.getResources(), R.drawable.ok);
	}
	
}
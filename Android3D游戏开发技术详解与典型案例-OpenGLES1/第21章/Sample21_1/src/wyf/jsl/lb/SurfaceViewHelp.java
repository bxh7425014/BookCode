package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewHelp extends SurfaceView implements SurfaceHolder.Callback {
	Activity_GL_Demo activity;
	private Bitmap help;
	private Bitmap ok;
	private Paint paint;
	public SurfaceViewHelp(Activity_GL_Demo activity) {
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
		canvas.drawBitmap(help, 0, 0, paint);
		canvas.drawBitmap(ok, 380, 280, paint);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_UP:
			if(x>=380&&x<=480&&y>=280&&y<=320)
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
		help=BitmapFactory.decodeResource(getResources(), R.drawable.help2);
		ok=BitmapFactory.decodeResource(getResources(), R.drawable.ok);
	}

}

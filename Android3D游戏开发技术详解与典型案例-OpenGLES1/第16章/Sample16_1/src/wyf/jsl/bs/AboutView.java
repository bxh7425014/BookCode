package wyf.jsl.bs;

import static wyf.jsl.bs.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AboutView extends SurfaceView implements SurfaceHolder.Callback
{
	BasketballActivity activity;
	Paint paint;
	Bitmap background;
	Bitmap ok;
	public AboutView(BasketballActivity activity) {
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
		canvas.drawBitmap(background, 0, 0, paint);
		canvas.drawBitmap(ok, 20, 440, paint);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_UP:
			if(x>=20&&x<=120&&y>=440&&y<=475)
			{
				activity.hd.sendEmptyMessage(GAME_MENU);
			}
			
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

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void initBitmap()
	{
		background=BitmapFactory.decodeResource(activity.getResources(), R.drawable.jieshao);
		ok=BitmapFactory.decodeResource(activity.getResources(), R.drawable.ok);
	}
	
}
package wyf.jsl.bs;

import static wyf.jsl.bs.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class OverView extends SurfaceView implements SurfaceHolder.Callback
{
	BasketballActivity activity;
	Paint paint;
	MenuView w;
	
	Bitmap background;
	Bitmap exit;
	Bitmap retry;
	
	public OverView(BasketballActivity activity,MenuView w) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		this.w=w;
		paint=new Paint();
		paint.setAntiAlias(true);
		initBitmap();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(background, 0, 0, paint);
		canvas.drawBitmap(retry, 4, 420, paint);
		canvas.drawBitmap(exit,260, 420,paint);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_UP:
			if(x>=260&&x<=316&&y>=420&&y<=460)
			{
				System.exit(0);
			}
			else if(x>=4&&x<=104&&y>=420&&y<=460)
			{
				w.left1=LEFT;
				w.left2=LEFT;
				w.left3=LEFT;
				w.left4=LEFT;
				
				activity.hd.sendEmptyMessage(GAME_MENU);
				//添加声音方法
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
		background=BitmapFactory.decodeResource(activity.getResources(), R.drawable.background);
		retry=BitmapFactory.decodeResource(activity.getResources(), R.drawable.retry);
		exit=BitmapFactory.decodeResource(activity.getResources(), R.drawable.exit);
	}
	
}
package wyf.jsl.bs;

import static wyf.jsl.bs.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class LoadView extends SurfaceView implements SurfaceHolder.Callback
{
	Bitmap load;
    BasketballActivity activity;
	
	public LoadView(BasketballActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		
		this.activity=activity;
		this.getHolder().addCallback((Callback) this);
		load=BitmapFactory.decodeResource(activity.getResources(), R.drawable.background);
	}

	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(load, 0, 0, null);
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
    
		
		new Thread()
		{
			public void run()
			{
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		score=0;//还原得分
		deadtimes=10;//还原倒计时
		
		SOUND_FLAG=SOUND_MEMORY;//还原声音选择
		
		activity.hd.sendEmptyMessage(GAME_PLAY);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	
}
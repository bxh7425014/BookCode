package wyf.jsc.tdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoadView extends SurfaceView implements SurfaceHolder.Callback{

	MyActivity activity;
	Bitmap isloading;				//±≥æ∞Õº∆¨
	
	public LoadView(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		isloading=BitmapFactory.decodeResource(this.getResources(), R.drawable.loading);
	}
	//ªÊ÷∆Õº–Œ
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(isloading, 0, 0,null);	//loadÕº∆¨
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas = holder.lockCanvas();//ªÒ»°ª≠≤º
		try{
			synchronized(holder){
				onDraw(canvas);//ªÊ÷∆
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
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}

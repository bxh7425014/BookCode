package com.bn.carracer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ViewBreaking extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;//声明引用
	Bitmap breaking;
	
	public ViewBreaking(Activity_GL_Racing activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		breaking=BitmapFactory.decodeResource(this.getResources(), R.drawable.breaking);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
			canvas.drawBitmap(breaking, Activity_GL_Racing.screen_xoffset,0, null);
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Canvas canvas = holder.lockCanvas();//获取画布
		try{
			synchronized(holder){
				onDraw(canvas);//绘制
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
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}

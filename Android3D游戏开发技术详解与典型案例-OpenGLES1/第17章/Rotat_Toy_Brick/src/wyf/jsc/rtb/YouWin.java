package wyf.jsc.rtb;


import static wyf.jsc.rtb.Constant.*;
import static wyf.jsc.rtb.Constant.soundFlag;
import static wyf.jsc.rtb.Constant.winAndLose;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class YouWin extends SurfaceView implements SurfaceHolder.Callback{
	
	MainActivity activity;
	int screenWidth = 320;//屏幕宽度
	int screenHeight = 480;//屏幕高度
	
	Bitmap uwin;	
	Bitmap ulose;
	public YouWin(MainActivity activity) {
		super(activity);
		
		getHolder().addCallback(this);	
		
		this.activity=activity;		
		// TODO Auto-generated constructor stub
		initBitemap();
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>5&&x<35&&y>450&&y<490)
			{
				//Log.d("sss","ddfdfgdfgdgfhgghgjhjhjhjhkjkklkfdf");
				activity.toAnotherView(START_GAME);
				soundFlag=true;
				activity.initSound();
			}
			else if(x>285&&x<315&&y>450&&y<490)
			{
				//Log.d("sss","dfdf");
				activity.toAnotherView(ENTER_MENU);
				soundFlag=true;
				threadFlag=true;
			}
			break;
		}
		return true;
	}
	private void initBitemap() {
		// TODO Auto-generated method stub
		
		uwin = BitmapFactory.decodeResource(getResources(), R.drawable.win); 	
		ulose=BitmapFactory.decodeResource(getResources(), R.drawable.lose);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);	
		switch(winAndLose)
		{
		case 0:canvas.drawBitmap(uwin,0,0, null);break;
		case 1:canvas.drawBitmap(ulose, 0, 0,null);break;
		}
	}		
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}

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

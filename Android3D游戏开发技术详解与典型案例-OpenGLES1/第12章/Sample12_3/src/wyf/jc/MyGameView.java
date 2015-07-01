package wyf.jc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyGameView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	SensorBallActivity activity;
	BallGoThread bgt;
	GameViewDrawThread gvdt;
	Paint paint;//画笔
	Bitmap tableBM;
	Bitmap ballBM;
	
	int ballSize=64;//球的尺寸
	int dLength=5;//标准步进
	int ballX=100;
	int ballY=80;
	int dx=2;
	int dy=2;
	
	public MyGameView(SensorBallActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		
		//加载图片
		tableBM=BitmapFactory.decodeResource(activity.getResources(), R.drawable.table);
		ballBM=BitmapFactory.decodeResource(activity.getResources(), R.drawable.ball);
		
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		bgt=new BallGoThread(this);
		gvdt=new GameViewDrawThread(this);
	}

	public void onDraw(Canvas canvas){					
		//贴底纹
		canvas.drawBitmap(tableBM, 0, 0, paint);		
		//贴球
		canvas.drawBitmap(ballBM, ballX, ballY, paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
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
		//启动球定时根据重力移动的线程
		bgt.start();
		//启动定时重新绘制画面的线程
		gvdt.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用
         
	}
}
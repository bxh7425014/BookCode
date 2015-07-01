package wyf.zcl;
import android.graphics.Canvas;					//引入相关包
import android.view.SurfaceHolder;				//引入相关包
//该类的作用是时时刷新onDraw，进行画面的重绘
public class OnDrawThread extends Thread{
	MySurfaceView msv;		//得到MySurfaceView的引用
	SurfaceHolder sh;		//SurfaceHolder引用
	public OnDrawThread(MySurfaceView msv) {
		super();
		this.msv = msv;			//构造方法中，将msv引用指向调用了该类的MySurfaceView的对象
		sh=msv.getHolder();
	}
	@Override
	public void run() {
		super.run();
		Canvas canvas = null;	//Canvas的引用
		while(true){
			try{
				canvas=sh.lockCanvas(null);			//将canvas的引用指向surfaceView的canvas的对象
				synchronized(this.sh){				//绘制过程，可能带来同步方面的问题，加锁
					if(canvas!=null){
					msv.onDraw(canvas);
					}
				}
			}finally{
				try{
						if(sh!=null){
							sh.unlockCanvasAndPost(canvas);	//绘制完后解锁
						}
				}catch(Exception e){e.printStackTrace();}
			}
			try{
				Thread.sleep(Constant.ONDRAWSPEED);					//休息1秒钟
			}catch(Exception e){e.printStackTrace();}
		}
	}
}

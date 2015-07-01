package wyf.jc;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

//定时重新绘制画面的线程
public class GameViewDrawThread extends Thread{
	boolean flag = true;
	boolean pauseFlag=false;
	int sleepSpan = 100;
	MyGameView gameView;
	SurfaceHolder surfaceHolder;
	public GameViewDrawThread(MyGameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}
	public void run(){
		Canvas c;
        while (this.flag) {
            c = null;
            if(!pauseFlag)
            {
            	try {
	            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                c = this.surfaceHolder.lockCanvas(null);
	                synchronized (this.surfaceHolder) {
	                	gameView.onDraw(c);//绘制
	                }
	            } finally {
	                if (c != null) {
	                	//并释放锁
	                    this.surfaceHolder.unlockCanvasAndPost(c);
	                }
	            }
            }
	            
            try{
            	Thread.sleep(sleepSpan);//睡眠指定毫秒数
            }
            catch(Exception e){
            	e.printStackTrace();//打印堆栈信息
            }
        }
	}
}

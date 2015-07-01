package wyf.jsc.tdb;

import static wyf.jsc.tdb.Constant.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SelectView extends SurfaceView implements SurfaceHolder.Callback {
	
	MyActivity activity;
	Bitmap isSelect;  
	public boolean flag=true;
	public SelectView(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		this.activity=activity;
		isSelect=BitmapFactory.decodeResource(this.getResources(), R.drawable.select);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);		
		canvas.drawBitmap(isSelect, 0,0, null);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				int x = (int) event.getX();
				int y = (int) event.getY();
				if(x>145&&x<340&&y>110&&y<145)
				{
					cueFlag=true;//重绘球杆的标志位
					overFlag=false;//打球结束的标志位
					hitFlag=false;//控制是否打球
					hitSound=false;
					score=0;//记录得分
					scoreOne=0;//first player score
					scoreTwo=0;//second player score
					scoreNODFlag=1;//用来判断给那位玩家进球加分  1  表示玩家1    2 表示玩家2
					scoreNOD=0;//用来标志是网络版还是单机版     0  表示单机版   1 表示网络版
					scoreTip=1;//用来标志下一次谁有击球权
					sendFlag=false;//控制客户端发送一次消息。
					winLoseFlag=0;
					
					activity.toAnotherView(START_LOAD);
					hitFlag=true;//打开击球请求					
					scoreNOD=0;//单机版的移标版绘制
				}						
				else if(x>145&&x<340&&y>170&&y<210)
				{
					MySurfaceView.id=0;
					cueFlag=true;//重绘球杆的标志位
					overFlag=false;//打球结束的标志位
					hitFlag=false;//控制是否打球
					hitSound=false;
					score=0;//记录得分
					scoreOne=0;//first player score
					scoreTwo=0;//second player score
					scoreNODFlag=1;//用来判断给那位玩家进球加分  1  表示玩家1    2 表示玩家2
					scoreNOD=0;//用来标志是网络版还是单机版     0  表示单机版   1 表示网络版
					scoreTip=1;//用来标志下一次谁有击球权
					sendFlag=false;//控制客户端发送一次消息。
					winLoseFlag=0;
					activity.toAnotherView(ENTER_NET);
					scoreNOD=1;//网络版的移标版绘制
				break;
				}
			case MotionEvent.ACTION_UP:
				break;
		}
		
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)    //触控事件
	{
		Log.d("sadsad","onKeyUp"+keyCode);
		if(keyCode==4)
		{
			activity.toAnotherView(ENTER_MENU);			
			Log.d("onKeyUp 77","onKeyUp88");
			return true;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
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
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}

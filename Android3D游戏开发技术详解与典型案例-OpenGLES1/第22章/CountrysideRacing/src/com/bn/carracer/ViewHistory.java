package com.bn.carracer;
import static com.bn.carracer.Constant.*;
import static com.bn.carracer.Activity_GL_Racing.*;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ViewHistory extends SurfaceView implements SurfaceHolder.Callback{

	Activity_GL_Racing activity;
	Bitmap history;//背景
	Paint paint;//画笔
	
	ArrayList<String[]> al=new ArrayList<String[]>();//用来获取结果集	
	boolean moveFlag=false;//移动会不移动的标志位 false为不移动，true为移动
	private float startMoveY=0;//记录开始的位置
	float width=25;//格之间的宽的
	
	int TOTAL_ROWS=5;//显示的最多行数
	int rowFirst=0;//当前表格第一行行号
	
	public ViewHistory(Activity_GL_Racing activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		paint=new Paint();
		getHolder().addCallback(this);
		history=BitmapFactory.decodeResource(this.getResources(), R.drawable.history);//加载背景图片
		al=DBUtil.getResult();		
	}


	
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float x=event.getX();
		float y=event.getY();
		switch(event.getAction())
		{
		    case MotionEvent.ACTION_DOWN:
		      //写返回按钮的代码
		      if(x>387+Activity_GL_Racing.screen_xoffset&&x<474+Activity_GL_Racing.screen_xoffset&&y>280&&y<310)
		      {
		    	  activity.toAnotherView(CHOOSE);//返回到选择界面
		      }
		    break;
			case MotionEvent.ACTION_MOVE:				
				int dy=(int) (y-startMoveY);//获取y方向上的移动量
				
				if(al.size()<=TOTAL_ROWS)
				{
					return true;
				}
				
				if(dy>25)//如果移动的距离大于一个格的距离
				{
					rowFirst=rowFirst-1;
					if(rowFirst<0){rowFirst=0;};
					startMoveY=y;//将当前y值赋予开始点
					repaint();
				}
				if(dy<-25)
				{
					rowFirst=rowFirst+1;
					if(rowFirst>al.size()-TOTAL_ROWS)
					{
						rowFirst=al.size()-TOTAL_ROWS;
					}
					startMoveY=y;//将当前y值赋予开始点
					repaint();
				}				
				break;
		}
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(history, Activity_GL_Racing.screen_xoffset,0, paint);//绘制背景		
		for(int i=0;i<5;i++)//绘制使用时间
		{
			if (i+rowFirst>al.size()-1) break;
			
			int currIndex=i+rowFirst;
			 
			String timeStr=al.get(currIndex)[0];
			String dateStr=al.get(currIndex)[1];
			for(int j=0;j<timeStr.length();j++)
			{
				char c=timeStr.charAt(j);
				if(c==':')
				{
					canvas.drawBitmap(number[10], 21+j*16+Activity_GL_Racing.screen_xoffset, 139+i*width, paint);
				}else
				{ 
					canvas.drawBitmap(number[c-'0'], 21+j*16+Activity_GL_Racing.screen_xoffset, 139+i*width, paint);
				}
			}
			for(int j=0;j<dateStr.length();j++)
			{
				char d=dateStr.charAt(j);				
				if(d=='-')
				{
					canvas.drawBitmap(number[11], 134+j*16+Activity_GL_Racing.screen_xoffset, 139+i*width, paint);
				}else if(d==' ')
				{
					continue;
				}else if(d==':')
				{
					canvas.drawBitmap(number[10], 134+j*16+Activity_GL_Racing.screen_xoffset, 139+i*width, paint);
				}else
				{
					canvas.drawBitmap(number[d-'0'], 134+j*16+Activity_GL_Racing.screen_xoffset, 139+i*width, paint);
				}
			}			
		}	
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
	//重绘的方法
	public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
	


}

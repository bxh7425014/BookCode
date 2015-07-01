package wyf.sj;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class MySurfaceView extends SurfaceView
implements SurfaceHolder.Callback { //实现生命周期回调接口
	MyActivity mActivity;//activity引用
	Paint paint;//画笔引用
	Game game=new Game(this,getHolder());//创建对象
	final float span=15.7f;//矩形大小
	final int LJCD_COUNT=6;//路径长度
	int LjcdCount;//路径长度
	public MySurfaceView(MyActivity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		this.mActivity=mActivity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
	}
	public void onDraw(Canvas canvas){
		int map[][]=game.map;//获取地图
		int row=map.length;//地图行数
		int col=map[0].length;//地图列数
		canvas.drawARGB(255, 128, 128, 128);//设置背景颜色
		int width=(int)span*map.length;//画布宽度
		int hight=(int)span*map[0].length;// 画布长度
		canvas.setViewport(width, hight);//设置画布大小
		for(int i=0;i<row;i++)//绘制地图
		{
			for(int j=0;j<col;j++)
			{
				if(map[i][j]==1)
				{
					paint.setColor(Color.BLACK);	//设置画笔颜色为黑色	
				}
				else if(map[i][j]==0)
				{
					paint.setColor(Color.WHITE);//设置画笔颜色为白色
				}
				canvas.drawRect(2+j*(span+1),2+i*(span+1),2+j*(span+1)+span,2+i*(span+1)+span, paint);//绘制矩形
			}
		} 
		//绘制寻找过程
		ArrayList<int[][]> searchProcess=game.searchProcess;
		for(int k=0;k<searchProcess.size();k++)
		{
			int[][] edge=searchProcess.get(k);  
			paint.setColor(Color.BLACK);//设置画笔颜色
			canvas.drawLine
			(
					edge[0][0]*(span+1)+span/2+2, edge[0][1]*(span+1)+span/2+2, 
					edge[1][0]*(span+1)+span/2+2, edge[1][1]*(span+1)+span/2+2, paint
			);
			
		}
		
		//绘制结果路径
		if(
				mActivity.mySurfaceView.game.algorithmId==0||
				mActivity.mySurfaceView.game.algorithmId==1||
				mActivity.mySurfaceView.game.algorithmId==2
		)
		{//深度优先，广度优先，广度优先A*
			if(game.pathFlag)
			{
				HashMap<String,int[][]> hm=game.hm;		
				int[] temp=game.target;
				int count=0;//路径长度计数器	
				while(true)
				{
					int[][] tempA=hm.get(temp[0]+":"+temp[1]);//获取结果路径记录
					paint.setColor(Color.BLACK);//设置画笔黑色
					paint.setStrokeWidth(3);//设置画笔宽度		
					canvas.drawLine//绘制线段
				    (
				    	tempA[0][0]*(span+1)+span/2+2,tempA[0][1]*(span+1)+span/2+2,
						tempA[1][0]*(span+1)+span/2+2,tempA[1][1]*(span+1)+span/2+2,paint
				    );

					
					count++;
					//判断是否到出发点
					if(tempA[1][0]==game.source[0]&&tempA[1][1]==game.source[1])
					{
						break;
					}
					
					temp=tempA[1];			
				}
				LjcdCount=count;//记录路径长度
				mActivity.hd.sendEmptyMessage(LJCD_COUNT);//更改路径长度
			}			
		}
		else if(
				mActivity.mySurfaceView.game.algorithmId==3||
				mActivity.mySurfaceView.game.algorithmId==4
		)
		{//Dijkstra路径绘制
		    if(game.pathFlag)
		    {
		    	Log.d(game.pathFlag+"*****************", "dijkst");
		    	HashMap<String,ArrayList<int[][]>> hmPath=game.hmPath;
				ArrayList<int[][]> alPath=hmPath.get(game.target[0]+":"+game.target[1]);
				for(int[][] tempA:alPath)
				{
					paint.setColor(Color.BLACK);	
					paint.setStrokeWidth(3);
					canvas.drawLine
				    (
				    	tempA[0][0]*(span+1)+span/2+2,tempA[0][1]*(span+1)+span/2+2,
						tempA[1][0]*(span+1)+span/2+2,tempA[1][1]*(span+1)+span/2+2,paint
				    );			
				}
				LjcdCount=alPath.size();//记录路径长度
				mActivity.hd.sendEmptyMessage(LJCD_COUNT);//更改路径长度
		    }
		}
		
		
		//绘制出发点
		Bitmap bitmapTmpS=BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.source);
		canvas.drawBitmap(bitmapTmpS, game.source[0]*(span+1),game.source[1]*(span+1) , paint);
		//绘制目标点
		Bitmap bitmapTmpT=BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.target);
		canvas.drawBitmap(bitmapTmpT, game.target[0]*(span+1),game.target[1]*(span+1), paint);
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
	}
	
	public void repaint(SurfaceHolder holder)
	{
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

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用

	}
	
	
}

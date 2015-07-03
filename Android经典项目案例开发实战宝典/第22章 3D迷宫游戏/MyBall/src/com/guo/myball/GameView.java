package com.guo.myball;			//声明包语句
import java.util.Vector;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.guo.myball.Constant.*;
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	MapMasetActivity activity;		//Activity引用
	Canvas c;
	SurfaceHolder holder;
    int scoreWidth = 10;
    int guanshuX;//关数文字X坐标
    int guanshuY;//关数文字Y坐标
    int guanshu=1;
    Bitmap iback;//背景图
    Bitmap[] iscore=new Bitmap[10];//得分图
    Bitmap JianHaotupian;//减号图
    Bitmap JiaHaotupian;//加号图
    Bitmap[] guanShu=new Bitmap[10];//关数图
    Bitmap time_wz;//时间文字图
    Bitmap gread_wz;//成绩文字图
    Bitmap hengXian;//横线
	public GameView(MapMasetActivity activity) {
		super(activity);
		getHolder().addCallback(this);//注册回调接口
		this.activity = activity;
		initBitmap();
	}
	//将图片加载
	public void initBitmap(){
		iback = BitmapFactory.decodeResource(getResources(), R.drawable.main);
		iscore[0] = BitmapFactory.decodeResource(getResources(), R.drawable.d0);//数字图
		iscore[1] = BitmapFactory.decodeResource(getResources(), R.drawable.d1);
		iscore[2] = BitmapFactory.decodeResource(getResources(), R.drawable.d2);
		iscore[3] = BitmapFactory.decodeResource(getResources(), R.drawable.d3);
		iscore[4] = BitmapFactory.decodeResource(getResources(), R.drawable.d4);
		iscore[5] = BitmapFactory.decodeResource(getResources(), R.drawable.d5);
		iscore[6] = BitmapFactory.decodeResource(getResources(), R.drawable.d6);
		iscore[7] = BitmapFactory.decodeResource(getResources(), R.drawable.d7);
		iscore[8] = BitmapFactory.decodeResource(getResources(), R.drawable.d8);
		iscore[9] = BitmapFactory.decodeResource(getResources(), R.drawable.d9);
		
		guanShu[0] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka);//关卡图
		guanShu[1] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka1);
		guanShu[2] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka2);
		guanShu[3] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka3);
		guanShu[4] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka4);
		guanShu[5] = BitmapFactory.decodeResource(getResources(), R.drawable.guanka5);
		JiaHaotupian = BitmapFactory.decodeResource(getResources(), R.drawable.right);
		JianHaotupian = BitmapFactory.decodeResource(getResources(), R.drawable.left);
		gread_wz = BitmapFactory.decodeResource(getResources(), R.drawable.grade);//成绩文字
		time_wz= BitmapFactory.decodeResource(getResources(), R.drawable.time);//时间文字
		hengXian=BitmapFactory.decodeResource(getResources(), R.drawable.hengxian);//横线
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas); 
		canvas.drawColor(Color.argb(255, 0, 0, 0));
		canvas.drawBitmap(iback,30,0, null);//画背景
		//绘制减号和加号图片
		canvas.drawBitmap(JianHaotupian,SCREEN_WIDTH/6,SCREEN_HEIGHT/6+40, null);	
		//绘制关卡文字
		canvas.drawBitmap(guanShu[guanshu-1],SCREEN_WIDTH/2-60,SCREEN_HEIGHT/6+40, null);
		//绘制右边加号
		canvas.drawBitmap(JiaHaotupian,SCREEN_WIDTH/2+80,SCREEN_HEIGHT/6+40, null);
		//绘制成绩文字gread_wz
		canvas.drawBitmap(gread_wz,SCREEN_WIDTH/6,SCREEN_HEIGHT/6+63, null);
		//绘制游戏时间文字
		canvas.drawBitmap(time_wz,SCREEN_WIDTH/2+80,SCREEN_HEIGHT/6+63, null);
		String sql_select="select grade,time from rank where level="+guanshu+" order by grade desc limit 0,5;";
    	Vector<Vector<String>> vector=SQLiteUtil.query(sql_select);//从数据库中取出相应的数据
    	for(int i=0;i<vector.size();i++)//循环绘制排行榜的分数和对应时间
    	{
    		drawScoreStr(canvas,vector.get(i).get(0).toString(),SCREEN_WIDTH/6,SCREEN_HEIGHT/6+40+60+i*30);//成绩，日期
    		drawRiQi(canvas,vector.get(i).get(1).toString(),SCREEN_WIDTH/2+65,SCREEN_HEIGHT/6+40+60+i*30);
    	}
	}
	public void drawScoreStr(Canvas canvas,String s,int width,int height)//绘制字符串方法
	{
    	//绘制得分
    	String scoreStr=s; 
    	for(int i=0;i<scoreStr.length();i++){//循环绘制得分
    		int tempScore=scoreStr.charAt(i)-'0';
    		canvas.drawBitmap(iscore[tempScore], width+i*scoreWidth,height, null);
    		}
	}
	public void drawRiQi(Canvas canvas,String s,int width,int height)//画年月
	{
		String ss[]=s.split("-");//切割得到年月日
		drawScoreStr(canvas,ss[0],width,height);//画年数数字
		canvas.drawBitmap(hengXian,width+scoreWidth*4,height, null);//画横线
		drawScoreStr(canvas,ss[1],width+scoreWidth*5,height);//画月数数字
		canvas.drawBitmap(hengXian,width+scoreWidth*7,height, null);//画横线
		drawScoreStr(canvas,ss[2],width+scoreWidth*8,height);//画日数字
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(x>SCREEN_WIDTH/6&&x<SCREEN_WIDTH/6+60&&
				y>SCREEN_HEIGHT/6+40&&y<SCREEN_HEIGHT/6+40+40)
		{			
			if(guanshu>1)
			{
				guanshu--;
				c = null;
	            try {
	            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                c = holder.lockCanvas(null);
	                synchronized (holder) {
	                	onDraw(c);//绘制
	                }
	            } finally {
	                if (c != null) {
	                	//并释放锁
	                	holder.unlockCanvasAndPost(c);
	                }
	            }
			}
		}
		if(x>SCREEN_WIDTH/2+80&&x<SCREEN_WIDTH/2+140
				&&y>SCREEN_HEIGHT/6+40&&y<SCREEN_HEIGHT/6+80){			
			if(guanshu<MAPP.length)
			{
				guanshu++;
				c = null;
	            try {
	            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                c = holder.lockCanvas(null);
	                synchronized (holder) {
	                	onDraw(c);//绘制
	                }
	            } finally {
	                if (c != null) {
	                	//并释放锁
	                	holder.unlockCanvasAndPost(c);
	                }
	            }
			}
		}		
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
	public void surfaceCreated(SurfaceHolder holder) {//创建时启动相应进程		
		this.holder=holder;        
            c = null;
            try {
            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                c = holder.lockCanvas(null);
                synchronized (holder) {
                	onDraw(c);//绘制
                }
            } finally {
                if (c != null) {
                	//并释放锁
                	holder.unlockCanvasAndPost(c);
                }
            }
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//摧毁时释放相应进程
	}
}

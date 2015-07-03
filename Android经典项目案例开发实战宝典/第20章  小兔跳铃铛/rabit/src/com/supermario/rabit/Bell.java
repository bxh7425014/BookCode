package com.supermario.rabit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
//铃铛类
public class Bell {
	//铃铛的中心坐标
	private float center_x;
	private float center_y;
	//铃铛图片组
	private Bitmap[] bitmaps;
	//上下文
	private Context context;
	//铃铛的状态
	private int state;
	//画笔
	private Paint paint;
	//铃铛通常情况下的宽高
	public static final float BELL_OK_WIDTH = Constant.BELL_OK_WIDTH;
	public static final float BELL_OK_HEIGHT= Constant.BELL_OK_HEIGHT;
	//铃铛爆炸情况下的宽高
	public static final float BELL_EXPLODE_WIDTH = Constant.BELL_EXPLODE_WIDTH;
	public static final float BELL_EXPLODE_HEIGHT = Constant.BELL_EXPLODE_HEIGHT;
	// 铃铛的状态
	public static final int BELL_OK = 0;
	public static final int BELL_EXPLODE0 = 1;
	public static final int BELL_EXPLODE1 = 2;
	public static final int BELL_EXPLODE2 = 3;

	//构造函数，带铃铛坐标
	public Bell(Context context, float center_x, float center_y) {
		//初始化中心坐标
		this.center_x = center_x;
		this.center_y = center_y;
		//初始化上下文
		this.context = context;
		//初始化铃铛图片资源
		initBitmaps();
		//初始化铃铛状态
		state = BELL_OK;
		paint = new Paint();
	}
	//构造函数，不带铃铛坐标
	public Bell(Context context){
		this.context = context;
		initBitmaps();
		state = BELL_OK;
		paint = new Paint();
	}
	//初始化铃铛图片组
	private void initBitmaps() {
		bitmaps = new Bitmap[4];
		//正常图片
		bitmaps[0] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bell_ok);
		//爆炸图片
		bitmaps[1] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bell_explode0);
		bitmaps[2] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bell_explode1);
		bitmaps[3] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bell_explode2);
	}
	//绘制铃铛
	public void onDraw(Canvas canvas) {
		switch(state){
		//铃铛完好时
		case BELL_OK:
			canvas.drawBitmap(bitmaps[BELL_OK], center_x-BELL_OK_WIDTH/2, center_y-BELL_OK_HEIGHT/2, paint);
			break;
			//铃铛爆炸第一阶段
		case BELL_EXPLODE0:
			canvas.drawBitmap(bitmaps[BELL_EXPLODE0], center_x-BELL_EXPLODE_WIDTH/2, center_y-BELL_EXPLODE_HEIGHT/2, paint);
			break;
			//铃铛爆炸第二阶段
		case BELL_EXPLODE1:
			canvas.drawBitmap(bitmaps[BELL_EXPLODE1], center_x-BELL_EXPLODE_WIDTH/2, center_y-BELL_EXPLODE_HEIGHT/2, paint);
			break;
			//铃铛爆炸第三阶段
		case BELL_EXPLODE2:
			canvas.drawBitmap(bitmaps[BELL_EXPLODE2], center_x-BELL_EXPLODE_WIDTH/2, center_y-BELL_EXPLODE_HEIGHT/2, paint);
			break;
			//默认情况下铃铛完好
		default:
			canvas.drawBitmap(bitmaps[BELL_OK], center_x-BELL_OK_WIDTH/2, center_y-BELL_OK_HEIGHT/2, paint);
			break;	
		}
	}
	//铃铛是否ok
	public boolean isOK(){
		return state==BELL_OK;
	}
	//铃铛是否爆炸
	public boolean isExplode(){
		return !isOK();
	}
	//取得铃铛的中心x坐标
	public float getCenter_x() {
		return center_x;
	}
	//设置铃铛的中心x坐标
	public void setCenter_x(float centerX) {
		center_x = centerX;
	}
	//取得铃铛的中心Y坐标
	public float getCenter_y() {
		return center_y;
	}
	//设置铃铛的中心Y坐标
	public void setCenter_y(float centerY) {
		center_y = centerY;
	}
	//取得图片组
	public Bitmap[] getBitmaps() {
		return bitmaps;
	}
	//设置图片组
	public void setBitmaps(Bitmap[] bitmaps) {
		this.bitmaps = bitmaps;
	}
	//取得上下文
	public Context getContext() {
		return context;
	}
	//设置上下文
	public void setContext(Context context) {
		this.context = context;
	}
	//取得图片状态
	public int getState() {
		return state;
	}
	//设置图片状态
	public void setState(int state) {
		this.state = state;
	}
	//取得画笔
	public Paint getPaint() {
		return paint;
	}
	//设置画笔
	public void setPaint(Paint paint) {
		this.paint = paint;
	}	
}
package com.supermario.rabit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
//小鸟类
public class Bird {
	//上下文
	private Context context;
	//小鸟的速度
	private float speed = Constant.BIRD_SPEED;
	//小鸟比撞击之后的速度
	private float speed_power = Constant.BIRD_SPEED_POWER;
	//小鸟的中心坐标
	private float center_x;
	private float center_y;
	//小鸟是否出现在屏幕
	private boolean on_screen = false;
	//小鸟的状态
	private int state;
	//小鸟图片组
	private Bitmap[] bitmaps  = new Bitmap[8];
	//画笔
	private Paint paint = new Paint();
	//小鸟的高宽
	public static final float BIRD_WIDTH = Constant.BIRD_WIDTH;
	public static final float BIRD_HIGHT = Constant.BIRD_HIGHT;
	//小鸟的速度
	public static final float BIRD_SPEED = Constant.BIRD_SPEED;
	public static final float BIRD_SPEED_POWER = Constant.BIRD_SPEED_POWER;
	//小鸟的飞行状态，向左飞行
	public static final int BIRD_LEFT_FLY0 = 0;
	public static final int BIRD_LEFT_FLY1 = 1;
	public static final int BIRD_LEFT_FLY2 = 2;
	//向右飞行
	public static final int BIRD_RIGHT_FLY0 = 3;
	public static final int BIRD_RIGHT_FLY1 = 4;
	public static final int BIRD_RIGHT_FLY2 = 5;
	//向左加速飞行
	public static final int BIRD_LEFT_FLY_POWER = 6;
	//向右加速飞行
	public static final int BIRD_RIGHT_FLY_POWER = 7;
	//构造函数
	public Bird(Context context){
		this.context = context;
		//初始化小鸟的状态
		state = BIRD_LEFT_FLY0;
		//初始化图片组
		init_bitmaps();
	}
	//初始化图片组
	public void init_bitmaps(){
		//向左飞行
		bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_left_fly0);
		bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_left_fly1);
		bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_left_fly2);
		//向右飞行
		bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_right_fly0);
		bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_right_fly1);
		bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_right_fly2);
		//向左加速飞行
		bitmaps[6] = bitmaps[1];
		//向右加速飞行
		bitmaps[7] = bitmaps[4];
	}
	//绘制小鸟
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(bitmaps[state], center_x-BIRD_WIDTH/2, center_y-BIRD_HIGHT/2, paint);	
	}
	//是否被碰撞到
	public boolean isHited(){
		return (state == BIRD_LEFT_FLY_POWER || state == BIRD_RIGHT_FLY_POWER);
	}
	//是否面朝左
	public boolean isFaceLeft(){
		return (state == BIRD_LEFT_FLY0 || state == BIRD_LEFT_FLY1 || state == BIRD_LEFT_FLY2 || state == BIRD_LEFT_FLY_POWER);
	}
	//是否面朝右
	public boolean isFaceRight(){
		return !isFaceLeft();
	}
	//取得无毒
	public float getSpeed() {
		return speed;
	}
	//设置速度
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	//取得被撞击后的速度
	public float getSpeed_power() {
		return speed_power;
	}
	//获得被撞击后的速度
	public void setSpeed_power(int speedPower) {
		speed_power = speedPower;
	}
	//取得中心x坐标
	public float getCenter_x() {
		return center_x;
	}
	//设置中心x坐标
	public void setCenter_x(float centerX) {
		center_x = centerX;
	}
	//取得中心y坐标
	public float getCenter_y() {
		return center_y;
	}
	//设置中心y坐标
	public void setCenter_y(float centerY) {
		center_y = centerY;
	}
	//判断是否在屏幕
	public boolean isOn_screen() {
		return on_screen;
	}
	//设置是否在屏幕
	public void setOn_screen(boolean onScreen) {
		on_screen = onScreen;
	}
	//取得状态
	public int getState() {
		return state;
	}
	//设置状态
	public void setState(int state) {
		this.state = state;
	}
}
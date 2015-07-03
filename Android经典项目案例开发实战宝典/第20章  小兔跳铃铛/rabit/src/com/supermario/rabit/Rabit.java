package com.supermario.rabit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
//小兔类
public class Rabit {
	//上下文
	private Context context;
	//用户触摸点的x坐标
	private float x;
	//用户触摸点的y坐标
	private float y;
	//小兔的中心坐标
	private float center_x;
	private float center_y;
	//当前的小兔图像
	private Bitmap currentBitmap;
	//小兔所有图像数组
	private Bitmap[] allBitmaps;
	//向左的速度
	private float speed_x_left;
	//向右的速度
	private float speed_x_right;
	//向上的速度
	private float speed_y_up;
	//向下的速度
	private float speed_y_down;
	//小兔运动的目标x坐标
	private float x_destination;
	//图片的状态
	private int pic_state;
	//小兔的朝向
	private int face_state;
	//小兔在地面的状态
	private int ground_state;
	//小兔在空中的状态
	private int air_state;
	private Bitmap[] bitmaps;
	//小兔的宽高
	public static final float RABIT_WIDTH = Constant.RABIT_WIDTH;
	public static final float RABIT_HEIGHT = Constant.RABIT_HEIGHT;
	
	//rabit 图片的状态
	public static final int RABIT_PIC_LEFT_STOP = 0;
	public static final int RABIT_PIC_RIGHT_STOP = 1;
	//在地面向左跳跃
	public static final int RABIT_PIC_ON_GROUND_LEFT_JUMP0 = 2;
	public static final int RABIT_PIC_ON_GROUND_LEFT_JUMP1 = 3;
	//在地面向右跳跃
	public static final int RABIT_PIC_ON_GROUND_RIGHT_JUMP0 = 4;
	public static final int RABIT_PIC_ON_GROUND_RIGHT_JUMP1 = 5;
	//在空中向左跳跃
	public static final int RABIT_PIC_ON_AIR_LEFT_JUMP = 6;
	//在空中向右跳跃
	public static final int RABIT_PIC_ON_AIR_RIGHT_JUMP = 7;
	//在空中面向左停止
	public static final int RABIT_PIC_ON_AIR_LEFT_STOP = 8;
	//在空中面向右停止
	public static final int RABIT_PIC_ON_AIR_RIGHT_STOP = 9;
	//在空中向左下角运动
	public static final int RABIT_PIC_ON_AIR_LEFT_DOWN = 10;
	//在空中向右下角运动
	public static final int RABIT_PIC_ON_AIR_RIGHT_DOWN = 11;
	//rabit朝左朝右状态
	public static final int RABIT_FACE_LEFT = 1;
	public static final int RABIT_FACE_RIGHT = 2;
	//rabit在地面状态
	public static final int RABIT_NOT_ON_GROUND = 0;
	public static final int RABIT_LEFT_STOP = 1;
	public static final int RABIT_RIGHT_STOP = 2;
	public static final int RABIT_LEFT_MOVE1_ON_GROUND = 3;
	public static final int RABIT_LEFT_MOVE2_ON_GROUND = 4;
	public static final int RABIT_RIGHT_MOVE1_ON_GROUND = 5;
	public static final int RABIT_RIGHT_MOVE2_ON_GROUND = 6;
	//rabit 在空中的状态 
	public static final int RABIT_ON_AIR_UP0 = 0;
	public static final int RABIT_ON_AIR_UP1 = 1;
	public static final int RABIT_ON_AIR_UP2 = 2;
	public static final int RABIT_ON_AIR_UP3 = 3;
	public static final int RABIT_ON_AIR_UP4 = 4;
	public static final int RABIT_ON_AIR_UP5 = 5;
	public static final int RABIT_ON_AIR_STOP = 6;
	public static final int RABIT_ON_AIR_DOWN = 7;
	
	//rabit 每次跳跃刷新的高度是不同的
	public static final float RABIT_UP_DESTIATON0 = 30;
	public static final float RABIT_UP_DESTIATON1 = 20;
	public static final float RABIT_UP_DESTIATON2 = 10;
	//rabit 每次下降刷新的距离是不同的
	public static final float RABIT_DOWN_DESTIATON0 = 10;
	public static final float RABIT_DOWN_DESTIATON1 = 20;
	public static final float RABIT_DWON_DESTIATON2 = 30;
	public static final float SPEED_X = 10;
	public static final float SPEED_Y = 15;
	public static final float SPEED_X_ON_AIR = 15;
	private Paint paint;
	
	public Rabit(Context context){
		this.context = context;
		//初始化图片资源
		initBitmaps();
		//设置小兔的初始位置
		this.setX(Constant.RABIT_INIT_X);
		this.setY(Constant.RABIT_INIT_Y);
		x_destination = x;
		//初始化速度
		speed_x_left = 0;
		speed_x_right = 0;
		speed_y_down = 15;
		speed_y_up = 15;
		paint = new Paint();
		//初始化小兔的状态
		ground_state = RABIT_RIGHT_STOP;
		air_state = RABIT_ON_AIR_UP0;
		face_state = RABIT_FACE_RIGHT;
		this.pic_state = RABIT_PIC_RIGHT_STOP;

	}
	//初始化函数
	public void init(){
		//初始化小兔的坐标和运动状态
		this.setY(Constant.RABIT_INIT_Y);
		ground_state = RABIT_RIGHT_STOP;
		air_state = RABIT_ON_AIR_UP0;
		face_state = RABIT_FACE_RIGHT;
		this.pic_state = RABIT_PIC_RIGHT_STOP;
	}
	//初始化图片资源
	private void initBitmaps(){
		bitmaps = new Bitmap[12];
		bitmaps[Rabit.RABIT_PIC_LEFT_STOP] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_left_stop);
		bitmaps[Rabit.RABIT_PIC_RIGHT_STOP] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_right_stop);
		bitmaps[RABIT_PIC_ON_GROUND_LEFT_JUMP0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_ground_left_jump0);
		bitmaps[RABIT_PIC_ON_GROUND_LEFT_JUMP1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_ground_left_jump1);
		bitmaps[RABIT_PIC_ON_GROUND_RIGHT_JUMP0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_ground_right_jump0);
		bitmaps[RABIT_PIC_ON_GROUND_RIGHT_JUMP1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_ground_right_jump1);
		bitmaps[RABIT_PIC_ON_AIR_LEFT_JUMP] = bitmaps[RABIT_PIC_ON_GROUND_LEFT_JUMP0];
		bitmaps[RABIT_PIC_ON_AIR_RIGHT_JUMP] = bitmaps[RABIT_PIC_ON_GROUND_RIGHT_JUMP0];
		bitmaps[RABIT_PIC_ON_AIR_LEFT_DOWN] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_air_left_down);
		bitmaps[RABIT_PIC_ON_AIR_RIGHT_DOWN] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_air_right_down);;
		bitmaps[RABIT_PIC_ON_AIR_LEFT_STOP] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_air_left_stop);;
		bitmaps[RABIT_PIC_ON_AIR_RIGHT_STOP] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rabit_on_air_right_stop);;
	}
	//绘制图片
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(bitmaps[pic_state], x, y, paint);
	}
	//判断是否撞到铃铛
	public boolean isHitBell(Bell bell){
		if(bell.isExplode()) return false;
		//获得小兔的中心坐标
		float x1 = center_x;
		float y1 = center_y;
		//获得铃铛的中心坐标
		float x2 = bell.getCenter_x();
		float y2 = bell.getCenter_y();
		//判断两个中心坐标的距离小于20则表明撞到
		return Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2) < 400;
	}
	//判断是否撞到铃铛
	public boolean isHitBird(Bird bird){
		//获得小兔的中心坐标
		float x1 = center_x;
		float y1 = center_y;
		//获得小鸟的中心坐标
		float x2 = bird.getCenter_x();
		float y2 = bird.getCenter_y();
		//判断两个中心坐标的距离小于20则表明撞到
		return Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2) < 400;
	}
	//判断小兔是否在地面
	public boolean isOnGround(){
		return !(ground_state==RABIT_NOT_ON_GROUND);
	}
	//判断小兔是否在空中
	public boolean isOnAir(){
		return !isOnGround();
	}
	//判断小兔是否停在地面
	public boolean isOnGroundStop(){
		return ground_state==RABIT_LEFT_STOP||ground_state==RABIT_RIGHT_STOP;
	}
	//判断小兔是否面朝左
	public boolean isFaceLeft(){
		return face_state==RABIT_FACE_LEFT;
	}
	//判断小兔是否面朝右
	public boolean isFaceRight(){
		return !isFaceLeft();
	}
	//取得上下文
	public Context getContext() {
		return context;
	}
	//设置上下文
	public void setContext(Context context) {
		this.context = context;
	}
	//取得用户触摸点的X坐标
	public float getX() {
		return x;
	}
	//设置用户触摸点的X坐标
	public void setX(float x) {
		this.x = x;
		this.center_x = x + RABIT_WIDTH/2;
	}
	//取得用户触摸点的Y坐标
	public float getY() {
		return y;
	}
	//设置用户触摸点的Y坐标
	public void setY(float y) {
		this.y = y;
		this.center_y = y + RABIT_HEIGHT/2;
	}
	//取得当前小兔的图片
	public Bitmap getCurrentBitmap() {
		return currentBitmap;
	}
	//设置当前小兔的图片
	public void setCurrentBitmap(Bitmap currentBitmap) {
		this.currentBitmap = currentBitmap;
	}
	//取得图片组
	public Bitmap[] getAllBitmaps() {
		return allBitmaps;
	}
	//设置图片组
	public void setAllBitmaps(Bitmap[] allBitmaps) {
		this.allBitmaps = allBitmaps;
	}
	//取得向左的速度
	public float getSpeed_x_left() {
		return speed_x_left;
	}
	//设置向左的速度
	public void setSpeed_x_left(float speedXLeft) {
		speed_x_left = speedXLeft;
	}
	//取得向右的速度
	public float getSpeed_x_right() {
		return speed_x_right;
	}
	//设置向右的速度
	public void setSpeed_x_right(float speedXRight) {
		speed_x_right = speedXRight;
	}
	//取得向上的速度
	public float getSpeed_y_up() {
		return speed_y_up;
	}
	//设置向上的速度
	public void setSpeed_y_up(float speedYUp) {
		speed_y_up = speedYUp;
	}
	//取得向下的速度
	public float getSpeed_y_down() {
		return speed_y_down;
	}
	//设置向下的速度
	public void setSpeed_y_down(float speedYDown) {
		speed_y_down = speedYDown;
	}
	//取得图片集
	public Bitmap[] getBitmaps() {
		return bitmaps;
	}
	//设置图片集
	public void setBitmaps(Bitmap[] bitmaps) {
		this.bitmaps = bitmaps;
	}
	//取得画笔
	public Paint getPaint() {
		return paint;
	}
	//设置画笔
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	//取得x轴终点位置
	public float getX_destination() {
		return x_destination;
	}
	//设置x轴终点位置
	public void setX_destination(float xDestination) {
		x_destination = xDestination;
	}
	//取得朝向
	public int getFace_state() {
		return face_state;
	}
	//获得朝向
	public void setFace_state(int faceState) {
		face_state = faceState;
	}
	//取得地面状态
	public int getGround_state() {
		return ground_state;
	}
	//设置地面状态
	public void setGround_state(int groundState) {
		ground_state = groundState;
	}
	//取得空中状态
	public int getAir_state() {
		return air_state;
	}
	//取得空中状态
	public void setAir_state(int airState) {
		air_state = airState;
	}
	//取得图片状态
	public int getPic_state() {
		return pic_state;
	}
	//设置图片状态
	public void setPic_state(int picState) {
		pic_state = picState;
	}
	//取得小兔的中心x坐标
	public float getCenter_x() {
		return center_x;
	}
	//设置小兔中心x坐标
	public void setCenter_x(float centerX) {
		center_x = centerX;
		this.x = centerX - RABIT_WIDTH/2;
	}
	//取得小兔中心Y坐标
	public float getCenter_y() {
		return center_y;
	}
	//设置小兔中心Y坐标
	public void setCenter_y(float centerY) {
		center_y = centerY;
		y = centerY - RABIT_HEIGHT/2;
	}	
}
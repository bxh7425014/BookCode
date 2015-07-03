package com.supermario.rabit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
//背景图片设置类
public class Backgroud {
	private Context context;
	//背景图片
	private Bitmap bg;
	//画笔
	private Paint paint;
	private int my_y;
	//构造函数
	public Backgroud(Context context){
		this.context = context;
		paint = new Paint();
		//初始化图片资源
		bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
		//小兔的高度
		my_y  = 0;
	}
	//绘制背景
	public void onDraw(Canvas canvas){
		if(my_y >= 0 && my_y <= 480){
			//图片的高度为720，界面高度为240，
			//这样可以让图片底部刚好与界面底部平齐
			int screen_y = my_y - 480;
			canvas.drawBitmap(bg, 0, screen_y, paint);
		}else{
			//当高度超过480
			int y_up = my_y % 480;
			int y_down = (my_y-240) % 480;
			if(y_up>=y_down){
				int screen_y = y_up - 480;
				//绘制一张背景图
				canvas.drawBitmap(bg, 0, screen_y, paint);
			}else{
				//绘制两张背景图进行重叠
				int screen_y1 = y_up - 480;
				int screent_y2 = y_up;
				canvas.drawBitmap(bg, 0, screen_y1, paint);
				canvas.drawBitmap(bg, 0, screent_y2, paint);
			}
		}
	}
	//初始化小兔高度
	public void init(){
		this.my_y = 0;
	}
	//背景往下拉
	public void drag_down(int px){
		this.my_y += px;
	}
	//背景往上拉
	public void drag_up(int px){
		//为了下降的落地的时间不至于过长，剪掉了兔子上升的背景距离
		if(my_y > 960){
			my_y = (my_y - 480) % 480 + 480;
		}
		this.my_y -= px;
	}
	//判断小兔是否掉落到最低点
	public boolean isLowest(){
		return (my_y >= -5 && my_y <= 10);
	}
	//取得上下文
	public Context getContext() {
		return context;
	}
	//设置上下文
	public void setContext(Context context) {
		this.context = context;
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
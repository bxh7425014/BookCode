package com.supermario.rabit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
//游戏帮助界面
public class HelpView extends View{
	private Bitmap bg;
	private Paint paint;
	//初始化画笔，获得图片资源
	public HelpView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.help);
	}
	//在画布上绘制图片
	protected void onDraw(Canvas canvas){
		canvas.drawBitmap(bg, 0, 0, paint);
	}
}
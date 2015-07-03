package com.supermario.rabit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
//游戏开场画面
public class IntroduceView extends View{
	private Bitmap bg;
	private Paint paint;
	//新建画笔，获得图片
	public IntroduceView(Context context) {
		super(context);
		paint = new Paint();
		bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.introduce);
	}
	//将图片绘制到画布上
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bg, 0, 0, paint);
	}	
}

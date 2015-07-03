package com.supermario.rabit;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//游戏结束界面
public class Conclusion {
	//是否按下按钮
	private boolean pressed = false;
	private GameSurfaceView context;
	//背景图片
	private Bitmap bg;
	//答复按钮
	private Bitmap replay_button;
	private Paint paint = new Paint();

	public Conclusion(GameSurfaceView context) {
		this.context = context;
		//获得图片资源
		this.bg = BitmapFactory.decodeResource(context.getContext()
				.getResources(), R.drawable.conclusion);
		//获得按钮图片
		replay_button = BitmapFactory.decodeResource(context.getContext()
				.getResources(), R.drawable.replay_button);
	}
	//绘制图片
	public void onDraw(Canvas canvas) {
		if (context.getHighest_score() > context.getScore()) {
			//绘制背景图片
			canvas.drawBitmap(bg, 95, 0, paint);
			//设置字体颜色为白色
			paint.setColor(Color.WHITE);
			paint.setTextSize(17);
			canvas.drawText("SCORE", 175, 51, paint);
			//分数以红色字体标出
			paint.setColor(Color.RED);
			canvas.drawText("" + context.getScore(), 180, 77, paint);
			paint.setColor(Color.WHITE);
			//最高分
			canvas.drawText("HIGHEST SCORE", 148, 105, paint);
			//黄色字体标出最高分
			paint.setColor(Color.YELLOW);
			canvas.drawText(""+context.getHighest_score(), 175, 138, paint);

			if (pressed) {
				canvas.drawBitmap(replay_button, 164, 156, paint);
			}
		} else {
			canvas.drawBitmap(bg, 95, 0, paint);
			paint.setColor(Color.RED);
			paint.setTextSize(20);
			//使用红色字体显示
			canvas.drawText("CONGRATULATIONS!", 100, 70, paint);
			paint.setColor(Color.WHITE);
			paint.setTextSize(17);
			//破纪录时用白色字体显示最高分
			canvas.drawText("New Hightest Score:", 125, 115, paint);
			canvas.drawText(""+context.getScore(), 180, 147, paint);
		}
	}
	//初始化点击标志位
	public void init() {
		pressed = false;
	}
	//获得是否已点击
	public boolean isPressed() {
		return pressed;
	}
	//设置是否已点击
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
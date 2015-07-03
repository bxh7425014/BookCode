package com.supermario.rabit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
//声音设置界面
public class AudioView extends View {
	//画笔
	Paint paint;
	//默认声音关闭
	boolean audio_on = false;
	//默认未点击
	boolean click = false;
	public AudioView(Context context) {
		super(context);
		paint = new Paint();
	}
	//绘制图片
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//将画布背景设为黑色
		canvas.drawColor(Color.BLACK);
		paint.setTextSize(20);
		//字体颜色为白色
		paint.setColor(Color.WHITE);
		//绘制文字
		canvas.drawText("是否打开音效?", 125, 85, paint);
		if(click){
			if(audio_on){
				//若打开声音则将“是”设为红色
				paint.setColor(Color.RED);
				canvas.drawText("是", 33, 197, paint);
				paint.setColor(Color.WHITE);
				canvas.drawText("否", 336, 197, paint);
			}else{
				//若关闭声音则将“否”设为红色
				paint.setColor(Color.WHITE);
				canvas.drawText("是", 33, 197, paint);
				paint.setColor(Color.RED);
				canvas.drawText("否", 336, 197, paint);
			}
		}else {
				//未点击则将两者都设为白色
				paint.setColor(Color.WHITE);
				canvas.drawText("是", 33, 197, paint);
				canvas.drawText("否", 336, 197, paint);
		}
	}
	//返回声音是否打开
	public boolean isAudio_on() {
		return audio_on;
	}
	//设置声音是否打开
	public void setAudio_on(boolean audioOn) {
		audio_on = audioOn;
	}
	//返回是否点击到
	public boolean isClick() {
		return click;
	}
	//设置是否点击到
	public void setClick(boolean click) {
		this.click = click;
	}
}
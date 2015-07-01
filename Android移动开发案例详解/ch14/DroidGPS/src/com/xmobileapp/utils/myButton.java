package com.xmobileapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.Button;

public class myButton extends Button{
	private Bitmap mBmp_back ;
	public myButton(Context context,Bitmap bmp) {
		super(context);
		mBmp_back = bmp;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		int bWidth = this.getWidth();
		int bHeight = this.getHeight();
		/*在Button中间的位置显示图片*/
		canvas.drawBitmap(mBmp_back, bWidth/2-mBmp_back.getWidth()/2, bHeight/2-mBmp_back.getHeight()/2, paint);
		super.onDraw(canvas);
	}
	public void setBackBitmap(Bitmap bmp)
	{
		mBmp_back = bmp;
		this.invalidate();
	}
}

package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Path.Direction;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	private static class MyView extends View
	{
		private Paint paint;
		private Path[] paths = new Path[3];
		private Paint pathPaint;
		private void makePath(Path p, int style)
		{
			p.moveTo(10, 0);
			switch (style)
			{
				case 1:
					p.cubicTo(100, -50, 200, 50, 300, 0);	
					break;
				case 2:
					p.addCircle(100,100, 100, Direction.CW);
					break;
				case 3:
					RectF rectF = new RectF();
					rectF.left = 0;
					rectF.top=0;
					rectF.right = 200;
					rectF.bottom = 100;
					p.addArc(rectF, 0, 360);
					break;
			}
		}



		public MyView(Context context)
		{
			super(context);
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			paint.setTypeface(Typeface.SERIF);


			paths[0] = new Path();
			paths[1] = new Path();
			paths[2] = new Path();
			makePath(paths[0], 1);
			makePath(paths[1], 2);
			makePath(paths[2], 3);

			pathPaint = new Paint();
			pathPaint.setAntiAlias(true);
			pathPaint.setColor(0x800000FF);
			pathPaint.setStyle(Paint.Style.STROKE);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);

		
			canvas.translate(0, 50);
			canvas.drawPath(paths[0], pathPaint);
			paint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawTextOnPath("Android/Ophone开发讲义", paths[0], 0,0, paint);

			canvas.translate(-20, 80);
			paint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawTextOnPath("Android/Ophone开发讲义", paths[0], 0,0, paint);

			canvas.translate(50, 50);
			paint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawTextOnPath("Android/Ophone开发讲义", paths[1], -30,0, paint);

			canvas.translate(0, 100);
			paint.setTextAlign(Paint.Align.RIGHT);
			canvas.drawPath(paths[2], pathPaint);
			canvas.drawTextOnPath("Android/Ophone开发讲义", paths[2], 0,0, paint);
			
		}

	}
}
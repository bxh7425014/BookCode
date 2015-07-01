package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
		private Paint[] paints;
		private Paint framePaint;
		private boolean[] useCenters;
		private RectF[] ovals;
		private RectF bigOval;
		private float start;
		private float sweep;
		private int bigIndex;

		private static final float SWEEP_INC = 2;
		private static final float START_INC = 15;

		public MyView(Context context)
		{
			super(context);

			paints = new Paint[4];
			useCenters = new boolean[4];
			ovals = new RectF[4];

			paints[0] = new Paint();			
			paints[0].setStyle(Paint.Style.FILL);
			paints[0].setColor(Color.BLUE);
			useCenters[0] = false;

			paints[1] = new Paint(paints[0]);
			paints[1].setColor(Color.RED);
			useCenters[1] = true;

			paints[2] = new Paint(paints[0]);
			paints[2].setStyle(Paint.Style.STROKE);
			paints[2].setStrokeWidth(4);
			paints[2].setColor(Color.CYAN);
			useCenters[2] = false;

			paints[3] = new Paint(paints[2]);
			paints[3].setColor(Color.BLACK);
			useCenters[3] = true;

			bigOval = new RectF(40, 10, 280, 250);

			ovals[0] = new RectF(10, 270, 70, 330);
			ovals[1] = new RectF(90, 270, 150, 330);
			ovals[2] = new RectF(170, 270, 230, 330);
			ovals[3] = new RectF(250, 270, 310, 330);

			framePaint = new Paint();
			framePaint.setStyle(Paint.Style.STROKE);
			framePaint.setStrokeWidth(0);
		}

		private void drawArcs(Canvas canvas, RectF oval, boolean useCenter,
				Paint paint)
		{
			canvas.drawRect(oval, framePaint);
			canvas.drawArc(oval, start, sweep, useCenter, paint);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);

			drawArcs(canvas, bigOval, useCenters[bigIndex],
					paints[bigIndex]);

			for (int i = 0; i < 4; i++)
			{
				drawArcs(canvas, ovals[i], useCenters[i], paints[i]);
			}

			sweep += SWEEP_INC;
			if (sweep > 360)
			{
				sweep -= 360;
				start += START_INC;
				if (start >= 360)
				{
					start -= 360;
				}
				bigIndex = (bigIndex + 1) % paints.length;
			}
			invalidate();
		}
	}
}
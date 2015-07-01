package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class Main extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	private class MyView extends View
	{
		private Paint paint;
		private Path path;
		private PathEffect[] effects;
		private int[] colors;
		private float phase;

		private void makeEffects(PathEffect[] e, float phase)
		{
			e[0] = null; // 没有效果
			e[1] = new CornerPathEffect(10);
			e[2] = new DashPathEffect(new float[]
			{ 20, 10, 5, 10 }, phase);
			e[3] = new PathDashPathEffect(makeCirclePath(), 12, phase,
					PathDashPathEffect.Style.ROTATE);

			e[4] = new ComposePathEffect(e[2], e[1]);
			e[5] = new ComposePathEffect(e[3], e[1]);
		}

		public MyView(Context context)
		{
			super(context);



			paint = new Paint();
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(5);

			path = makeFollowPath();

			effects = new PathEffect[6];

			colors = new int[]
			{ Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA,
					Color.BLACK };
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);

			RectF bounds = new RectF();

			canvas.translate(10 - bounds.left, 10 - bounds.top);

			makeEffects(effects, phase);
			phase += 1;
			invalidate();

			for (int i = 0; i < effects.length; i++)
			{
				paint.setPathEffect(effects[i]);
				paint.setColor(colors[i]);				
				canvas.drawPath(path, paint);
				canvas.translate(0, 70);
			}
		}

		private Path makeFollowPath()
		{
			Path p = new Path();
			p.moveTo(0, 0);
			for (int i = 1; i <= 15; i++)
			{
				p.lineTo(i * 20, (float) Math.random() * 75);
			}
			return p;
		}

		private Path makeCirclePath()
		{
			Path p = new Path();
			p.addCircle(0, 0, 5, Direction.CCW);

			return p;
		}

	}

}
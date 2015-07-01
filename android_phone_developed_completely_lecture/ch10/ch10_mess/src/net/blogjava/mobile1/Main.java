package net.blogjava.mobile1;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;

public class Main extends Activity
{

	private static Bitmap bitmap;
	private MyView myView;
	private int angle = 0;

	private Handler handler = new Handler()
	{

		public void handleMessage(Message msg)
		{

			switch (msg.what)
			{
				case 1:

					Random random = new Random();
					int centerX = bitmap.getWidth() / 2;
					int centerY = bitmap.getHeight() / 2;
					double radian = Math.toRadians((double) angle);
					int currentX = (int) (centerX + 100 * Math.cos(radian));
					int currentY = (int) (centerY + 100 * Math.sin(radian));
					myView.mess(currentX, currentY);
					angle += 2;
					if (angle > 360)
						angle = 0;
					Log.d("a", String.valueOf(currentY));

					break;
			}
			super.handleMessage(msg);
		}

	};
	private TimerTask timerTask = new TimerTask()
	{

		public void run()
		{
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		myView = new MyView(this);
		setContentView(myView);

		Timer timer = new Timer();
		timer.schedule(timerTask, 0, 100);

	}

	private static class MyView extends View
	{
		private static final int WIDTH = 20;
		private static final int HEIGHT = 20;
		private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);

		private final float[] verts = new float[COUNT * 2];
		private final float[] orig = new float[COUNT * 2];

		private final Matrix matrix = new Matrix();
		private final Matrix m = new Matrix();

		private static void setXY(float[] array, int index, float x, float y)
		{
			array[index * 2 + 0] = x;
			array[index * 2 + 1] = y;
		}

		public MyView(Context context)
		{
			super(context);
			setFocusable(true);

			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.image);

			float w = bitmap.getWidth();
			float h = bitmap.getHeight();
			int index = 0;
			for (int y = 0; y <= HEIGHT; y++)
			{
				float fy = h * y / HEIGHT;
				for (int x = 0; x <= WIDTH; x++)
				{
					float fx = w * x / WIDTH;
					setXY(verts, index, fx, fy);
					setXY(orig, index, fx, fy);
					index += 1;
				}
			}

			matrix.setTranslate(10, 10);
			setBackgroundColor(Color.WHITE);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.concat(matrix);
			canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0,
					null);
		}

		private void warp(float cx, float cy)
		{
			final float K = 100000;
			float[] src = orig;
			float[] dst = verts;
			for (int i = 0; i < COUNT * 2; i += 2)
			{
				float x = src[i + 0];
				float y = src[i + 1];
				float dx = cx - x;
				float dy = cy - y;
				float dd = dx * dx + dy * dy;
				float d = FloatMath.sqrt(dd);
				float pull = K / ((float) (dd *d));

				if (pull >= 1)
				{
					dst[i + 0] = cx;
					dst[i + 1] = cy;
				}
				else
				{
					dst[i + 0] = x + dx * pull;
					dst[i + 1] = y + dy * pull;
				}

			}
		}

		public void mess(int x, int y)
		{
			float[] pt =
			{ x, y };
			m.mapPoints(pt);

			warp(pt[0], pt[1]);
			invalidate();

		}

	}
}

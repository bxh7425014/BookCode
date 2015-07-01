package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.MotionEvent;
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
		private final Paint paint = new Paint();
		private final float[] verts = new float[10];
		private final float[] texs = new float[10];
		private final int[] colors = new int[10];
		private final short[] indices =
		{ 0, 1, 2, 3, 4, 1 };

		private final Matrix matrix = new Matrix();
		private final Matrix inverse = new Matrix();

		private static void setXY(float[] array, int index, float x, float y)
		{
			array[index * 2 + 0] = x;
			array[index * 2 + 1] = y;
		}

		public MyView(Context context)
		{
			super(context);
			Bitmap bm = BitmapFactory.decodeResource(getResources(),
					R.drawable.image);
			Shader s = new BitmapShader(bm, Shader.TileMode.CLAMP,
					Shader.TileMode.CLAMP);
			paint.setShader(s);

			float w = bm.getWidth();
			float h = bm.getHeight();

			setXY(texs, 0, w / 2, h / 2);
			setXY(texs, 1, 0, 0);
			setXY(texs, 2, w, 0);
			setXY(texs, 3, w, h);
			setXY(texs, 4, 0, h);

			setXY(verts, 0, w / 2, h / 2);
			setXY(verts, 1, 0,0);
			setXY(verts, 2, w, 0);
			setXY(verts, 3, w, h);
			setXY(verts, 4, 0, h);

			matrix.setScale(0.8f, 0.8f);
			matrix.preTranslate(20, 20);
			matrix.invert(inverse);
			setBackgroundColor(Color.WHITE);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.save();
			canvas.concat(matrix);
			canvas.translate(10,10);
			canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, verts, 0,
					null, 0, null, 0, indices, 0, 6, paint);

			canvas.translate(10,240);
			canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, verts, 0,
					texs, 0, null, 0, indices, 0, 6, paint);
			
			canvas.restore();
		}

		@Override 
		public boolean onTouchEvent(MotionEvent event)
		{
			float[] pt =
			{ event.getX(), event.getY() };
			inverse.mapPoints(pt);
			setXY(verts, 0, pt[0], pt[1]);
			invalidate();
			return true;
		}

	}

}
package net.blogjava.mobile;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity
{
	class MyView extends View
	{
		private Bitmap bitmap1;
		private Bitmap bitmap2;
		private int digree1 = 0;
		private int digree2 = 360;

		public MyView(Context context)
		{
			super(context);
			setBackgroundColor(Color.WHITE);
			InputStream is = getResources().openRawResource(R.drawable.cross);
			bitmap1 = BitmapFactory.decodeStream(is);
			is = getResources().openRawResource(R.drawable.ball);
			bitmap2 = BitmapFactory.decodeStream(is);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			Matrix matrix = new Matrix();
			if (digree1 > 360)
				digree1 = 0;
			if(digree2 < 0)
				digree2 = 360;
			
			matrix.setRotate(digree1++, 160, 240);						
			canvas.setMatrix(matrix);
			canvas.drawBitmap(bitmap1, 88, 169, null);
			matrix.setRotate(digree2--,160 , 240);
			canvas.setMatrix(matrix);
			canvas.drawBitmap(bitmap2, 35, 115, null);
			invalidate();

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}
}
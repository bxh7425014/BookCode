package net.blogjava.mobile1;

import java.io.FileOutputStream;
import java.io.InputStream;

import net.blogjava.mobile1.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class Main extends Activity implements OnColorChangedListener
{
	private MyView myView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        myView = new MyView(this);
		setContentView(myView);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(0xFFFF0000);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(6);

		emboss = new EmbossMaskFilter(new float[]
		{ 1, 1, 1 }, 0.4f, 6, 3.5f);

		blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}

	private Paint paint;
	private MaskFilter emboss;
	private MaskFilter blur;

	public void colorChanged(int color)
	{
		paint.setColor(color);
	}

	public class MyView extends View
	{

		private Bitmap bitmap1;
		private Bitmap bitmap2;
		private Canvas canvas;
		private Path path;
		private Paint bitmapPaint;		
        public void loadBitmap()
        {
			try
			{
				InputStream is = getResources().openRawResource(
						R.drawable.image);
				bitmap2 = BitmapFactory.decodeStream(is);
				bitmap1 = Bitmap
						.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
				canvas = new Canvas(bitmap1);
				canvas.drawBitmap(bitmap2, 0, 0, null);

			}
			catch (Exception e)
			{

			}
        }
		public MyView(Context c)
		{
			super(c);

			loadBitmap();
			bitmapPaint = new Paint(Paint.DITHER_FLAG);
			path = new Path();


		}
        public void clear()
        {        	
        	loadBitmap();
        	invalidate();
        }
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawBitmap(bitmap1, 0, 0, bitmapPaint);
			canvas.drawPath(path, paint);
		}

		private float mX, mY;

		private void touch_start(float x, float y)
		{

			path.moveTo(x, y);

			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y)
		{

			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);

			path.quadTo(mX, mY, x, y);
			mX = x;
			mY = y;

		}

		private void touch_up()
		{
		
			canvas.drawPath(path, paint);
			path.reset();
			try
			{
				FileOutputStream fos = new FileOutputStream("/sdcard/image.png");
				bitmap1.compress(CompressFormat.PNG, 100, fos);
				fos.close();
			}
			catch (Exception e)
			{

			}

		}

		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					touch_start(x, y);
					invalidate();
					break;
				case MotionEvent.ACTION_MOVE:
					touch_move(x, y);
					invalidate();
					break;
				case MotionEvent.ACTION_UP:
					touch_up();
					invalidate();
					break;
			}
			return true;
		}
	}

	private final int COLOR_MENU_ID = Menu.FIRST;
	private final int EMBOSS_MENU_ID = Menu.FIRST + 1;
	private final int BLUR_MENU_ID = Menu.FIRST + 2;
	private final int CLEAR_MENU_ID = Menu.FIRST + 3;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);

		menu.add(0, COLOR_MENU_ID, 0, "设置颜色");
		menu.add(0, EMBOSS_MENU_ID, 0, "浮雕效果");
		menu.add(0, BLUR_MENU_ID, 0, "喷涂效果");
		menu.add(0, CLEAR_MENU_ID, 0, "清除图形");
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case COLOR_MENU_ID:
				new ColorPickerDialog(this, this, paint.getColor()).show();
				return true;
			case EMBOSS_MENU_ID:
				if (paint.getMaskFilter() != emboss)
				{
					paint.setMaskFilter(emboss);
				}
				else
				{
					paint.setMaskFilter(null);
				}
				return true;
			case BLUR_MENU_ID:
				if (paint.getMaskFilter() != blur)
				{
					paint.setMaskFilter(blur);
				}
				else
				{
					paint.setMaskFilter(null);
				}
				return true;
			case CLEAR_MENU_ID:
				myView.clear();
				
				
				return true;

		}
		return super.onOptionsItemSelected(item);
	}
}
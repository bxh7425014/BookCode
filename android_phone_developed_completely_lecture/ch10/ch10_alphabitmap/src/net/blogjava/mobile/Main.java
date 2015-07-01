package net.blogjava.mobile;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main extends Activity implements OnSeekBarChangeListener
{
	public static int alpha = 100;
	private MyView myView;

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		alpha = progress;
		setTitle("alpha:" + progress);
		myView.invalidate();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		myView = new MyView(this);
		myView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 260));

		SeekBar seekBar = new SeekBar(this);
		seekBar.setMax(255);
		seekBar.setProgress(alpha);
		seekBar.setOnSeekBarChangeListener(this);
		linearLayout.addView(myView);
		linearLayout.addView(seekBar);
		linearLayout.setBackgroundColor(Color.WHITE);

		setContentView(linearLayout);
		setTitle("alpha:" + alpha);
	}

	private class MyView extends View
	{

		private Bitmap bitmap;

		public MyView(Context context)
		{
			super(context);
			InputStream is = getResources().openRawResource(R.drawable.image);
			bitmap = BitmapFactory.decodeStream(is);
			setBackgroundColor(Color.WHITE);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			Paint paint = new Paint();
			paint.setAlpha(alpha);

			canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap
					.getHeight()), new Rect(10, 10, 310, 235), paint);

		}
	}
}
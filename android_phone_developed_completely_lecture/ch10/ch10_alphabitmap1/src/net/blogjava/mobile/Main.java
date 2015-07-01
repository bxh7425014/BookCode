package net.blogjava.mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

	private static class MyView extends View
	{

		private Bitmap bitmap;
		private Shader shader;

		private static void drawIntoBitmap(Bitmap bm)
		{
			float x = bm.getWidth();
			float y = bm.getHeight();
			Canvas c = new Canvas(bm);
			Paint p = new Paint();

			p.setAlpha(Main.alpha);
			c.drawCircle(x / 2, y / 2, x / 2, p);

			p.setAlpha(60);
			p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
			p.setTextSize(60);
			p.setTextAlign(Paint.Align.CENTER);
			Paint.FontMetrics fm = p.getFontMetrics();

			c.drawText("Alpha", x / 2, (y - fm.ascent) / 2, p);
		}

		public MyView(Context context)
		{
			super(context);	
			shader = new LinearGradient(0, 0, 100, 70, new int[]
			{ Color.GREEN, Color.RED, Color.BLUE }, null,
					Shader.TileMode.MIRROR);
		}

		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);
			

			Paint p = new Paint();
			float y = 10;
			p.setShader(shader);
			bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
			drawIntoBitmap(bitmap);
			canvas.drawBitmap(bitmap, 10, y, p);
		}
	}
}
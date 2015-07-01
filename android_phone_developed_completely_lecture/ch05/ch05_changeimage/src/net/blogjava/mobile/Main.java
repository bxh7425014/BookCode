package net.blogjava.mobile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main extends Activity implements OnSeekBarChangeListener
{
	private int minWidth = 80;
	private ImageView imageView;
	private TextView textView1, textView2;
	private Matrix matrix = new Matrix();

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		if (seekBar.getId() == R.id.seekBar1)
		{
			int newWidth = progress + minWidth;
			int newHeight = (int) (newWidth * 3 / 4);
			imageView.setLayoutParams(new LinearLayout.LayoutParams(newWidth,
					newHeight));
			textView1.setText("图像宽度：" + newWidth + "  图像高度：" + newHeight);
		}
		else if (seekBar.getId() == R.id.seekBar2)
		{
			
			Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.dreamyworld_small))
					.getBitmap();
			matrix.setRotate(progress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			imageView.setImageBitmap(bitmap);
			textView2.setText(progress + "度");
		}

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
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageView = (ImageView) findViewById(R.id.imageView);
		SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		textView1 = (TextView)findViewById(R.id.textview1);
		textView2 = (TextView)findViewById(R.id.textview2);		
		seekBar1.setOnSeekBarChangeListener(this);
		seekBar2.setOnSeekBarChangeListener(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		seekBar1.setMax(dm.widthPixels - minWidth);
	}
}

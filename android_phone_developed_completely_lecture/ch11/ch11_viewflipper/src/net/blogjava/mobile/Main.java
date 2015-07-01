package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Main extends Activity implements OnTouchListener
{
	private ViewFlipper viewFlipper;
	private Animation translateIn;
	private Animation translateOut;
	private Animation alphaIn;
	private Animation alphaOut;
	

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		switch (view.getId())
		{
			case R.id.imageview1:
				viewFlipper.setInAnimation(translateIn);
				viewFlipper.setOutAnimation(translateOut);
				break;

			case R.id.imageview2:
				viewFlipper.setInAnimation(alphaIn);
				viewFlipper.setOutAnimation(alphaOut);
				break;
		}

		viewFlipper.showNext();

		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		viewFlipper = (ViewFlipper) getLayoutInflater().inflate(R.layout.main,
				null);
		View view1 = getLayoutInflater().inflate(R.layout.layout1, null);
		View view2 = getLayoutInflater().inflate(R.layout.layout2, null);
		View view3 = getLayoutInflater().inflate(R.layout.layout3, null);
		viewFlipper.addView(view1);
		viewFlipper.addView(view2);
		viewFlipper.addView(view3);
		setContentView(viewFlipper);
		translateIn = AnimationUtils.loadAnimation(this, R.anim.translate_in);
		translateOut = AnimationUtils.loadAnimation(this, R.anim.translate_out);
		alphaIn = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
		alphaOut = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
		ImageView imageView1 = (ImageView) view1.findViewById(R.id.imageview1);
		ImageView imageView2 = (ImageView) view2.findViewById(R.id.imageview2);
		imageView1.setOnTouchListener(this);
		imageView2.setOnTouchListener(this);

	}
}
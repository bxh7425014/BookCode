package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ImageView ivEarth = (ImageView) findViewById(R.id.ivEarth);
		ImageView ivHesper = (ImageView) findViewById(R.id.ivHesper);
		ImageView ivSun = (ImageView) findViewById(R.id.ivSun);
		Animation earthAnimation = AnimationUtils.loadAnimation(this,
				R.anim.earth);
		Animation hesperAnimation = AnimationUtils.loadAnimation(this,
				R.anim.hesper);
		Animation sunAnimation = AnimationUtils.loadAnimation(this, R.anim.sun);
		ivEarth.startAnimation(earthAnimation);
		ivHesper.startAnimation(hesperAnimation);
		ivSun.startAnimation(sunAnimation);

	
	
	}
}
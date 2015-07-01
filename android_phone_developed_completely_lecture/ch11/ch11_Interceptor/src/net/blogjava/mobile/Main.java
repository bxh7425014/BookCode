package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		ImageView imageView = (ImageView)findViewById(R.id.imageview);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
		animation.setInterpolator(new MyInterceptor());
		animation.setRepeatCount(Animation.INFINITE);
		imageView.startAnimation(animation);

	}
}
package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Main extends Activity implements OnClickListener,
		AnimationListener
{
	private EditText editText;
	private ImageView imageView;
	private Animation animationRight;
	private Animation animationBottom;
	private Animation animationTop;

	@Override
	public void onAnimationEnd(Animation animation)
	{
		if (animation.hashCode() == animationBottom.hashCode())
			imageView.startAnimation(animationTop);
		else if (animation.hashCode() == animationTop.hashCode())
			imageView.startAnimation(animationBottom);

	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{

	}

	@Override
	public void onAnimationStart(Animation animation)
	{

	}

	@Override
	public void onClick(View view)
	{
		// editText.startAnimation(animationLeft);
		editText.setAnimation(animationRight);
		animationRight.start();		
		animationRight.setRepeatCount(Animation.INFINITE);
		editText.setVisibility(EditText.VISIBLE);

		imageView.startAnimation(animationBottom);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editText = (EditText) findViewById(R.id.edittext);
		editText.setVisibility(EditText.INVISIBLE);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageview);
		animationRight = AnimationUtils.loadAnimation(this,
				R.anim.translate_right);
		animationBottom = AnimationUtils.loadAnimation(this,
				R.anim.translate_bottom);
		animationTop = AnimationUtils.loadAnimation(this, R.anim.translate_top);
		animationBottom.setAnimationListener(this);
		animationTop.setAnimationListener(this);
	}
}
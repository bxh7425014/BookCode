package net.blogjava.mobile;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Main extends Activity implements OnTouchListener,
		AnimationListener
{
	private ImageView ivMissile;
	private MyImageView ivBlast;
	private AnimationDrawable animationDrawable;
	private Animation missileAnimation;


	@Override
	public boolean onTouch(View view, MotionEvent event)
	{

		ivMissile.startAnimation(missileAnimation);
		
		return false;
	}

	@Override
	public void onAnimationEnd(Animation animation)
	{
		ivBlast.setVisibility(View.VISIBLE);
		ivMissile.setVisibility(View.INVISIBLE);
		try
		{
			MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bomb);
			mediaPlayer.stop();
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch (Exception e)
		{		
		}
		animationDrawable.stop();
		animationDrawable.start();

	}

	@Override
	public void onAnimationRepeat(Animation animation)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ivMissile = (ImageView) findViewById(R.id.ivMissile);
		ivMissile.setOnTouchListener(this);
		ivBlast = (MyImageView) findViewById(R.id.ivBlast);
		ivBlast.setBackgroundResource(R.anim.blast);
		Object backgroundObject = ivBlast.getBackground();
		animationDrawable = (AnimationDrawable) backgroundObject;
		ivBlast.animationDrawable = animationDrawable;
		missileAnimation = AnimationUtils.loadAnimation(this, R.anim.missile);
		missileAnimation.setAnimationListener(this);
		ivBlast.setVisibility(View.INVISIBLE);
		ivBlast.ivMissile = ivMissile;

	}
}
package net.blogjava.mobile;



import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class Main extends Activity implements OnClickListener
{

	private AnimationDrawable animationDrawable;

	@Override
	public void onClick(View view)
	{
		animationDrawable.stop();
		animationDrawable.start();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MyImageView ivAnimView = (MyImageView) findViewById(R.id.ivAnimView);

		ivAnimView.setBackgroundResource(R.anim.frame_animation);
		ivAnimView.setOnClickListener(this);
		Object backgroundObject = ivAnimView.getBackground();
		animationDrawable = (AnimationDrawable) backgroundObject;
		ivAnimView.animationDrawable = animationDrawable;		
		setTitle(getTitle() + "<¹²" + animationDrawable.getNumberOfFrames()
				+ "Ö¡>");

	}
}
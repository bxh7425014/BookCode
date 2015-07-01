package net.blogjava.mobile;

import java.lang.reflect.Field;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Main extends Activity implements OnClickListener
{
	private ImageView ivAnimView;
	private AnimationDrawable animationDrawable;
	private AnimationDrawable animationDrawable1;
	private Button btnAddFrame;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnOneShot:

				animationDrawable.setOneShot(true);
				animationDrawable.start();

				break;
			case R.id.btnStartAnim:
				animationDrawable.setOneShot(false);
				animationDrawable.stop();
				animationDrawable.start();

				break;

			case R.id.btnStopAnim:

				animationDrawable.stop();
				if (animationDrawable1 != null)
				{
					animationDrawable1.stop();
				}

				break;
			case R.id.btnAddFrame:
				if (btnAddFrame.isEnabled())
				{

					animationDrawable1 = (AnimationDrawable) getResources()
							.getDrawable(R.anim.frame_animation1);
					animationDrawable.addFrame(animationDrawable1, 2000);
					btnAddFrame.setEnabled(false);
				}
				break;

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnStartAnim = (Button) findViewById(R.id.btnStartAnim);
		Button btnStopAnim = (Button) findViewById(R.id.btnStopAnim);
		Button btnOneShot = (Button) findViewById(R.id.btnOneShot);
		btnAddFrame = (Button) findViewById(R.id.btnAddFrame);
		btnStartAnim.setOnClickListener(this);
		btnStopAnim.setOnClickListener(this);
		btnOneShot.setOnClickListener(this);
		btnAddFrame.setOnClickListener(this);
		ivAnimView = (ImageView) findViewById(R.id.ivAnimView);

		ivAnimView.setBackgroundResource(R.anim.frame_animation);
		Object backgroundObject = ivAnimView.getBackground();
		animationDrawable = (AnimationDrawable) backgroundObject;
		// animationDrawable.setAlpha(80);

	}
}
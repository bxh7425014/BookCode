package net.blogjava.mobile;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GLSurfaceView glView = new GLSurfaceView(this);
		MyRender myRender = new MyRender();
		glView.setRenderer(myRender);
		setContentView(glView);
	}
}  
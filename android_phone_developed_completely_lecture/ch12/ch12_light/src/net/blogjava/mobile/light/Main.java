package net.blogjava.mobile.light;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class Main extends Activity
{
	private MyRender myRender; 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GLImage.load(this.getResources());
		GLSurfaceView glView = new GLSurfaceView(this);
		myRender = new MyRender();
		glView.setRenderer(myRender);

		setContentView(glView);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
			myRender.light = !myRender.light;
		return super.onTouchEvent(event);
	}
	
}

class GLImage
{
	public static Bitmap mBitmap;

	public static void load(Resources resources)
	{
		mBitmap = BitmapFactory.decodeResource(resources, R.drawable.image);
	}
}
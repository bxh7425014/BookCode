package net.blogjava.mobile.texture;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Main extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GLImage.load(this.getResources());
		GLSurfaceView glView = new GLSurfaceView(this);
		MyRender myRender = new MyRender();
		glView.setRenderer(myRender);

		setContentView(glView);
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
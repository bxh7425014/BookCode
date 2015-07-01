package net.blogjava.mobile.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Main extends Activity implements OnClickListener
{
	public ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
		btnTakePicture.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageview);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1)
		{
			if (resultCode == 20)
			{
				Bitmap cameraBitmap;
				byte[] bytes = data.getExtras().getByteArray("bytes");
				cameraBitmap = BitmapFactory.decodeByteArray(bytes, 0,
						bytes.length);
				if (getWindowManager().getDefaultDisplay().getOrientation() == 0)
				{
					Matrix matrix = new Matrix();
					matrix.setRotate(90);
					cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0,
							cameraBitmap.getWidth(), cameraBitmap.getHeight(),
							matrix, true);
				}
				File myCaptureFile = new File("/sdcard/camera.jpg");
				try
				{
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(myCaptureFile));

					cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();

					imageView.setImageBitmap(cameraBitmap);

				}
				catch (Exception e)
				{
				}
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view)
	{
		Intent intent = new Intent(this, CameraPreview.class);
		startActivityForResult(intent, 1);

	}

}

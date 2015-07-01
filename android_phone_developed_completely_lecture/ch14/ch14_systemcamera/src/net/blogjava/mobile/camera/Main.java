package net.blogjava.mobile.camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Main extends Activity implements OnClickListener
{
	public ImageView imageView;
	private ImageView ivFocus;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
		btnTakePicture.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.imageview);
		ivFocus = new ImageView(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
				imageView.setImageBitmap(cameraBitmap);				
				//  存成320*240和50*50  如果是垂直方向，需要对cameraBitmap旋转90度
				MediaStore.Images.Media.insertImage(getContentResolver(), cameraBitmap, null, null);

			}
 
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view)
	{

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		//  不起作用
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/test.jpg")));
		ListView a;
		
		startActivityForResult(intent, 1);
		
	}

}

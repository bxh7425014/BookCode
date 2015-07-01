package net.blogjava.mobile;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

public class Main extends Activity implements OnClickListener
{
	private List<Integer> imageResIdList = new ArrayList<Integer>();
	private Gallery gallery;

	public class ImageAdapter extends BaseAdapter
	{
		int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter(Context context)
		{
			mContext = context;
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			mGalleryItemBackground = typedArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
		}

		public int getCount()
		{
			return Integer.MAX_VALUE;
		}

		public Object getItem(int position)
		{
			return position;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(mContext);

			imageView.setImageResource(imageResIdList.get(position));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));
			imageView.setBackgroundResource(mGalleryItemBackground);
			return imageView;
		}
	}

	@Override
	public void onClick(View view)
	{
		try
		{
			switch (view.getId())
			{
				//  将Gallery组件中的图像保存到SD卡的根目录
				case R.id.btnSaveImage:

					String sdcard = android.os.Environment
							.getExternalStorageDirectory().toString();
					FileOutputStream fos = new FileOutputStream(sdcard
							+ "/item" + gallery.getSelectedItemPosition() + ".jpg");
					((BitmapDrawable) getResources().getDrawable(
							imageResIdList.get(gallery
									.getSelectedItemPosition()))).getBitmap()
							.compress(CompressFormat.JPEG, 50, fos);
					fos.close();
					break;
			    //  显示图像浏览器
				case R.id.btnBrowserImage:
					Intent intent = new Intent(this, ImageBrowser.class);
					startActivity(intent);
					break;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try
		{
			Field[] fields = R.drawable.class.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.getName().startsWith("item"))
					imageResIdList.add(field.getInt(R.drawable.class));
			}
			gallery = (Gallery) findViewById(R.id.gallery);
			ImageAdapter imageAdapter = new ImageAdapter(this);
			gallery.setAdapter(imageAdapter);
			Button btnSaveImage = (Button) findViewById(R.id.btnSaveImage);
			Button btnBrowserImage = (Button) findViewById(R.id.btnBrowserImage);
			btnSaveImage.setOnClickListener(this);
			btnBrowserImage.setOnClickListener(this);

		}
		catch (Exception e)
		{

		}

	}
}
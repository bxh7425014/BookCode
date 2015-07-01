package net.blogjava.mobile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener
{
	private SharedPreferences mySharedPreferences;
	private EditText etProductID;
	private EditText etProductName;
	private EditText etProductPrice;
	private ImageView imageView;
	private List<Integer> imageResIdList = new ArrayList<Integer>();
	public class ImageAdapter extends BaseAdapter
	{
		int mGalleryItemBackground;
		private Context mContext;

		public ImageAdapter(Context context)
		{
			mContext = context;
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery1);
			mGalleryItemBackground = typedArray.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);

		}

		public int getCount()
		{
			return imageResIdList.size();
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
				case R.id.btnSave:
					Product product = new Product();
					product.setId(etProductID.getText().toString());
					product.setName(etProductName.getText().toString());
					product.setPrice(Float.parseFloat(etProductPrice.getText()
							.toString()));
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(product);
					mySharedPreferences = getSharedPreferences("base64",
							Activity.MODE_PRIVATE);
					String productBase64 = new String(Base64.encodeBase64(baos
							.toByteArray()));

					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString("product", productBase64);
               
					baos = new ByteArrayOutputStream();
					((BitmapDrawable) imageView.getDrawable()).getBitmap()
							.compress(CompressFormat.JPEG, 50, baos);
					
					String imageBase64 = new String(Base64.encodeBase64(baos
							.toByteArray()));
					editor.putString("productImage", imageBase64);

					editor.commit();
					oos.close();
					new AlertDialog.Builder(this).setTitle("保存成功.")
							.setPositiveButton("确定", null).show();

					break;

				case R.id.btnSelectImage:
					View myView = getLayoutInflater().inflate(R.layout.gallery,
							null);
					final Gallery gallery = (Gallery) myView
							.findViewById(R.id.gallery);
					ImageAdapter imageAdapter = new ImageAdapter(this);
					gallery.setAdapter(imageAdapter);

					new AlertDialog.Builder(this)
							.setTitle("选择产品图像")
							.setView(myView)
							.setPositiveButton(
									"确定",
									new android.content.DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{

											imageView
													.setImageResource(imageResIdList
															.get(gallery
																	.getSelectedItemPosition()));
										}
									}).setNegativeButton("取消", null).show();
					break;
			}

		}
		catch (Exception e)
		{
			setTitle("error:" + e.getMessage());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnSave = (Button) findViewById(R.id.btnSave);
		Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		etProductID = (EditText) findViewById(R.id.etProductID);
		etProductName = (EditText) findViewById(R.id.etProductName);
		etProductPrice = (EditText) findViewById(R.id.etProductPrice);
		imageView = (ImageView) findViewById(R.id.imageview);
		btnSave.setOnClickListener(this);
		btnSelectImage.setOnClickListener(this);
		byte[] base64Bytes;
		ByteArrayInputStream bais;
		try
		{

			mySharedPreferences = getSharedPreferences("base64",
					Activity.MODE_PRIVATE);

			String productBase64 = mySharedPreferences.getString("product", "");

			base64Bytes = Base64.decodeBase64(productBase64.getBytes());

			bais = new ByteArrayInputStream(base64Bytes);	
			ObjectInputStream ois = new ObjectInputStream(bais);

			Product product = (Product) ois.readObject();			
			etProductID.setText(product.getId());
			etProductName.setText(product.getName());
			etProductPrice.setText(String.valueOf(product.getPrice()));

			ois.close();

		}
		catch (Exception e)
		{
			
		}
		try
		{
			String imageBase64 = mySharedPreferences.getString("productImage",
					"");
			base64Bytes = Base64.decodeBase64(imageBase64.getBytes());
			bais = new ByteArrayInputStream(base64Bytes);
			imageView.setImageDrawable(Drawable.createFromStream(bais,
					"product_image"));

			Field[] fields = R.drawable.class.getDeclaredFields();

			for (Field field : fields)
			{
				if (!"icon".equals(field.getName()))
					imageResIdList.add(field.getInt(R.drawable.class));
			}
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

	}
}
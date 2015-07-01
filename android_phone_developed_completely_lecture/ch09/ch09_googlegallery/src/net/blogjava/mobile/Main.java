package net.blogjava.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView.ScaleType;

public class Main extends Activity implements OnClickListener
{
	
	private EditText etGoogleSearch;
	private ImageAdapter imageAdapter;
    private int currentPage = 0;
	public class ImageAdapter extends BaseAdapter
	{
		int galleryItemBackground;
		private Context mContext;
		private List<String> imageUrlList = new ArrayList<String>();

		public ImageAdapter(Context context)
		{
			mContext = context;
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			galleryItemBackground = typedArray.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
		}

		private InputStream getNetInputStream(String urlStr)
		{
			try
			{
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				conn
						.setRequestProperty(
								"User-Agent",
								"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.15) Gecko/2009101601 Firefox/3.0.15 (.NET CLR 3.5.30729)");
				conn.connect();
				InputStream is = conn.getInputStream();
				return is;
			}
			catch (Exception e)
			{
				Log.d("eee", e.getMessage());
			}
			return null; 
		}

		public void refreshImageList(String searchStr, int page)
		{
			try
			{
				
				String url = "http://images.google.com/images?hl=zh-CN&source=hp&q="
						+ URLEncoder.encode(searchStr, "utf-8")
						+ "&start="
						+ page * 20;
				
				InputStream is = getNetInputStream(url);
				
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String s = null;
				String html = "";
				while ((s = br.readLine()) != null)
				{
					html += s;
				}
				is.close();
				String startStr = "/imgres?imgurl\\x3d";
				String endStr = "]";
				int start = 0, end = 0;
				int count = 0;
				imageUrlList.clear();
				while (true)
				{
					start = html.indexOf(startStr, end);					
					if (start < 0)
						break;
					end = html.indexOf(endStr, start + startStr.length());
					String ss = html.substring(start + startStr.length(),
							end);
					String[] strArray = ss.split("\"");		
		
					imageUrlList.add("http://t1.gstatic.cn/images?q=tbn:" + strArray[4]);
				}
				this.notifyDataSetChanged();

			}
			catch (Exception e)
			{
			
			}

		}

		public int getCount()
		{
			return imageUrlList.size();
		}

		public Object getItem(int position)
		{
			return imageUrlList.get(position);
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(mContext);

			try
			{
				InputStream is = getNetInputStream(imageUrlList.get(position));
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				imageView.setImageBitmap(bitmap);
				is.close();

			}
			catch (Exception e)
			{

			}

			imageView.setScaleType(ScaleType.FIT_CENTER);
			imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));		
			imageView.setBackgroundResource(galleryItemBackground);

			return imageView;
		}
	}

	@Override
	public void onClick(View view)
	{
		
		switch (view.getId())
		{
			case R.id.btnSearch:
				currentPage = 0;
				imageAdapter.refreshImageList(etGoogleSearch.getText().toString(), currentPage);				
				break;
			case R.id.btnPrev:
				if(currentPage == 0) return;
				imageAdapter.refreshImageList(etGoogleSearch.getText().toString(), --currentPage);
				break;
			case R.id.btnNext:
				imageAdapter.refreshImageList(etGoogleSearch.getText().toString(), ++currentPage);
				break;
				
		}
		setTitle("ตฺ" + String.valueOf(currentPage + 1) + "าณ");
		

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		etGoogleSearch = (EditText)findViewById(R.id.etGoogleSearch);		
		Button btnSearch = (Button)findViewById(R.id.btnSearch);
		Button btnPrev = (Button)findViewById(R.id.btnPrev);
		Button btnNext = (Button)findViewById(R.id.btnNext);
		
		btnSearch.setOnClickListener(this);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		imageAdapter = new ImageAdapter(this);
		gallery.setAdapter(imageAdapter);
	}
}
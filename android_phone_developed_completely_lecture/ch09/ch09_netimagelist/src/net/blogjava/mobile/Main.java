package net.blogjava.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class Main extends ListActivity
{
	public class ApkListAdapter extends BaseAdapter
	{
		private Context context;
		private LayoutInflater layoutInflater;
		private String inflater = Context.LAYOUT_INFLATER_SERVICE;
		private String rootUrl = "http://192.168.17.156/apk/";
		private String listUrl = rootUrl + "list.txt";
		private List<ImageData> imageDataList = new ArrayList<ImageData>();

		class ImageData
		{
			public String url;
			public String applicationName;
			public float rating;
		}

		private InputStream getNetInputStream(String urlStr)
		{
			try
			{
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				return is;
			}
			catch (Exception e)
			{

			}
			return null;
		}

		public ApkListAdapter(Context context)
		{
			this.context = context;
			layoutInflater = (LayoutInflater) context
					.getSystemService(inflater);
			try
			{
				InputStream is = getNetInputStream(listUrl);
				InputStreamReader isr = new InputStreamReader(is, "GBK");
				BufferedReader br = new BufferedReader(isr);
				String s = null;
				while ((s = br.readLine()) != null)
				{
					String[] data = s.split(",");
					if (data.length > 2)
					{
						ImageData imageData = new ImageData();
						imageData.url = data[0];
						Log.d("aa", imageData.url);
						imageData.applicationName = data[1];
						imageData.rating = Float.parseFloat(data[2]);
						imageDataList.add(imageData);
					}
				}
				is.close();
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
		}

		@Override
		public int getCount()
		{
			return imageDataList.size();
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
					R.layout.item, null);
			ImageView ivLogo = (ImageView) linearLayout
					.findViewById(R.id.ivLogo);

			TextView tvApplicationName = ((TextView) linearLayout
					.findViewById(R.id.tvApplicationName));

			TextView tvRating = (TextView) linearLayout
					.findViewById(R.id.tvRating);
			RatingBar ratingBar = (RatingBar) linearLayout
					.findViewById(R.id.ratingbar);

			tvApplicationName
					.setText(imageDataList.get(position).applicationName);
			tvRating
					.setText(String.valueOf(imageDataList.get(position).rating));
			ratingBar.setRating(imageDataList.get(position).rating);

			try
			{
				InputStream is = getNetInputStream(rootUrl
						+ imageDataList.get(position).url);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				ivLogo.setImageBitmap(bitmap);
			}
			catch (Exception e)
			{
			}

			return linearLayout;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ApkListAdapter apkListAdapter = new ApkListAdapter(this);
		setListAdapter(apkListAdapter);

	}
}
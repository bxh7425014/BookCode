package com.yarin.android.Examples_06_04;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity01 extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		TextView tv = new TextView(this);
		
		String myString = null;
		
		try
		{
			/* 定义我们要访问的地址url */
			URL uri = new URL("http://192.168.1.110:8080/android.txt");
			
			/* 打开这个url连接 */
			URLConnection ucon = uri.openConnection();
			
			/* 从上面的链接中取得InputStream */
			InputStream is = ucon.getInputStream();
			
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
			int current = 0;
			/* 一直读到文件结束 */
			while ((current = bis.read()) != -1)
			{
				baf.append((byte) current);
			}
			myString = new String(baf.toByteArray());
		}
		catch (Exception e)
		{

			myString = e.getMessage();
		}
		/* 将信息设置到TextView */
		tv.setText(myString);
		
		/* 将TextView显示到屏幕上 */
		this.setContentView(tv);

	}
}

package org.crazyit.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class URLTest extends Activity
{
	ImageView show;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		show = (ImageView) findViewById(R.id.show);
		// 定义一个URL对象
		try
		{
			URL url = new URL("http://www.crazyit.org/attachments/"
				+ "month_1008/20100812_7763e970f822325bfb019ELQVym8tW3A.png");
			// 打开该URL对应的资源的输入流
			InputStream is = url.openStream();
			// 从InputStream中解析出图片
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			// 使用ImageView显示该图片
			show.setImageBitmap(bitmap);
			is.close();
			// 再次打开URL对应的资源的输入流
			is = url.openStream();
			// 打开手机文件对应的输出流
			OutputStream os = openFileOutput("crazyit.png"
				, MODE_WORLD_READABLE);
			byte[] buff = new byte[1024];
			int hasRead = 0;
			// 将URL对应的资源下载到本地
			while((hasRead = is.read(buff)) > 0)
			{
				os.write(buff, 0 , hasRead);
			}
			is.close();
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
package com.guo.weibo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import com.weibo.net.Weibo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//保存程序通用的函数和变量
public class Common {
	public static UserInfo login_user=null;
	public static Weibo weibo=Weibo.getInstance();
	//通过url地址取得图像
  	public static Bitmap getBm(URL aURL)
	{
		URLConnection conn;
		Bitmap bm=null;
		try {
			//打开url链接
			conn = aURL.openConnection();
			conn.connect();
			//将数据流保存到is
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			//用位图工厂解码数据流，将数据流转换成位图
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//返回位图
		return bm;		
	}
}

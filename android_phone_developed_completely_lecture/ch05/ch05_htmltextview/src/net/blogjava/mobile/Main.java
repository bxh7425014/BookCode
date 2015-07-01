package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//  自动识别链接的方式
		TextView tvWebURL = (TextView) findViewById(R.id.tvWebURL);
		tvWebURL.setText("作者博客：http://nokiaguy.blogjava.net");

		TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvEmail.setText("电子邮件:techcast@126.com");

		TextView tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvPhone.setText("联系电话:024-12345678");
		
		//  在代码中设置带HTML标签的文本
		TextView textView2 = (TextView) findViewById(R.id.textview2);
		textView2
				.setText(Html
						.fromHtml("作者博客：<a href='http://nokiaguy.blogjava.net'>http://nokiaguy.blogjava.net</a><h1><i><font color='#0000FF'>h1号字、斜体、蓝色</font></i></h5></h1><h3>h3号字</h3><h5><font color='#CC0000'>李宁</font></h5>"));
	}
}
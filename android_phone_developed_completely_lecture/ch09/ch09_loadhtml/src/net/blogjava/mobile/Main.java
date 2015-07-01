package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Main extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView webView = (WebView) findViewById(R.id.webview);
		String html = "<html>"
				+ "<body>"
				+ "图书封面<br>"
				+ "<table width='200' border='1' >"
				+ "<tr>"
				+ "<td><a onclick='alert(\"Java Web开发速学宝典\")' ><img style='margin:10px' src='http://images.china-pub.com/ebook45001-50000/48015/cover.jpg' width='100'/></a></td>"				
				+ "<td><a onclick='alert(\"大象--Thinking in UML\")' ><img style='margin:10px' src='http://images.china-pub.com/ebook125001-130000/129881/zcover.jpg' width='100'/></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><img style='margin:10px' src='http://images.china-pub.com/ebook25001-30000/27518/zcover.jpg' width='100'/></td>"
				+ "<td><img  style='margin:10px' src='http://images.china-pub.com/ebook30001-35000/34838/zcover.jpg' width='100'/></td>"
				+ "</tr>" + "</table>" + "</body>" + "</html>";

		 webView.loadDataWithBaseURL("图书名", html, "text/html", "utf-8", null);
		 webView.getSettings().setJavaScriptEnabled(true);
		 webView.setWebChromeClient(new WebChromeClient());
	
		 
	}
}

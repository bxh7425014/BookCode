package net.blogjava.mobile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;

public class Main extends Activity
{
	public final String DICTIONARY_SINGLE_WORD_URI = "content://net.blogjava.mobile.dictionarycontentprovider/single";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView webView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		
		webView.addJavascriptInterface(new Object()
		{
			public String searchWord(String word)
			{
				Uri uri = Uri.parse(DICTIONARY_SINGLE_WORD_URI);
				Cursor cursor = getContentResolver().query(uri, null,
						"english=?", new String[]
						{ word }, null);
				String result = "Î´ÕÒµ½¸Ãµ¥´Ê.";
				if (cursor.getCount() > 0)
				{
					cursor.moveToFirst();
					result = cursor.getString(cursor.getColumnIndex("chinese"));
				}
				return result;
			}

		}, "dictionary");
		InputStream is = getResources().openRawResource(R.raw.dictionary);
		byte[] buffer = new byte[1024];
		try
		{
			int count = is.read(buffer);
			String html = new String(buffer,0 ,count, "utf-8");
			webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
		}
		catch (Exception e)
		{
		
		}

	}
}
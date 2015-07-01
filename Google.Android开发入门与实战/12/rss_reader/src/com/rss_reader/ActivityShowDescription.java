package com.rss_reader;
//Download by http://www.codefans.net
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.*;

public class ActivityShowDescription extends Activity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.showdescription);
		String content = null;
		Intent startingIntent = getIntent();

		if (startingIntent != null) {
			Bundle bundle = startingIntent
					.getBundleExtra("android.intent.extra.rssItem");
			if (bundle == null) {
				content = "不好意思程序出错啦";
			} else {
				content = bundle.getString("title") + "\n\n"
						+ bundle.getString("pubdate") + "\n\n"
						+ bundle.getString("description").replace('\n', ' ')
						+ "\n\n详细信息请访问以下网址:\n" + bundle.getString("link");
			}
		} else {
			content = "不好意思程序出错啦";

		}

		TextView textView = (TextView) findViewById(R.id.content);
		textView.setText(content);

		Button backbutton = (Button) findViewById(R.id.back);

		backbutton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}

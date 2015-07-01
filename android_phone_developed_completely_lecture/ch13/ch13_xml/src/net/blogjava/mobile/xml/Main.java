package net.blogjava.mobile.xml;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView) findViewById(R.id.textview);
		StringBuffer sb = new StringBuffer();
		XmlResourceParser xml = getResources().getXml(R.xml.android);
		try
		{
			
			int eventType = xml.next();
			while (true)
			{
				if (eventType == XmlPullParser.START_DOCUMENT)
				{
					Log.d("start_document", "start_document");

				}
				else if (eventType == XmlPullParser.START_TAG)
				{
					Log.d("start_tag", xml.getName());

					sb
							.append(xml.getName() + "£¨depth£º" + xml.getDepth()
									+ "  ");
					int count = xml.getAttributeCount();
					for (int i = 0; i < count; i++)
					{ 
						sb.append(xml.getAttributeName(i) + ":"
								+ xml.getAttributeValue(i) + "  ");
					}
					sb.append("£©\n");

				}
				else if (eventType == XmlPullParser.END_TAG)
				{
					Log.d("end_tag", xml.getName());

				}
				else if (eventType == XmlPullParser.TEXT)
				{
					Log.d("text", "text");
				}
				else if (eventType == XmlPullParser.END_DOCUMENT)
				{
					Log.d("end_document", "end_document");
					break;
				}
				eventType = xml.next();
			}
			textView.setText(sb.toString());
		}
		catch (Exception e)
		{

		}

	}
}
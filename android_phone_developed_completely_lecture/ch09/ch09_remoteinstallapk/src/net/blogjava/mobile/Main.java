package net.blogjava.mobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private void installApk(String filename)
	{
		File file = new File(filename);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);

	}

	@Override
	public void onClick(View view)
	{
		String downloadPath = Environment.getExternalStorageDirectory().getPath() + "/download_cache";		

		String url = "http://192.168.17.156/apk/integration.apk";
		File file = new File(downloadPath);
		if(!file.exists())
		{
			file.mkdir();
		}
		HttpGet httpGet = new HttpGet(url);
		try
		{
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{

				InputStream is = httpResponse.getEntity().getContent();
				FileOutputStream fos = new FileOutputStream(downloadPath
						+ "/integration.apk");
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) != -1)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				installApk(downloadPath+ "/integration.apk");
			}
		}
		catch (Exception e) 
		{
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		Button btnDownloadInstallApk = (Button) findViewById(R.id.btnDownloadInstallApk);
		btnDownloadInstallApk.setOnClickListener(this);
		
	}
}
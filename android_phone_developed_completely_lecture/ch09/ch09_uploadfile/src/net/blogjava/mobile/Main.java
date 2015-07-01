package net.blogjava.mobile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity implements OnFileBrowserListener
{

	@Override
	public void onDirItemClick(String path)
	{

	}

	@Override
	public void onFileItemClick(String filename)
	{
		String uploadUrl = "http://192.168.17.156:8080/upload/UploadServlet";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try
		{
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			
			DataOutputStream dos = new DataOutputStream(httpURLConnection
					.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos
					.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
							+ filename.substring(filename.lastIndexOf("/") + 1)
							+ "\"" + end);
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(filename);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1)
			{
				dos.write(buffer, 0, count);

			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			dos.close();
			is.close();
			

		}
		catch (Exception e)
		{
			setTitle(e.getMessage());
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		FileBrowser fileBrowser = (FileBrowser) findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
	}
}
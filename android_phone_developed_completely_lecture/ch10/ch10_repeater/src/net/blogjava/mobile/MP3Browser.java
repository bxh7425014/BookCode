package net.blogjava.mobile;

import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MP3Browser extends Activity implements OnFileBrowserListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filebrowser);
		FileBrowser fileBrowser = (FileBrowser) findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
	}

	@Override
	public void onDirItemClick(String path)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileItemClick(String filename)
	{
		Intent intent = new Intent();
		intent.putExtra("filename", filename);
		setResult(1, intent);
		finish();

	}

}

package net.blogjava.mobile;

import java.io.FileInputStream;

import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageBrowser extends Activity implements OnFileBrowserListener
{

	@Override
	public void onDirItemClick(String path)
	{
		setTitle("当前目录：" + path);

	}

	@Override
	public void onFileItemClick(String filename)
	{
		if (!filename.toLowerCase().endsWith(".jpg"))
			return;
		View view = getLayoutInflater().inflate(R.layout.imagebrowser, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		try
		{
			FileInputStream fis = new FileInputStream(filename);

			imageView
					.setImageDrawable(Drawable.createFromStream(fis, filename));
			new AlertDialog.Builder(this).setTitle("浏览图像").setView(view)
					.setPositiveButton("关闭", null).show();
			fis.close();
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filebrowser);
		FileBrowser fileBrowser = (FileBrowser) findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
	}
}

package net.blogjava.mobile;

import java.io.FileInputStream;
import java.util.List;
import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Xml;

public class Main extends Activity implements OnFileBrowserListener
{

	@Override
	public void onDirItemClick(String path)
	{
		setTitle("当前目录：" + path);

	}

	@Override
	public void onFileItemClick(String filename)
	{
		try
		{
			if (!filename.toLowerCase().endsWith("xml"))
				return;
			FileInputStream fis = new FileInputStream(filename);
			XML2Product xml2Product = new XML2Product();
			android.util.Xml.parse(fis, Xml.Encoding.UTF_8, xml2Product);

			List<Product> products = xml2Product.getProducts();
			String msg = "共" + products.size() + "个产品\n";
			for (Product product : products)
			{
				msg += "id:" + product.getId() + "  产品名：" + product.getName()
						+ "  价格：" + product.getPrice() + "\n";
			}
			new AlertDialog.Builder(this).setTitle("产品信息").setMessage(msg)
					.setPositiveButton("关闭", null).show();
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
		FileBrowser fileBrowser = (FileBrowser) findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
	}
}
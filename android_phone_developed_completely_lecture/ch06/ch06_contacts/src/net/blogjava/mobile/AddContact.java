package net.blogjava.mobile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.channels.SelectableChannel;

import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Contacts.Intents.Insert;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddContact extends Activity implements OnClickListener,
		OnFileBrowserListener, OnMenuItemClickListener
{

	private FileBrowser fileBrowser;
	private View fileBrowserView;
	private AlertDialog alertDialog;
	private EditText etName;
	private EditText etTelephone;
	private EditText etEmail;
	private ImageView ivPhoto;
	private String photoFilename;

	@Override
	public void onDirItemClick(String path)
	{

	}

	@Override
	public void onFileItemClick(String filename)
	{
		if (filename.toLowerCase().endsWith("jpg")
				|| filename.toLowerCase().endsWith("jpeg"))
		{
			try
			{
				FileInputStream fis = new FileInputStream(filename);
				ivPhoto.setImageDrawable(Drawable.createFromStream(fis,
						filename));
				alertDialog.dismiss();
				photoFilename = filename;
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}

		}
	}

	@Override
	public void onClick(View view)
	{
		fileBrowserView = getLayoutInflater().inflate(R.layout.select_photo,
				null);
		fileBrowser = (FileBrowser) fileBrowserView
				.findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
		alertDialog = new AlertDialog.Builder(this).setTitle("选择联系人头像")
				.setIcon(R.drawable.select_photo).setView(fileBrowserView)
				.setPositiveButton("关闭", null).create();
		alertDialog.show();

	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		Button btnSelectPhoto = (Button) findViewById(R.id.btnSelectPhoto);
		btnSelectPhoto.setOnClickListener(this);
		etName = (EditText) findViewById(R.id.etName);
		etTelephone = (EditText) findViewById(R.id.etTelphone);
		etEmail = (EditText) findViewById(R.id.etEmail);
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

	}

	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		String sql = " insert into t_contacts(name, telephone, email, photo) values(?,?,?,?)";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		((BitmapDrawable) ivPhoto.getDrawable()).getBitmap().compress(
				CompressFormat.JPEG, 50, baos);
		Object[] args = new Object[]
		{ etName.getText(), etTelephone.getText(), etEmail.getText(),
				baos.toByteArray() };

		Main.dbService.execSQL(sql, args);
		Main.contactAdapter.getCursor().requery();		
		Main.contactAdapter.notifyDataSetChanged();
		finish();
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("保存").setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

}

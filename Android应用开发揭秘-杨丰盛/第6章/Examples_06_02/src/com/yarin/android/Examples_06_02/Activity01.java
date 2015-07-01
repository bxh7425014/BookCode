package com.yarin.android.Examples_06_02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class Activity01 extends Activity
{
	
	private MIDIPlayer	mMIDIPlayer	= null;
	private boolean		mbMusic		= false;
	private TextView	mTextView	= null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView = (TextView) this.findViewById(R.id.TextView01);

		mMIDIPlayer = new MIDIPlayer(this);

		/* 读取文件数据  */
		load();

		if (mbMusic)
		{
			mTextView.setText("当前音乐状态：开");
			mbMusic = true;
			mMIDIPlayer.PlayMusic();
		}
		else
		{
			mTextView.setText("当前音乐状态：关");
		}

	}

	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				mTextView.setText("当前音乐状态：开");
				mbMusic = true;
				mMIDIPlayer.PlayMusic();
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				mTextView.setText("当前音乐状态：关");
				mbMusic = false;
				mMIDIPlayer.FreeMusic();
				break;
		}
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			/* 退出应用程序时保存数据 */
			save();
			
			if ( mbMusic )
			{
				mMIDIPlayer.FreeMusic();
			}
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/* 装载、读取数据 */
	void load()
	{
		/* 构建Properties对对象 */
		Properties properties = new Properties();
		try
		{
			/* 开发文件 */
			FileInputStream stream = this.openFileInput("music.cfg");
			/* 读取文件内容 */
			properties.load(stream);
		}
		catch (FileNotFoundException e)
		{
			return;
		}
		catch (IOException e)
		{
			return;
		}
		/* 取得数据 */
		mbMusic = Boolean.valueOf(properties.get("bmusic").toString());
	}
	
	/* 保存数据 */
	boolean save()
	{
		Properties properties = new Properties();
		
		/* 将数据打包成Properties */
		properties.put("bmusic", String.valueOf(mbMusic));
		try
		{
			FileOutputStream stream = this.openFileOutput("music.cfg", Context.MODE_WORLD_WRITEABLE);
			
			/* 将打包好的数据写入文件中 */
			properties.store(stream, "");
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}

		return true;
	}
}

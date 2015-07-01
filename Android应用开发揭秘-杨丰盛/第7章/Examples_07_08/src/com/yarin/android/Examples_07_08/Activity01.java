package com.yarin.android.Examples_07_08;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class Activity01 extends Activity
{
	/* 3个按钮 */
	private Button mButtonRingtone;
	private Button mButtonAlarm;
	private Button mButtonNotification;

	/* 自定义的类型 */
	public static final int ButtonRingtone			= 0;
	public static final int ButtonAlarm				= 1;
	public static final int ButtonNotification		= 2;
	/* 铃声文件夹 */
	private String strRingtoneFolder = "/sdcard/music/ringtones";
	private String strAlarmFolder = "/sdcard/music/alarms";
	private String strNotificationFolder = "/sdcard/music/notifications";


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		mButtonRingtone = (Button) findViewById(R.id.ButtonRingtone);
		mButtonAlarm = (Button) findViewById(R.id.ButtonAlarm);
		mButtonNotification = (Button) findViewById(R.id.ButtonNotification);
		/* 设置来电铃声 */
		mButtonRingtone.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				if (bFolder(strRingtoneFolder))
				{
					//打开系统铃声设置
					Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
					//类型为来电RINGTONE
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
					//设置显示的title
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");
					//当设置完成之后返回到当前的Activity
					startActivityForResult(intent, ButtonRingtone);
				}
			}
		});
		/* 设置闹钟铃声 */
		mButtonAlarm.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				if (bFolder(strAlarmFolder))
				{
					//打开系统铃声设置
					Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
					//设置铃声类型和title
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃铃声");
					//当设置完成之后返回到当前的Activity
					startActivityForResult(intent, ButtonAlarm);
				}
			}
		});
		/* 设置通知铃声 */
		mButtonNotification.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				if (bFolder(strNotificationFolder))
				{
					//打开系统铃声设置
					Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
					//设置铃声类型和title
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置通知铃声");
					//当设置完成之后返回到当前的Activity
					startActivityForResult(intent, ButtonNotification);
				}
			}
		});
	}
	/* 当设置铃声之后的回调函数 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK)
		{
			return;
		}
		switch (requestCode)
		{
			case ButtonRingtone:
				try
				{
					//得到我们选择的铃声
					Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
					//将我们选择的铃声设置成为默认
					if (pickedUri != null)
					{
						RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_RINGTONE, pickedUri);
					}
				}
				catch (Exception e)
				{
				}
				break;
			case ButtonAlarm:
				try
				{
					//得到我们选择的铃声
					Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
					//将我们选择的铃声设置成为默认
					if (pickedUri != null)
					{
						RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_ALARM, pickedUri);
					}
				}
				catch (Exception e)
				{
				}
				break;
			case ButtonNotification:
				try
				{
					//得到我们选择的铃声
					Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
					//将我们选择的铃声设置成为默认
					if (pickedUri != null)
					{
						RingtoneManager.setActualDefaultRingtoneUri(Activity01.this, RingtoneManager.TYPE_NOTIFICATION, pickedUri);
					}
				}
				catch (Exception e)
				{
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	//检测是否存在指定的文件夹 
	//如果不存在则创建
	private boolean bFolder(String strFolder)
	{
		boolean btmp = false;
		File f = new File(strFolder);
		if (!f.exists())
		{
			if (f.mkdirs())
			{
				btmp = true;
			}
			else
			{
				btmp = false;
			}
		}
		else
		{
			btmp = true;
		}
		return btmp;
	}
}

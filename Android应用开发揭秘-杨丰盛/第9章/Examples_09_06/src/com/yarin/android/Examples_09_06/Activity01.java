package com.yarin.android.Examples_09_06;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.LiveFolders;

public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// 判断是否创建实时文件夹
		if (getIntent().getAction().equals(LiveFolders.ACTION_CREATE_LIVE_FOLDER))
		{
			Intent intent = new Intent();
			// 设置数据地址
			intent.setData(Uri.parse("content://contacts/live_folders/people"));
			// 设置当我们单击之后的事件，这里单击一个联系人后，呼叫
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT, new Intent(Intent.ACTION_CALL, ContactsContract.Contacts.CONTENT_URI));
			// 设置实时文件夹的名字
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, "电话本");
			// 设置实施文件夹的图标
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON, Intent.ShortcutIconResource.fromContext(this, R.drawable.contacts));
			// 设置显示模式为列表
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE, LiveFolders.DISPLAY_MODE_LIST);
			// 完成
			setResult(RESULT_OK, intent);
		}
		else
		{
			setResult(RESULT_CANCELED);
		}
		finish();
	}
}

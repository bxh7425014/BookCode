package org.crazyit.desktop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.LiveFolders;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class ContactsLiveFolder extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 如果该Intent的Action是创建实时文件夹的Action
		if (getIntent().getAction().equals(
			LiveFolders.ACTION_CREATE_LIVE_FOLDER))
		{
			Intent intent = new Intent();
			// 设置实时文件夹所显示ContentProvider提供数据的URI
			intent.setData(Uri.parse(
				"content://contacts/live_folders/people"));
			// 设置单击实时文件夹所显示的各项数据时将触发该Intent对应的组件
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT,
				new Intent(Intent.ACTION_VIEW,
					ContactsContract.Contacts.CONTENT_URI));
			// 设置实时文件夹的名称
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, "电话本");
			// 设置实时文件夹的图标
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,
				Intent.ShortcutIconResource.fromContext(this, R.drawable.icon));
			// 设置实时文件夹的显示模式
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE,
				LiveFolders.DISPLAY_MODE_LIST);
			setResult(RESULT_OK, intent);
		}
		else
		{
			setResult(RESULT_CANCELED);
		}
		// 结束该Activity。
		finish();
	}
}
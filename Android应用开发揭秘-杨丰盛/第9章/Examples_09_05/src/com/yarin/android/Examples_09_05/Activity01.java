package com.yarin.android.Examples_09_05;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

public class Activity01 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//要添加的快捷方式的Intent
		Intent addShortcut; 
		//判断是否要添加快捷方式
		if (getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT))
		{
			addShortcut = new Intent(); 
			//设置快捷方式的名字
			addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "发送邮件");
			//构建快捷方式中专门的图标
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this,R.drawable.mail_edit);  
			//添加快捷方式图标
			addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,icon);
			//构建快捷方式执行的Intent
			Intent mailto = new  Intent(Intent.ACTION_SENDTO, Uri.parse( "mailto:xxx@xxx.com" )); 
			//添加快捷方式Intent
			addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, mailto);  
			//正常
			setResult(RESULT_OK,addShortcut);  
		}
		else
		{
			//取消
			setResult(RESULT_CANCELED);  
		}
		//关闭
		finish();  
	}
}

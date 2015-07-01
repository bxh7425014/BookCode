package com.yarin.android.Examples_06_03;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	private int  miCount = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
	
		miCount=1000;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			//退出应用程序时保存数据
			/* 发送邮件的地址 */
			Uri uri = Uri.parse("mailto:fengsheng.studio@hotmail.com");  
			
			/* 创建Intent */
			Intent it = new Intent(Intent.ACTION_SENDTO, uri);  
			
			/* 设置邮件的主题 */
			it .putExtra(android.content.Intent.EXTRA_SUBJECT, "数据备份");
			
			/* 设置邮件的内容 */
			it .putExtra(android.content.Intent.EXTRA_TEXT, "本次计数："+miCount);
			
			/* 开启 */
			startActivity(it);
			
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

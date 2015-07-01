package net.blogjava.mobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private void showDialog(String title, String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//  设置对话框的图标
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//  设置对话框的标题
		builder.setTitle(title);
		//  设置对话框显示的信息
		builder.setMessage(msg);
		//  设置对话框的按钮
		builder.setPositiveButton("确定", null);
		//  显示对话框
		builder.create().show();
		Intent intent;
		
	}
	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
			case R.id.btnShowDate:
			{				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//  显示当前日期
				showDialog("当前日期", sdf.format(new Date()));
				break;
			}
			case R.id.btnShowTime: 
			{				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				//  显示当前时间
				showDialog("当前时间", sdf.format(new Date()));
				break;
			} 
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//  获得两个按钮的对象实例
		Button btnShowDate = (Button) findViewById(R.id.btnShowDate);
		Button btnShowTime = (Button) findViewById(R.id.btnShowTime);
		//  为两个按钮添加单击事件
		btnShowDate.setOnClickListener(this);
		btnShowTime.setOnClickListener(this);
		//setTitle(R.string.hello);
		
	}
}
package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main extends Activity implements OnClickListener
{
	@Override
	public void onClick(View v)
	{
		LinearLayout loginLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.login, null);
		new AlertDialog.Builder(this).setIcon(R.drawable.login)
				.setTitle("用户登录").setView(loginLayout).setPositiveButton("登录",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								// 编写处理用户登录的代码
							}
						}).setNegativeButton("取消",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								// 取消用户登录，退出程序

							}
						}).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);

	}
}
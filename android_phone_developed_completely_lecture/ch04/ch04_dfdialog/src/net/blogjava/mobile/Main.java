package net.blogjava.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	@Override
	public void onClick(View v)
	{
		new AlertDialog.Builder(this).setIcon(R.drawable.question).setTitle(
				"是否删除文件").setPositiveButton("确定",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						new AlertDialog.Builder(Main.this).setMessage(
								"文件已经被删除.").create().show();
					}
				}).setNegativeButton("取消",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{

						new AlertDialog.Builder(Main.this).setMessage(
								"您已经选择了取消按钮，该文件未被删除.").create().show();
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
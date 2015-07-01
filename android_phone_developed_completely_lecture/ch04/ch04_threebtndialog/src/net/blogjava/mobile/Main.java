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
				"是否覆盖文件？").setPositiveButton("覆盖",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						new AlertDialog.Builder(Main.this)
								.setMessage("文件已经覆盖.").create().show();
					}
				}).setNeutralButton("忽略", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				new AlertDialog.Builder(Main.this).setMessage("忽略了覆盖文件的操作.")
						.create().show();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{

				new AlertDialog.Builder(Main.this).setMessage("您已经取消了所有的操作.")
						.create().show();
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
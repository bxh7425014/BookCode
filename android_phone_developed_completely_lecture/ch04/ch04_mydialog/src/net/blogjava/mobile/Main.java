package net.blogjava.mobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Main extends Activity implements android.view.View.OnClickListener
{
	private DateDialog dateDialog;

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnCurrentDate:
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");

				dateDialog.setIcon(R.drawable.date);
				dateDialog.setTitle("当前日期："
						+ simpleDateFormat.format(new Date()));
				dateDialog.setButton("确定", new OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// 

					}
				});
				dateDialog.setOnDismissListener(new OnDismissListener()
				{
					@Override
					public void onDismiss(DialogInterface dialog)
					{
						new DateDialog.Builder(Main.this).setMessage(
								"您已经关闭的当前对话框.").create().show();
					}
				});
				dateDialog.show();
				break;

			case R.id.btnFinish:
				finish();
				break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		finish();
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnCurrentDate = (Button) findViewById(R.id.btnCurrentDate);
		Button btnFinish = (Button) findViewById(R.id.btnFinish);
		btnCurrentDate.setOnClickListener(this);
		btnFinish.setOnClickListener(this);
		dateDialog = new DateDialog(this);

	}
}
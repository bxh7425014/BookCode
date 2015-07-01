package net.blogjava.mobile;

import java.util.Random;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener
{
	private static final int MAX_PROGRESS = 100;
	private ProgressDialog progressDialog;
	private Handler progressHandler;
	private int progress;

	// 显示进度对话框，style表示进度对话框的风格
	private void showProgressDialog(int style)
	{
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(R.drawable.wait);
		progressDialog.setTitle("正在处理数据...");
		progressDialog.setMessage("请稍后...");		
		// 设置进度对话框的风格
		progressDialog.setProgressStyle(style);
		// 设置进度对话框的进度最大值
		progressDialog.setMax(MAX_PROGRESS);
		// 设置进度对话框的【暂停】按钮
		progressDialog.setButton("暂停", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// 通过删除消息代码的方式停止定时器
				progressHandler.removeMessages(1);
			}
		});
		// 设置进度对话框的【取消】按钮
		progressDialog.setButton2("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// 通过删除消息代码的方式停止定时器的执行
				progressHandler.removeMessages(1);
				// 恢复进度初始值
				progress = 0;
				progressDialog.setProgress(0);
			}
		});
		progressDialog.show();
		progressHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				// if (progressDialog.getProgress() >= MAX_PROGRESS)
				if (progress >= MAX_PROGRESS)
				{
					// 进度达到最大值，关闭对话框
					progress = 0;
					progressDialog.dismiss();
					
				}
				else
				{
					progress++;
					// 将进度递增1
					progressDialog.incrementProgressBy(1);
					// 随机设置下一次递增进度（调用handleMessage方法）的时间
					progressHandler.sendEmptyMessageDelayed(1,
							50 + new Random().nextInt(500));

				}
			}
		};

		// 设置进度初始值
		progress = (progress > 0) ? progress : 0;
		progressDialog.setProgress(progress);
		progressHandler.sendEmptyMessage(1);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.button1:
				// 显示进度条风格的进度对话框
				showProgressDialog(ProgressDialog.STYLE_HORIZONTAL);
				break;

			case R.id.button2:
				// 显示旋转指针风格的进度对话框
				showProgressDialog(ProgressDialog.STYLE_SPINNER);
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);

	}
}
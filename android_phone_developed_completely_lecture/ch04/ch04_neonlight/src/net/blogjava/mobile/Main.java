package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Main extends Activity implements Runnable
{
	// 5个TextView的颜色值
	private int[] colors = new int[]
	{ 0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFF00FF, 0xFF00FFFF };
	// 每一次颜色的下一个颜色的索引，最后一个颜色的下一个颜色是第一个颜色，相当于循环链表
	private int[] nextColorPointers = new int[]
	{ 1, 2, 3, 4, 0 };
	private View[] views; // 保存5个TextView
	private int currentColorPointer = 0; // 当前颜色索引（指针）
	private Handler handler;

	@Override
	public void run()
	{
		int nextColorPointer = currentColorPointer;
		for (int i = views.length - 1; i >= 0; i--)
		{
			// 设置当前TextView的背景颜色
			views[i]
					.setBackgroundColor(colors[nextColorPointers[nextColorPointer]]);
			// 获得下一个TextView的背景颜色值的索引（指针）
			nextColorPointer = nextColorPointers[nextColorPointer];
		}
		currentColorPointer++;
		if (currentColorPointer == 5)
			currentColorPointer = 0;
		handler.postDelayed(this, 300); // 第300毫秒循环一次
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 初始化views数组
		views = new View[]
		{ findViewById(R.id.textview5), findViewById(R.id.textview4),
				findViewById(R.id.textview3), findViewById(R.id.textview2),
				findViewById(R.id.textview1) };
		handler = new Handler();
		handler.postDelayed(this, 300); // 第300毫秒循环一次
	
	}
}

package org.crazyit.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
public class FileTest extends Activity
{
	final String FILE_NAME = "crazyit.bin";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.out.println(new StringBuilder("a").append("b").append("c")
			.toString());
		// 获取两个按钮
		Button read = (Button) findViewById(R.id.read);
		Button write = (Button) findViewById(R.id.write);
		// 获取两个文本框
		final EditText edit1 = (EditText) findViewById(R.id.edit1);
		final EditText edit2 = (EditText) findViewById(R.id.edit2);
		// 为write按钮绑定事件监听器
		write.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 将edit1中的内容写入文件中
				write(edit1.getText().toString());
				edit1.setText("");
			}
		});

		read.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 读取指定文件中的内容，并显示出来
				edit2.setText(read());
			}
		});
	}

	private String read()
	{
		try
		{
			// 打开文件输入流
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] buff = new byte[1024];
			int hasRead = 0;
			StringBuilder sb = new StringBuilder("");
			while ((hasRead = fis.read(buff)) > 0)
			{
				sb.append(new String(buff, 0, hasRead));
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void write(String content)
	{
		try
		{
			// 以追加模式打开文件输出流
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
			// 将FileOutputStream包装成PrintStream
			PrintStream ps = new PrintStream(fos);
			// 输出文件内容
			ps.println(content);
			ps.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
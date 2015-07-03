package org.crazyit.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class SingleChoiceDialog extends Activity
{
	final int SINGLE_DIALOG = 0x113;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		//为按钮绑定事件监听器
		bn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//显示对话框
				showDialog(SINGLE_DIALOG);				
			}
		});
	}
	//重写onCreateDialog方法创建对话框
	@Override
	public Dialog onCreateDialog(int id, Bundle state)
	{
		//判断需要生成哪种类型的对话框
		switch (id)
		{
			case SINGLE_DIALOG:
				Builder b = new AlertDialog.Builder(this);
				// 设置对话框的图标
				b.setIcon(R.drawable.tools);
				// 设置对话框的标题
				b.setTitle("单选列表对话框");
				// 为对话框设置多个列表
				b.setSingleChoiceItems(new String[]
				{ "红色", "绿色", "蓝色" }
				// 默认选中第二项
					, 1
					//为列表项的单击事件设置监听器
					, new OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog,
							int which)
						{
							TextView show = (TextView) findViewById(R.id.show);
							// which代表哪个列表项被单击了
							switch (which)
							{
								//修改文本框的背景色
								case 0:
									show.setBackgroundColor(Color.RED);
									break;
								case 1:
									show.setBackgroundColor(Color.GREEN);
									break;
								case 2:
									show.setBackgroundColor(Color.BLUE);
									break;
							}
						}
					});
				// 添加一个“确定”按钮，用于关闭该对话框
				b.setPositiveButton("确定", null);
				// 创建对话框
				return b.create();
		}
		return null;
	}
}
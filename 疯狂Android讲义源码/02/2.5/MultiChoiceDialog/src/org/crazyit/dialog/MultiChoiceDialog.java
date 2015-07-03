package org.crazyit.dialog;

import org.crazyit.dialog.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
public class MultiChoiceDialog extends Activity
{
	final int SINGLE_DIALOG = 0x113;
	String[] colorNames = new String[]
	{ "红色", "绿色", "蓝色" };
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button) findViewById(R.id.bn);
		// 为按钮绑定事件监听器
		bn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				// 显示对话框
				showDialog(SINGLE_DIALOG);
			}
		});
	}
	@Override
	public Dialog onCreateDialog(int id, Bundle state)
	{
		// 判断需要生成哪种类型的对话框
		switch (id)
		{
			case SINGLE_DIALOG:
				Builder b = new AlertDialog.Builder(this);
				// 设置对话框的图标
				b.setIcon(R.drawable.tools);
				// 设置对话框的标题
				b.setTitle("多选列表对话框");
				final boolean[] checkStatus = new boolean[] { true, false, true };
				// 为对话框设置多个列表
				b.setMultiChoiceItems(new String[] { "红色", "绿色", "蓝色" }
				// 设置默认勾选了哪些列表项
					, checkStatus
					// 为列表项的单击事件设置监听器
					, new OnMultiChoiceClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which,
							boolean isChecked)
						{
							EditText show = (EditText) findViewById(R.id.show);
							String result = "您喜欢的颜色为：";
							for (int i = 0; i < checkStatus.length; i++)
							{
								// 如果该选项被选中
								if (checkStatus[i])
								{
									result += colorNames[i] + "、";
								}
							}
							show.setText(result);
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
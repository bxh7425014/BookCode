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
public class ListDialog extends Activity
{	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bn = (Button)findViewById(R.id.bn);
		final Builder b = new AlertDialog.Builder(this);
		//为按钮绑定事件监听器
		bn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//设置对话框的图标
				b.setIcon(R.drawable.tools);
				//设置对话框的标题
				b.setTitle("简单列表对话框");
				//为对话框设置多个列表
				b.setItems(
					new String[] {"红色" , "绿色" , "蓝色"}
					//为按钮设置监听器
					, new OnClickListener() 
					{
						//该方法的which参数代表用户单击了那个列表项
						@Override
						public void onClick(DialogInterface dialog
							, int which) 
						{
							TextView show = (TextView)findViewById(R.id.show);
							//which代表哪个列表项被单击了
							switch(which)
							{
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
				//创建、并显示对话框
				b.create().show();	
			}
		});
	}
}
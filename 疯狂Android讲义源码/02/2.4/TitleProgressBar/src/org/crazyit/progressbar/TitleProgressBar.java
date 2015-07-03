package org.crazyit.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

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
public class TitleProgressBar extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		//设置窗口特征：启用显示进度的进度条
//		requestWindowFeature(Window.FEATURE_PROGRESS);
		//设置窗口特征：启用不显示进度的进度条
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		Button bn1 = (Button)findViewById(R.id.bn01);
		Button bn2 = (Button)findViewById(R.id.bn02);
		bn1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//显示不带进度的进度条。
				setProgressBarIndeterminateVisibility(true);
				//显示带进度的进度条。
				setProgressBarVisibility(true);
				//设置进度条的进度
				setProgress(4500);	
				
			}
		});
		bn2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				//隐藏不带进度的进度条。
				setProgressBarIndeterminateVisibility(false);
				//隐藏带进度的进度条。
				setProgressBarVisibility(false);
			}
		});	
	}
}
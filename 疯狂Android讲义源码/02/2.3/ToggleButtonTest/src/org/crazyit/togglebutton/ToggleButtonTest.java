package org.crazyit.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

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
public class ToggleButtonTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ToggleButton toggle = (ToggleButton)findViewById(R.id.toggle);
		final LinearLayout test = (LinearLayout)findViewById(R.id.test);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1)
				{
					if(arg1)
					{
						//设置LinearLayout垂直布局
						test.setOrientation(1);
					}
					else
					{
						//设置LinearLayout水平布局
						test.setOrientation(0);
					}			
				}
			});
	}
}
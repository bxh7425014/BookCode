package org.crazyit.relativelayout;

import android.app.Activity;
import android.os.Bundle;

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
public class RelativeLayoutTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.view01).setPadding(5, 5 , 5 , 5);
		findViewById(R.id.view02).setPadding(5, 5 , 5 , 5);
		findViewById(R.id.view03).setPadding(5, 5 , 5 , 5);
		findViewById(R.id.view04).setPadding(5, 5 , 5 , 5);
		findViewById(R.id.view05).setPadding(5, 5 , 5 , 5);
	}
}
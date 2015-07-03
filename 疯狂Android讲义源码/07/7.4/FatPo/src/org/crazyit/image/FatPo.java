package org.crazyit.image;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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
public class FatPo extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获取两个按钮
		Button play = (Button)findViewById(R.id.play);
		Button stop = (Button)findViewById(R.id.stop);
		ImageView imageView = (ImageView)findViewById(R.id.anim);
		//获取AnimationDrawable动画对象
		final AnimationDrawable anim = (AnimationDrawable)imageView
			.getBackground();
		play.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//开始播放动画
				anim.start();				
			}			
		});
		stop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//停止播放动画
				anim.stop();				
			}			
		});	
	}
}
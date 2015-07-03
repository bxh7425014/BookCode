package org.crazyit.ratingbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

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
public class RatingBarTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ImageView image = (ImageView)findViewById(R.id.image);
		RatingBar ratingBar = (RatingBar)findViewById(R.id.rating);
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{
			//当拖动条的滑块位置发生改变时触发该方法
			@Override
			public void onRatingChanged(RatingBar arg0
				, float rating, boolean fromUser)
			{
				//动态改变图片的透明度，其中255是星级评分条的最大值，
				//5个星星就代表最大值255
				image.setAlpha((int)(rating * 255 / 5));
			}
		});	
	}
}
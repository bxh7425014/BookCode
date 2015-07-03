package org.crazyit.gridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

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
public class GridViewTest extends Activity
{
	private static final String TAG = "==CrazyIt.org==";
	int[] imageIds = new int[]
	{
		R.drawable.bomb5 , R.drawable.bomb6 , R.drawable.bomb7 
		, R.drawable.bomb8 , R.drawable.bomb9 , R.drawable.bomb10
		, R.drawable.bomb11 , R.drawable.bomb12	, R.drawable.bomb13
		, R.drawable.bomb14 , R.drawable.bomb15 , R.drawable.bomb16
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建一个List对象，List对象的元素是Map
		List<Map<String, Object>> listItems 
			= new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageIds.length; i++)
		{
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image" , imageIds[i]);
			listItems.add(listItem);
		}
		//获取显示图片的ImageSwitcher
		final ImageSwitcher switcher = (ImageSwitcher)
			findViewById(R.id.switcher);
		//设置图片更换的动画效果
		switcher.setInAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_in));
		switcher.setOutAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_out));
		//为ImageSwitcher设置图片切换的动画效果
		switcher.setFactory(new ViewFactory()
		{
			@Override
			public View makeView()
			{
				ImageView imageView = new ImageView(GridViewTest.this);
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				return imageView;
			}
		});		
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this
			, listItems 
			//使用/layout/cell.xml文件作为界面布局
			, R.layout.cell
			, new String[]{"image"}
			, new int[]{R.id.image1});
		GridView grid = (GridView)findViewById(R.id.grid01);
		//为GridView设置Adapter
		grid.setAdapter(simpleAdapter);
		//添加列表项被选中的监听器
		grid.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, 
				int position , long id)
			{
				//显示当前被选中的图片
				switcher.setImageResource(imageIds[position % imageIds.length]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent){}			
		});
		//添加列表项被单击的监听器
		grid.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent
				, View view, int position, long id)
			{
				//显示被单击的图片的图片
				switcher.setImageResource(imageIds[position % imageIds.length]);
			}
		});
	}
}
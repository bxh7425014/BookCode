/**
 * 
 */
package org.crazyit.auction.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class JSONArrayAdapter extends BaseAdapter
{
	private Context ctx;
	// 定义需要包装的JSONArray对象
	private JSONArray jsonArray;
	// 定义列表项显示JSONObject对象的哪个属性
	private String property;
	private boolean hasIcon;
	public JSONArrayAdapter(Context ctx 
		, JSONArray jsonArray, String property
		, boolean hasIcon)
	{
		this.ctx = ctx;
		this.jsonArray = jsonArray;
		this.property = property;
		this.hasIcon = hasIcon;
	}

	@Override
	public int getCount()
	{
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position)
	{
		return jsonArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position)
	{
		try
		{
			// 返回物品的ID
			return ((JSONObject)getItem(position)).getInt("id");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// 定义一个线性布局管理器
		LinearLayout linear = new LinearLayout(ctx);
		// 设置为水平的线性布局管理器
		linear.setOrientation(0);
		// 创建一个ImageView
		ImageView iv = new ImageView(ctx);
		iv.setPadding(10, 0, 20, 0);
		iv.setImageResource(R.drawable.item);
		// 将图片添加到LinearLayout中
		linear.addView(iv);
		// 创建一个TextView
		TextView tv = new TextView(ctx);
		try
		{
			// 获取JSONArray数组元素的property属性
			String itemName = ((JSONObject)getItem(position))
				.getString(property);
			// 设置TextView所显示的内容
			tv.setText(itemName);		
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		tv.setTextSize(20);
		if (hasIcon)
		{
			// 将TextView添加到LinearLayout中
			linear.addView(tv);
			return linear;
		}
		else
		{
			tv.setTextColor(Color.BLACK);
			return tv;
		}
	}
}
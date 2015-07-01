package com.xmobileapp.DroidGPS;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.xmobileapp.utils.*;
public class MyAdapter extends BaseAdapter
{
  private LayoutInflater mInflater;
  private List<trackinfo> mItems;
  private int[] intRes = {R.drawable.walk,R.drawable.sport,
		  R.drawable.hiker,R.drawable.cycling,
		  R.drawable.motorcycling,R.drawable.car,
		  R.drawable.ferry,R.drawable.subway,R.drawable.airports};
  public MyAdapter(Context context,List<trackinfo> it)
  {
	/*获取布局的句柄*/
    mInflater = LayoutInflater.from(context);
    /*接收传入进来的数据*/
    mItems = it;
  }
  
  public int getCount()
  {
	if(mItems==null)
	{
		return 0;
	}
	else
	{
		return mItems.size();
	}
  }

  public Object getItem(int position)
  {
	if(mItems==null)
	{
		return null;
	}
	else
	{
		return mItems.get(position);
	}
  }
  
  public long getItemId(int position)
  {
    return position;
  }
  
  public View getView(int position,View convertView,ViewGroup parent)
  {
	  try
	  {
	    ViewHolder holder;
	    /*获取相关VIew的句柄*/
	    if(convertView == null)
	    {
	      convertView = mInflater.inflate(R.layout.track_list_element, null);
	      holder = new ViewHolder();
	      holder.iv_states = (ImageView)convertView.findViewById(R.id.iv_tle_states);
	      holder.tv_date = (TextView)convertView.findViewById(R.id.tv_tle_date);
	      holder.tv_day = (TextView)convertView.findViewById(R.id.tv_tle_day);
	      holder.tv_time = (TextView)convertView.findViewById(R.id.tv_tle_time);
	      holder.tv_distance = (TextView)convertView.findViewById(R.id.tv_tle_distance);
	      holder.tv_group = (TextView)convertView.findViewById(R.id.tv_group);
	      holder.tv_mark = (TextView)convertView.findViewById(R.id.tv_tle_marks);
	      holder.tv_points = (TextView)convertView.findViewById(R.id.tv_tle_points);
	      convertView.setTag(holder);
	    }
	    else
	    {
	      holder = (ViewHolder) convertView.getTag();
	    }
	    /*设置相应的View的值*/
	    holder.iv_states.setBackgroundResource(intRes[mItems.get(position).mBmpIndex]);
	    holder.tv_date.setText(mItems.get(position).mStrDate);
	    holder.tv_day.setText(mItems.get(position).mStrDay);
	    holder.tv_time.setText(mItems.get(position).mStrTime);
	    holder.tv_distance.setText(mItems.get(position).mStrDistance+" km");
	    holder.tv_group.setText(mItems.get(position).mStrGroup);
	    holder.tv_mark.setText(mItems.get(position).mStrMarks);
	    holder.tv_points.setText(mItems.get(position).mStrPoints+" Points");
	  }
	  catch(Exception e)
	  {
		  e.toString();
	  }
    return convertView;
  }
  private class ViewHolder
  {
	  ImageView iv_states;
	  TextView tv_date,tv_day,tv_time,tv_distance,tv_speed,tv_group,tv_mark,tv_points;
  }
}

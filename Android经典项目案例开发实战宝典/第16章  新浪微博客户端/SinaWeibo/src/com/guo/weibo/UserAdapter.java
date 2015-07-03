package com.guo.weibo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//用户信息适配器
public class UserAdapter extends BaseAdapter{  
	private List<UserInfo> userList;
	private Context mContext;
	//构造函数
	 public UserAdapter(Context context,List<UserInfo> info)
	 {
		 mContext=context;
		 userList=info;
	 }
	 //获得列表的元素个数
	@Override  
	public int getCount() {  
		return userList.size();  
	}  
	//获得指定位置的元素
	@Override  
	public Object getItem(int position) {  
		return userList.get(position);  
	}  
	//获得位置信息
	@Override  
	public long getItemId(int position) {  
		return position;  
	}  
	 //获得每个元素的视图
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		convertView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_user, null);  	  
		ImageView iv = (ImageView) convertView.findViewById(R.id.iconImg);  
		TextView tv = (TextView) convertView.findViewById(R.id.name);  
		UserInfo user = userList.get(position);  
		try {  
		//设置图片显示  
		iv.setImageDrawable(user.getUserIcon());  
		//设置信息  
		tv.setText(user.getUserName());  		  	  
		} catch (Exception e) {  
		e.printStackTrace();  
		}  
		return convertView;  
	}
}
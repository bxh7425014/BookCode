package com.guo.apkmanager;
import java.util.List; 
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//GridView的适配器
class GridViewAdapter extends BaseAdapter {
	//用于存放应用程序信息
	private List<PackageInfo> packageInfos = null;
	private LayoutInflater inflater = null;//inflater的作用是将xml文件转换成视图
	private  Context context = null;
	//构造函数，初始化变量
	public GridViewAdapter(List<PackageInfo>  packageInfos , Context context){
		this.packageInfos = packageInfos; 
		this.context = context ; 
		inflater = LayoutInflater.from(context);
	}
	//获得应用程序的个数
	@Override
	public int getCount() {
		return packageInfos.size();
	}
	//获得应用程序
	@Override
	public Object getItem(int arg0) {
		return packageInfos.get(arg0);
	}
	//获得应用程序的ID
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	//设置listView的视图
	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.gridviewitem, null);
		TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
		ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);
		//设置应用程序名称
		tv.setText(packageInfos.get(position).applicationInfo.loadLabel(context.getPackageManager()));
		//设置icon
		iv.setImageDrawable(packageInfos.get(position).applicationInfo.loadIcon(context.getPackageManager()));
		return view;
	}	
}

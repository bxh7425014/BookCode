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
//ListView的适配器
class ListViewAdapter extends BaseAdapter {
	//用于存放应用程序信息
	private List<PackageInfo> packageInfos = null;
	private LayoutInflater inflater = null;
	private  Context context = null;
	//构造函数，初始化变量
	public ListViewAdapter(List<PackageInfo>  packageInfos , Context context){
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
		View view = inflater.inflate(R.layout.listviewitem, null);
		TextView appName = (TextView) view.findViewById(R.id.lv_item_appname);
		TextView packageName = (TextView) view.findViewById(R.id.lv_item_packagename);
		ImageView iv = (ImageView) view.findViewById(R.id.lv_icon);
		//设置应用程序名称
		appName.setText(packageInfos.get(position).applicationInfo.loadLabel(context.getPackageManager()));
		//设置包名
		packageName.setText(packageInfos.get(position).packageName);
		//设置icon
		iv.setImageDrawable(packageInfos.get(position).applicationInfo.loadIcon(context.getPackageManager()));	
		return view;
	}	
}

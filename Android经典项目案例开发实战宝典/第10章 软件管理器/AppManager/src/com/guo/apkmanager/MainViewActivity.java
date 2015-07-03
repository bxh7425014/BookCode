package com.guo.apkmanager;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainViewActivity extends Activity implements Runnable {

	private GridView gridView = null;
	// 用来取得系统中所有包的信息
	private List<PackageInfo> packageInfos = null; 
	private ImageButton changeCategoryBtn = null;
	// 用户自己安装的程序的信息
	private List<PackageInfo> userPackageInfos = null;
	//用来实现系统应用与自己应用的切换
	private boolean isUserApp = true; 
	private ListView listView = null;
	private ImageButton changeViewBtn = null;
	//用来ListView、GridView的切换
	private boolean isListView = true; 
	// 当前显示的安装程序
	private List<PackageInfo> showPackageInfos = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 让title具有显示进度的功能
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		//所有程序与用户程序切换按钮
		changeCategoryBtn = (ImageButton) findViewById(R.id.ib_change_category);
		changeCategoryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isUserApp){
					//改变按钮背景图片
					changeCategoryBtn.setImageResource(R.drawable.user);
					//用户自己的应用程序
					showPackageInfos =userPackageInfos;
					//设置标志位
					isUserApp = false;
					Toast.makeText(MainViewActivity.this, "用户自己的程序", 2000).show();
				}else{
					changeCategoryBtn.setImageResource(R.drawable.all);
					//所有应用程序
					showPackageInfos = packageInfos;
					//设置标志位
					isUserApp = true;
					Toast.makeText(MainViewActivity.this, "所有的程序", 2000).show();
				}
				gridView.setAdapter(new GridViewAdapter(showPackageInfos, MainViewActivity.this));
				listView.setAdapter(new ListViewAdapter(showPackageInfos, MainViewActivity.this));
			}
		});
		listView = (ListView) findViewById(R.id.lv_apps);
		changeViewBtn = (ImageButton) findViewById(R.id.ib_change_view);
		//列表视图和网格视图切换
		changeViewBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isListView == true){
					listView.setAdapter(new ListViewAdapter(showPackageInfos, MainViewActivity.this));
					//显示列表视图
					listView.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.GONE);
					//设置标志位
					isListView = false;
					Toast.makeText(MainViewActivity.this, "当前是列表视图", 2000).show();
					changeViewBtn.setImageResource(R.drawable.list);
				}else {
					//显示网格视图
					listView.setVisibility(View.GONE);
					gridView.setVisibility(View.VISIBLE);
					//设置标志位
					isListView = true;
					Toast.makeText(MainViewActivity.this, "当前是网格视图", 2000).show();
					changeViewBtn.setImageResource(R.drawable.grids);
				}
			}
		});
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setOnItemClickListener(listener);
		listView.setOnItemClickListener(listener);
		
		Thread thread = new Thread(this);
		thread.start();
		//设置标题进度条可见
		setProgressBarIndeterminateVisibility(true);
	}
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//通过position取出对应apk的packageInfo
			final PackageInfo packageInfo = showPackageInfos.get(position);
			//创建一个Dialog用来进行选择
			AlertDialog.Builder builder = new AlertDialog.Builder(MainViewActivity.this);
			builder.setTitle("选项");
			//接收一个资源的ID
			builder.setItems(R.array.choice,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						String packageName = packageInfo.packageName;
						ActivityInfo activityInfo = packageInfo.activities[0];
						//activities数组只有在设置了PackageManager.GET_ACTIVITIES后才会被填充
						//故在获取packageInfo时要在后面加上一个或的条件。
						if(activityInfo == null) {
							Toast.makeText(MainViewActivity.this, "没有任何activity", Toast.LENGTH_SHORT).show();
							return;
						}
						String activityName = activityInfo.name;
						Intent intent = new Intent();
						//通过包名和类名来启动应用程序
						intent.setComponent(new ComponentName(packageName,activityName));
						//启动apk
						startActivity(intent);
						break;
					case 1:
						//显示apk详细信息
						showAppDetail(packageInfo);
						break;
					case 2:
						Uri packageUri = Uri.parse("package:" + packageInfo.packageName);
						Intent deleteIntent = new Intent();
						deleteIntent.setAction(Intent.ACTION_DELETE);
						deleteIntent.setData(packageUri);
						//采用这句话是为了：解决删除完应用后，程序图标仍然存在的Bug。它会调用onActivityResult方法
						startActivityForResult(deleteIntent, 0);
						break;
					}
				}
			});
			//此处设为null，因为默认就实现了关闭功能
			builder.setNegativeButton("取消", null);
			builder.create().show();
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//获得所有apk
		packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		userPackageInfos = new ArrayList<PackageInfo>();
		for(int i=0;i<packageInfos.size();i++) {
			
			PackageInfo temp = packageInfos.get(i);
			ApplicationInfo appInfo = temp.applicationInfo;
			boolean flag = false;
			if((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				flag = true;
				//FLAG_SYSTEM表明是系统apk
			} else if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				// 用户apk
				flag = true;
			}
			if(flag) {
				//添加到系统apk数组中
				userPackageInfos.add(temp);
			}
		}	
		if(isUserApp) {
			showPackageInfos = packageInfos;
		} else {
			showPackageInfos = userPackageInfos;
		}
		gridView.setAdapter(new GridViewAdapter(showPackageInfos,MainViewActivity.this));
		listView.setAdapter(new ListViewAdapter(showPackageInfos,MainViewActivity.this));		
	}
	//显示apk的详细信息
	private void showAppDetail(PackageInfo packageInfo) {	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("详细信息");
		StringBuffer message = new StringBuffer();
		message.append("程序名称:" + packageInfo.applicationInfo.loadLabel(getPackageManager()));
		message.append("\n 包名:" + packageInfo.packageName);//包名
		message.append("\n 版本号:" + packageInfo.versionCode);//版本号
		message.append("\n 版本名:" + packageInfo.versionName);//版本名		
		builder.setMessage(message.toString());
		builder.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
		builder.setPositiveButton("确定", null);//仅仅是让Dialog消失
		builder.create().show();
	}

	private final int SEARCH_APP = 0 ;
	private Handler handler = new Handler() {
		// 当消息发送过来的时候会执行下面这个方法
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what == SEARCH_APP){
				showPackageInfos = packageInfos;
				gridView.setAdapter(new GridViewAdapter(showPackageInfos, MainViewActivity.this));
				listView.setAdapter(new ListViewAdapter(showPackageInfos, MainViewActivity.this));
				//设置标题进度条不可见
				setProgressBarIndeterminateVisibility(false);
			}
		};
	};
	// 这个新开辟的线程主要用来把ListView给填充满，以避免它阻塞主线程
	@Override
	public void run() {
		// 获得系统中所有包
		packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		// 实例化用户自己安装的程序
		userPackageInfos = new ArrayList<PackageInfo>();
		for (PackageInfo temp : packageInfos) {
			boolean flag = false;
			ApplicationInfo appInfo = temp.applicationInfo;
			if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				// 更新过的系统应用程序
				flag = true;
			} else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				// 用户自己的应用程序
				flag = true;
			}
			if (flag) {
				userPackageInfos.add(temp);
			}
		}
		// 发送一个信息给主线程，让主线程把ProgressDialog给取消掉
		handler.sendEmptyMessage(SEARCH_APP);
		// 不同的操作就会有不同的参数值，该参数主要用来区分不同的操作
		//我们可以用这个值来对用户不同的操作进行区分
		
		try {// 为了看到演示效果，加上下面这句话
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
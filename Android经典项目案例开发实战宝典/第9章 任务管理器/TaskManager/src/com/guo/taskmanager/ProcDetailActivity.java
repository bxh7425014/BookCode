package com.guo.taskmanager;

import java.lang.reflect.Method;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//程序详细信息界面
public class ProcDetailActivity extends Activity {
	private static final String ATTR_PACKAGE_STATS="PackageStats";	
	private DetailProgramUtil processInfo = null;
	private static TextView textProcessName = null;
	private static TextView textProcessVersion = null;
	private static TextView textInstallDir = null;
	private static TextView textDataDir = null;
	private static TextView textPkgSize = null;
	private static TextView textPermission = null;
	private static TextView textService = null;
	private static TextView textActivity = null;
	private static Button btnKillProcess = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proc_detail);
		//获得界面元素
		//程序名称
		textProcessName = (TextView)findViewById(R.id.detail_process_name);
		//程序版本
		textProcessVersion = (TextView)findViewById(R.id.detail_process_copyright);
		//安装目录
		textInstallDir = (TextView)findViewById(R.id.detail_process_install_dir);
		//数据目录
		textDataDir = (TextView)findViewById(R.id.detail_process_data_dir);
		//包的小信息
		textPkgSize = (TextView)findViewById(R.id.detail_process_package_size);
		//权限信息
		textPermission = (TextView)findViewById(R.id.detail_process_permission);
		//服务信息
		textService = (TextView)findViewById(R.id.detail_process_service);
		//Activity信息
		textActivity = (TextView)findViewById(R.id.detail_process_activity);
		//强制结束按键
		btnKillProcess = (Button)findViewById(R.id.btn_kill_process);
		//绑定监听器
		btnKillProcess.setOnClickListener(new KillButtonListener());
		//获得传递过来的数据
		Intent intent = getIntent();
		Bundle bundle= intent.getExtras();
		processInfo = (DetailProgramUtil) bundle.getSerializable("process_info");
		//将获得的数据显示到界面
		showAppInfo();
	}
	//显示程序详细信息
	public void showAppInfo(){
		//设置程序名称
		textProcessName.setText(processInfo.getProcessName());
		//设置程序安装目录
		textInstallDir.setText(processInfo.getSourceDir());
		//设置程序版本
		textProcessVersion.setText(
				getString(R.string.detail_process_company) + processInfo.getCompanyName()
				+ "  " + getString(R.string.detail_process_version) + processInfo.getVersionName()
				+ "(" + processInfo.getVersionCode() + ")");
		//设置数据目录
		textDataDir.setText(processInfo.getDataDir());
		//设置权限
		textPermission.setText(processInfo.getUserPermissions());
		//设置服务信息
		textService.setText(processInfo.getServices());
		//设置Activity信息
		textActivity.setText(processInfo.getActivities());
		//设置包大小信息
		getpkginfo(processInfo.getPackageName());
	}
	//结束进程按键监听器
	private class KillButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			//获得活动管理器
			ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			PackageUtil packageUtil = new PackageUtil(ProcDetailActivity.this);
			//如果是本程序，则不结束
			if (processInfo.getProcessName().equals(R.string.my_package)) {
				Toast.makeText(ProcDetailActivity.this, "Canot Terminate Myself!", Toast.LENGTH_LONG).show();
				return;
			}
			//获得程序的信息类
			ApplicationInfo tempAppInfo = packageUtil.getApplicationInfo(processInfo.getProcessName());
			activityManager.killBackgroundProcesses(tempAppInfo.packageName);
			Toast.makeText(ProcDetailActivity.this, "Process is killed!", Toast.LENGTH_LONG).show();
		}
	}
		//用于更新界面的报数据带下信息
	 private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String infoString="";
					PackageStats newPs = msg.getData().getParcelable(ATTR_PACKAGE_STATS);
					if (newPs!=null) {
						infoString+="应用程序大小: "+formatFileSize(newPs.codeSize);
						infoString+="\n数据大小: "+formatFileSize(newPs.dataSize);
						infoString+="\n缓存大小: "+formatFileSize(newPs.cacheSize);
					}
					textPkgSize.setText(infoString);
					break;
				default:
					break;
				}
			}
			};
	//利用反射机制获得程序的包大小信息
			//使用普通方式将得不到包的大小信息
	public void getpkginfo(String pkg){
		PackageManager pm = getPackageManager();
		try {
			Method getPackageSizeInfo = pm.getClass()
			.getMethod("getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			getPackageSizeInfo.invoke(pm, pkg,
			new PkgSizeObserver());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//使用AIDL实现进程间通信，将包的信息发送给mHandler
	class PkgSizeObserver extends IPackageStatsObserver.Stub {
  public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) {
       Message msg = mHandler.obtainMessage(1);
       Bundle data = new Bundle();
       //将包信息存放到data中
       data.putParcelable(ATTR_PACKAGE_STATS, pStats);
       msg.setData(data);
       mHandler.sendMessage(msg);
      
   }
}
	 //格式化文件大小信息
	public static String formatFileSize(long length) {
		String result = null;
		int sub_string = 0;
		//文件是GB级别的情况
		if (length >= 1073741824) {
			sub_string = String.valueOf((float) length / 1073741824).indexOf(
					".");
			result = ((float) length / 1073741824 + "000").substring(0,
					sub_string + 3)
					+ "GB";
			//文件是MB级别的情况
		} else if (length >= 1048576) {
			sub_string = String.valueOf((float) length / 1048576).indexOf(".");
			result = ((float) length / 1048576 + "000").substring(0,
					sub_string + 3)
					+ "MB";
			//文件是KB级别的情况
		} else if (length >= 1024) {
			sub_string = String.valueOf((float) length / 1024).indexOf(".");
			result = ((float) length / 1024 + "000").substring(0,
					sub_string + 3)
					+ "KB";
			//文件是B级别的情况
		} else if (length < 1024)
			result = Long.toString(length) + "B";
		return result;
	}
}

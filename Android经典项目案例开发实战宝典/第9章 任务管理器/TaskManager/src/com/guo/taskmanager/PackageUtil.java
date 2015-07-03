package com.guo.taskmanager;
import java.util.List;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
public class PackageUtil {
	/* ApplicationInfo 类，保存了普通应用程序的信息，
	 *主要是指Manifest.xml中application标签中的信息
	 **/
	private List<ApplicationInfo> allAppList;
	//构造函数
	public PackageUtil(Context context) {
		//获得通过包管理器
		PackageManager pm = context.getApplicationContext().getPackageManager();
		//获取所有应用程序，包括那些被卸载掉，但仍保留数据目录的程序
		allAppList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
	}	
	/**
	 * 通过一个程序名返回该程序的一个ApplicationInfo对象
	 * @param name 程序名
	 * @return ApplicationInfo
	 */
	public ApplicationInfo getApplicationInfo(String appName) {
		if (appName == null) {
			return null;
		}
		//遍历，返回对应的应用程序信息
		for (ApplicationInfo appinfo : allAppList) {
			if (appName.equals(appinfo.processName)) {
				return appinfo;
			}
		}
		return null;
	}
}

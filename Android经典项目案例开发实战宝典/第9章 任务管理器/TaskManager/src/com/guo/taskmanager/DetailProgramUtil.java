package com.guo.taskmanager;

import java.io.Serializable;
import android.content.pm.ActivityInfo;
import android.content.pm.ServiceInfo;
import android.text.format.DateFormat;
public class DetailProgramUtil implements Serializable{
	private static final long serialVersionUID = 1L;
	/*
	 * 定义应用程序的扩展信息部分
	 */

	private String packageName;						// 包名
	private int pid;								// 程序的进程id
	private String processName;						// 程序运行的进程名
	
	private String companyName;						// 公司名称
	private int versionCode;						// 版本代号
	private String versionName;						// 版本名称
	
	private String dataDir;							// 程序的数据目录
	private String sourceDir;						// 程序包的源目录
	
	private String firstInstallTime;				// 第一次安装的时间
	private String lastUpdateTime;					// 最近的更新时间
	
	private String userPermissions;					// 应用程序的权限
	private String activities;						// 应用程序包含的Activities
	private String services;						// 应用程序包含的服务
	
	// android.content.pm.PackageState类的包信息
	//构造函数，初始化数据
	public DetailProgramUtil() {
		pid = 0;
		processName = "";
		companyName = "";
		versionCode = 0;
		versionName = "";
		dataDir = "";
		sourceDir = "";
		firstInstallTime = "";
		lastUpdateTime = "";
		userPermissions = "";
		activities = "";
		services = "";
	}
	//获得进程id
	public int getPid() {
		return pid;
	}
	//设置进程id
	public void setPid(int pid) {
		this.pid = pid;
	}
	//获得版本号
	public int getVersionCode() {
		return versionCode;
	}
	//设置版本号
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	//获得版本信息
	public String getVersionName() {
		return versionName;
	}
	//设置版本信息
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	//获得公司名称
	public String getCompanyName() {
		return companyName;
	}
	//设置公司名称
	public void setCompanyName(String companyString) {
		this.companyName = companyString;
	}
	//获得初次安装时间
	public String getFirstInstallTime() {
		if (firstInstallTime == null || firstInstallTime.length() <= 0) {
			firstInstallTime = "null";
		}
		return firstInstallTime;
	}
	//设置初次安装时间
	public void setFirstInstallTime(long firstInstallTime) {
		this.firstInstallTime = DateFormat.format(
				"yyyy-MM-dd", firstInstallTime).toString();
	}
	//获得最后更新时间
	public String getLastUpdateTime() {
		if (lastUpdateTime == null || lastUpdateTime.length() <= 0) {
			lastUpdateTime = "null";
		}
		return lastUpdateTime;
	}
	//设置最后更新时间
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = DateFormat.format(
				"yyyy-MM-dd", lastUpdateTime).toString();
	}
	//获得活动
	public String getActivities() {
		if (activities == null || activities.length() <= 0) {
			activities = "null";
		}
		return activities;
	}
	//设置活动
	public void setActivities(ActivityInfo[] activities) {
		this.activities = Array2String(activities);
	}
	//获得用户权限
	public String getUserPermissions() {
		if (userPermissions == null || userPermissions.length() <= 0) {
			userPermissions = "null";
		}
		return userPermissions;
	}
	//设置用户权限
	public void setUserPermissions(String[] userPermissions) {
		this.userPermissions = Array2String(userPermissions);	
	}
	//获得服务
	public String getServices() {
		if (services == null || services.length() <= 0) {
			services = "null";
		}
		return services;
	}
	//设置服务
	public void setServices(ServiceInfo[] services) {
		this.services = Array2String(services);
	}
	//获得进程名称
	public String getProcessName() {
		if (processName == null || processName.length() <= 0) {
			processName = "null";
		}
		return processName;
	}
	//设置进程名称
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	//获得数据目录
	public String getDataDir() {
		if (dataDir == null || dataDir.length() <= 0) {
			dataDir = "null";
		}
		return dataDir;
	}
	//设置数据目录
	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}
	//获得缓存的目录
	public String getSourceDir() {
		if (sourceDir == null || sourceDir.length() <= 0) {
			sourceDir = "null";
		}
		return sourceDir;
	}
	//设置源路径
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
	//设置包名
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	//获得包名
	public String getPackageName() {
		return packageName;
	}
	/*
	 * 三个重载方法，参数不同，调用不同的方法，用于将object数组转化成要求的字符串
	 */
	// 用户权限信息
    public String Array2String(String[] array) {
    	
    	String resultString = "";
    	if (array != null && array.length > 0) {
    		resultString = "";
    		//遍历数组，使用换行符拼接数据
			for (int i = 0; i < array.length; i++) {
				resultString += array[i];
				if (i < (array.length - 1)) {
					resultString += "\n";
				}
			}
		}
		return resultString;
    }
    
    // 服务信息
    public String Array2String(ServiceInfo[] array) {
    	String resultString = "";
    	//遍历数组，使用换行符拼接数据
    	if (array != null && array.length > 0) {
    		resultString = "";
			for (int i = 0; i < array.length; i++) {
				if (array[i].name == null) {
					continue;
				}
				resultString += array[i].name.toString();
				if (i < (array.length - 1)) {
					resultString += "\n";
				}
			}
		}
		return resultString;
    }
    
    // 活动信息
    public String Array2String(ActivityInfo[] array) {
    	String resultString = "";
    	//遍历数组，使用换行符拼接数据
    	if (array != null && array.length > 0) {
    		resultString = "";
			for (int i = 0; i < array.length; i++) {
				if (array[i].name == null) {
					continue;
				}
				resultString += array[i].name.toString();
				if (i < (array.length - 1)) {
					resultString += "\n";
				}
			}
		}
    	return resultString;
    }

}
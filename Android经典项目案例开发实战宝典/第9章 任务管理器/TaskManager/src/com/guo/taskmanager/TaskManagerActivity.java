package com.guo.taskmanager;
/**
 * ActivityManager.RunningAppProcessInfo {
 *     public int importance				// 进程在系统中的重要级别
 *     public int importanceReasonCode		// 进程的重要原因代码
 *     public ComponentName importanceReasonComponent	// 进程中组件的描述信息
 *     public int importanceReasonPid		// 当前进程的子进程Id
 *     public int lru						// 在同一个重要级别内的附加排序值
 *     public int pid						// 当前进程Id
 *     public String[] pkgList				// 被载入当前进程的所有包名
 *     public String processName			// 当前进程的名称
 *     public int uid						// 当前进程的用户Id
 * }
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TaskManagerActivity extends ListActivity {
	
	// 获取包管理器和活动管理器的实例
	private static PackageManager packageManager = null;
	private static ActivityManager activityManager = null;

	// 正在运行的进程列表
	private static List<RunningAppProcessInfo> runningProcessList = null;
	private static List<ProgramUtil> infoList = null;
	//用于获得应用程序基本信息
	private static PackageUtil packageUtil = null;
	
	// 被选中的进程名称
	private static RunningAppProcessInfo processSelected = null;	
	//刷新按钮
	private static Button btnRefresh = null;
	//结束进程按钮
	private static Button btnCloseAll = null;
	
	// 用于后台刷新列表，显示刷新提示
	private static RefreshHandler handler = null;
	// 用于显示刷新进度条
	private static ProgressDialog pd = null;
	// ListView的适配器
	private static ProcListAdapter procListAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proc_list);
        
        btnRefresh = (Button)findViewById(R.id.btn_refresh_process);
        btnRefresh.setOnClickListener(new RefreshButtonListener());
        btnCloseAll = (Button)findViewById(R.id.btn_closeall_process);
        btnCloseAll.setOnClickListener(new CloseAllButtonListener());
        
        // 获取包管理器，主要通过包管理器获取程序的图标和程序名
    	packageManager = this.getPackageManager();
		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		packageUtil = new PackageUtil(this);
        
		// 获取正在运行的进程列表		
		runningProcessList = new ArrayList<RunningAppProcessInfo>();
    	infoList = new ArrayList<ProgramUtil>();
    	
        updateProcessList();
    }
    //获得进程占用内存信息
	public String getUsedMemory(int pid)
	{
		//获得活动管理器实例
		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		//构建int数组
		int[] pids = {pid};
		MemoryInfo[] memoryInfos =  am.getProcessMemoryInfo(pids);
		//获得进程占用内存总量，返回kB值
		int memorysize = memoryInfos[0].getTotalPrivateDirty();
		return "内存占用为 "+ memorysize +" KB";
	}
    private void updateProcessList() {
    	// 新建一个进度对话框，在刷新列表时，覆盖在父视图之上    	
    	pd = new ProgressDialog(TaskManagerActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle(getString(R.string.progress_tips_title));
        pd.setMessage(getString(R.string.progress_tips_content));    
        // 启动新线程，执行更新列表操作
		handler = new RefreshHandler();
		RefreshThread thread = new RefreshThread();
		thread.start();	
		// 显示进度对话框
        pd.show();
    }
    
    private class RefreshHandler extends Handler {
    	@Override
		public void handleMessage(Message msg) {
			// 更新界面
    		getListView().setAdapter(procListAdapter);
			// 取消进度框
    		pd.dismiss();
		}
    }
    //用于更新界面的进程
    class RefreshThread extends Thread {
		@Override
		public void run() {
			// TODO : Do your Stuff here.
			procListAdapter = buildProcListAdapter();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
	}
    //列表按键监听器
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//获得当前选中的进程
    	processSelected = runningProcessList.get(position);
    	//新建对话框
    	AlertButtonListener listener = new AlertButtonListener();
    	Dialog alertDialog = new AlertDialog.Builder(this)
    		.setIcon(android.R.drawable.ic_dialog_info)
    		.setTitle(R.string.please_select)
    		.setNegativeButton(R.string.kill_process, listener)
    		.setNeutralButton(R.string.info_detail, listener).create();
    	alertDialog.show();
    	super.onListItemClick(l, v, position, id);
	}
    
    private class AlertButtonListener implements 
    		android.content.DialogInterface.OnClickListener {
    	//按键处理
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case Dialog.BUTTON_NEUTRAL:
				Intent intent = new Intent();
				intent.setClass(TaskManagerActivity.this, ProcDetailActivity.class);
				// 为选中的进程获取安装包的详细信息
				DetailProgramUtil programUtil = buildProgramUtilComplexInfo(processSelected.processName);
				if (programUtil == null) {
					break;
				}
				Bundle bundle = new Bundle();
				// 使用Serializable在Activity之间传递对象
				bundle.putSerializable("process_info", (Serializable)programUtil);
				intent.putExtras(bundle);
				//打开进程详细信息界面
				startActivity(intent);				
				break;
			case Dialog.BUTTON_NEGATIVE:
				//结束进程
				closeOneProcess(processSelected.processName);
				//更新界面
				updateProcessList();
				break;
			default:
				break;
			}
		}
    }
    //更新列表
    private class RefreshButtonListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			updateProcessList();
		}
    }
    //关闭所有用户程序
    private class CloseAllButtonListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			int count = infoList.size();
			ProgramUtil bpu = null;
			//遍历所有进程，逐个关闭
			for (int i = 0; i < count; i++) {
				bpu = infoList.get(i);
				closeOneProcess(bpu.getProcessName());
			}
			//更新列表
			updateProcessList();
		}
    }
    //关闭指定进程
    private void closeOneProcess(String processName) {
    	//阻止用户结束本程序
		if (processName.equals(R.string.my_package)) {		
			Toast.makeText(TaskManagerActivity.this, "Canot Terminate Myself!", Toast.LENGTH_LONG).show();
			return;
		}
		//通过一个程序名返回该程序的一个ApplicationInfo对象
		ApplicationInfo tempAppInfo = packageUtil.getApplicationInfo(processName);
		if (tempAppInfo == null) {
			return;
		}
		//根据包名关闭进程
		activityManager.killBackgroundProcesses(tempAppInfo.packageName);
    }
	//构建一个ListAdapter
	public ProcListAdapter buildProcListAdapter() {
		//清空正在运行的程序
		if (!runningProcessList.isEmpty()) {
			runningProcessList.clear();	
		}
		//清空存放运行程序信息的数组
		if (!infoList.isEmpty()) {
			infoList.clear();
		}
		// 获取正在运行的进程
    	runningProcessList = activityManager.getRunningAppProcesses();
    	RunningAppProcessInfo procInfo = null;
    	for (Iterator<RunningAppProcessInfo> iterator = runningProcessList.iterator(); iterator.hasNext();) {
    		procInfo = iterator.next();
    		//将程序的信息存储到类中
    		ProgramUtil programUtil = buildProgramUtilSimpleInfo(procInfo.pid, procInfo.processName);
    		//将程序信息添加到数组中
    		infoList.add(programUtil);
		}
    	
    	ProcListAdapter adapter = new ProcListAdapter(infoList, this);
    	return adapter;
	}

	/*
	 * 为进程获取基本的信息
	 */
    public ProgramUtil buildProgramUtilSimpleInfo(int procId, String procNameString) {

		ProgramUtil programUtil = new ProgramUtil();
		programUtil.setProcessName(procNameString);
		
		//根据进程名，获取应用程序的ApplicationInfo对象
		ApplicationInfo tempAppInfo = packageUtil.getApplicationInfo(procNameString);

		if (tempAppInfo != null) {
			//为进程加载图标和程序名称
			programUtil.setIcon(tempAppInfo.loadIcon(packageManager));
    		programUtil.setProgramName(tempAppInfo.loadLabel(packageManager).toString());
		} 
		else {
			//如果获取失败，则使用默认的图标和程序名
			programUtil.setIcon(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
			programUtil.setProgramName(procNameString);
		}
		//设置进程内存使用量
		String str = getUsedMemory(procId);
		programUtil.setMemString(str);
		return programUtil;
    }

	/*
	 * 为进程获取安装包的详情
	 */
    public DetailProgramUtil buildProgramUtilComplexInfo(String procNameString) {

    	DetailProgramUtil complexProgramUtil = new DetailProgramUtil();
		// 根据进程名，获取应用程序的ApplicationInfo对象
		ApplicationInfo tempAppInfo = packageUtil.getApplicationInfo(procNameString);
		if (tempAppInfo == null) {
			return null;
		}
		
		PackageInfo tempPkgInfo = null;
		try {
			tempPkgInfo = packageManager.getPackageInfo(
					tempAppInfo.packageName, 
					PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES
					| PackageManager.GET_SERVICES | PackageManager.GET_PERMISSIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (tempPkgInfo == null) {
			return null;
		}
		
		complexProgramUtil.setProcessName(procNameString);
		complexProgramUtil.setCompanyName(getString(R.string.no_use));
		complexProgramUtil.setVersionName(tempPkgInfo.versionName);
		complexProgramUtil.setVersionCode(tempPkgInfo.versionCode);
		complexProgramUtil.setDataDir(tempAppInfo.dataDir);
		complexProgramUtil.setSourceDir(tempAppInfo.sourceDir);
		complexProgramUtil.setPackageName(tempPkgInfo.packageName);
		// 获取以下三个信息，需要为PackageManager进行授权(packageManager.getPackageInfo()方法)
		complexProgramUtil.setUserPermissions(tempPkgInfo.requestedPermissions);
		complexProgramUtil.setServices(tempPkgInfo.services);
		complexProgramUtil.setActivities(tempPkgInfo.activities);
		
		return complexProgramUtil;
    }
    

}
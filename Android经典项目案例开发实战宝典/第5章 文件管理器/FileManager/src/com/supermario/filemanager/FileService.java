package com.supermario.filemanager;
import java.io.File;
import java.util.ArrayList;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
public class FileService extends Service {	
	private Looper mLooper;
	private FileHandler mFileHandler;
	private ArrayList<String> mFileName = null;
	private ArrayList<String> mFilePaths = null;
	public static final String FILE_SEARCH_COMPLETED = "com.supermario.file.FILE_SEARCH_COMPLETED";
	public static final String FILE_NOTIFICATION = "com.supermario.file.FILE_NOTIFICATION";	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}	
	//创建服务
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("FileService", "file service is onCreate");
		//新建处理线程
		HandlerThread mHT = new HandlerThread("FileService",HandlerThread.NORM_PRIORITY);
		mHT.start();
		mLooper = mHT.getLooper();
		mFileHandler = new FileHandler(mLooper);
	}
	//服务开始
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d("FileService", "file service is onStart");
		mFileName = new ArrayList<String>();
		mFilePaths = new ArrayList<String>();
		mFileHandler.sendEmptyMessage(0);
		//发出通知表明正在进行搜索
		fileSearchNotification();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//取消通知
		mNF.cancel(R.string.app_name);
	}	
	class FileHandler extends Handler{		
		public FileHandler(Looper looper){
			super(looper);
		}		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.d("FileService", "file service is handleMessage");
			//在指定范围搜索
			initFileArray(new File(SearchBroadCast.mServiceSearchPath));
			//当用户点击了取消搜索则不发生广播
			if(!MainActivity.isComeBackFromNotification == true){
				Intent intent = new Intent(FILE_SEARCH_COMPLETED);
				intent.putStringArrayListExtra("mFileNameList", mFileName);
				intent.putStringArrayListExtra("mFilePathsList", mFilePaths);
				//搜索完毕之后携带数据并发送广播
				sendBroadcast(intent);
			}
		}	
	}	
	private int m = -1;
    /**具体做搜索事件的可回调函数*/
    private void initFileArray(File file){
    	Log.d("FileService", "currentArray is "+file.getPath());
    	//只能遍历可读的文件夹，否则会报错
    	if(file.canRead()){
    		File[] mFileArray = file.listFiles();
        	for(File currentArray:mFileArray){
        		if(currentArray.getName().indexOf(SearchBroadCast.mServiceKeyword) != -1){
        			if (m == -1) {
						m++;
						// 返回搜索之前目录
						mFileName.add("BacktoSearchBefore");
						mFilePaths.add(MainActivity.mCurrentFilePath);
					}
        			mFileName.add(currentArray.getName());
        			mFilePaths.add(currentArray.getPath());
        		}
        		//如果是文件夹则回调该方法
        		if(currentArray.exists()&&currentArray.isDirectory()){
        			//如果用户取消了搜索，应该停止搜索的过程
        			if(MainActivity.isComeBackFromNotification == true){
        				return;
        			}
        			initFileArray(currentArray);
        		}
        	}
    	}                                                                                                                                                                                                                                                                                                                                                                                            
    }    
    NotificationManager mNF;
    /**通知*/
    private void fileSearchNotification(){
    	Notification mNotification = new Notification(R.drawable.logo,"后台搜索中...",System.currentTimeMillis());
    	Intent intent = new Intent(FILE_NOTIFICATION);
    	//打开notice时的提示内容
    	intent.putExtra("notification", "当通知还存在，说明搜索未完成，可以在这里触发一个事件，当点击通知回到Activity之后，可以弹出一个框，提示是否取消搜索!");
    	PendingIntent mPI = PendingIntent.getBroadcast(this,0,intent,0);
    	mNotification.setLatestEventInfo(this, "在"+SearchBroadCast.mServiceSearchPath+"下搜索", "搜索关键字为"+SearchBroadCast.mServiceKeyword+"【点击可取消搜索】", mPI);
    	if(mNF == null){
    		mNF = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	}
    	mNF.notify(R.string.app_name, mNotification);
    }  
}
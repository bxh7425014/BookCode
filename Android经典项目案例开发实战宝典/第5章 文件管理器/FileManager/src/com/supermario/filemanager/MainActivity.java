package com.supermario.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends ListActivity implements OnItemLongClickListener{
    // 声明成员变量：
	//存放显示的文件列表的名称
	private List<String> mFileName = null;
	//存放显示的文件列表的相对应的路径
	private List<String> mFilePaths = null;
	//起始目录“/” 
	private String mRootPath = java.io.File.separator;
	// SD卡根目录
	private String mSDCard = Environment.getExternalStorageDirectory().toString();
	private String mOldFilePath = "";
	private String mNewFilePath = "";
	private String keyWords;
	//用于显示当前路径
	private TextView mPath;
	//用于放置工具栏
	private GridView mGridViewToolbar;
	private int[] girdview_menu_image = {R.drawable.menu_phone,R.drawable.menu_sdcard,R.drawable.menu_search,
														R.drawable.menu_create,R.drawable.menu_palse,R.drawable.menu_exit};
	private String[] gridview_menu_title = {"手机","SD卡","搜索","创建","粘贴","退出"};
	// 代表手机或SD卡，1代表手机，2代表SD卡
	private static int menuPosition = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //初始化菜单视图
        initGridViewMenu();
        //初始化菜单监听器
        initMenuListener();
        //为列表项绑定长按监听器
        getListView().setOnItemLongClickListener(this);
        mPath = (TextView)findViewById(R.id.mPath);
        //一开始程序的时候加载手机目录下的文件列表
		 initFileListInfo(mRootPath);
    }
    
    /**为GridView配饰菜单资源*/
    private void initGridViewMenu(){
    	 mGridViewToolbar = (GridView)findViewById(R.id.file_gridview_toolbar);
    	 //设置选中时候的背景图片
         mGridViewToolbar.setSelector(R.drawable.menu_item_selected);
         //设置背景图片
         mGridViewToolbar.setBackgroundResource(R.drawable.menu_background);
         //设置列数
         mGridViewToolbar.setNumColumns(6);
         //设置剧中对齐
         mGridViewToolbar.setGravity(Gravity.CENTER);
         //设置水平，垂直间距为10
         mGridViewToolbar.setVerticalSpacing(10);
         mGridViewToolbar.setHorizontalSpacing(10);
         //设置适配器
         mGridViewToolbar.setAdapter(getMenuAdapter(gridview_menu_title,girdview_menu_image));
    }
    
    /**菜单适配器*/
    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
    	//数组列表用于存放映射表
		ArrayList<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> mMap = new HashMap<String, Object>();
			//将“image”映射成图片资源
			mMap.put("image", imageResourceArray[i]);
			//将“title”映射成标题
			mMap.put("title", menuNameArray[i]);		
			mData.add(mMap);
		}
		//新建简单适配器，设置适配器的布局文件，映射关系
		SimpleAdapter mAdapter = new SimpleAdapter(this, mData,R.layout.item_menu, new String[] { "image", "title" },new int[] { R.id.item_image, R.id.item_text });
		return mAdapter;
	}
    
    /**菜单项的监听*/
    protected void initMenuListener(){
    	mGridViewToolbar.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				//回到根目录
				case 0:
					menuPosition = 1;
					 initFileListInfo(mRootPath);
					break;
				//回到SD卡根目录
				case 1:
					menuPosition = 2;
			        initFileListInfo(mSDCard);
					break;
				//显示搜索对话框
				case 2:
					searchDilalog();
					break;
				//创建文件夹
				case 3:
					createFolder();
					break;
				//粘贴文件
				case 4:
					palseFile();
					break;
				//退出
				case 5:
					MainActivity.this.finish();
					break;
				}
			}  		
    	});
    }
    /**粘贴*/
    private void palseFile(){
    	mNewFilePath = mCurrentFilePath+java.io.File.separator+mCopyFileName;//得到新路径
		Log.d("copy", "mOldFilePath is "+mOldFilePath+"| mNewFilePath is "+mNewFilePath+"| isCopy is "+isCopy);
		if(!mOldFilePath.equals(mNewFilePath)&&isCopy == true){//在不同路径下复制才起效
			if(!new File(mNewFilePath).exists()){
				copyFile(mOldFilePath,mNewFilePath);
				Toast.makeText(MainActivity.this, "执行了粘贴", Toast.LENGTH_SHORT).show();
				initFileListInfo(mCurrentFilePath);
			}else{
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("提示!")
				.setMessage("该文件名已存在，是否要覆盖?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int which){
						copyFile(mOldFilePath,mNewFilePath);
						initFileListInfo(mCurrentFilePath);
					}
				})
				.setNegativeButton("取消", null).show();
			}
		}else{
			Toast.makeText(MainActivity.this, "未复制文件！", Toast.LENGTH_LONG).show();
		}
    }
    //用静态变量存储 当前目录路径信息
    public static String mCurrentFilePath = "";
    /**根据给定的一个文件夹路径字符串遍历出这个文
     * 件夹中包含的文件名称并配置到ListView列表中*/
    private void initFileListInfo(String filePath){
    	isAddBackUp = false;
    	mCurrentFilePath = filePath;
    	//显示当前的路径
    	mPath.setText(filePath);
    	mFileName = new ArrayList<String>();
    	mFilePaths = new ArrayList<String>();
    	File mFile = new File(filePath);
    	//遍历出该文件夹路径下的所有文件/文件夹
    	File[] mFiles = mFile.listFiles();
    	//只要当前路径不是手机根目录或者是sd卡根目录则显示“返回根目录”和“返回上一级”
    	if(menuPosition == 1&&!mCurrentFilePath.equals(mRootPath)){
    		initAddBackUp(filePath,mRootPath);
    	}else if(menuPosition == 2&&!mCurrentFilePath.equals(mSDCard)){
        	initAddBackUp(filePath,mSDCard);
    	}
    	
    	/*将所有文件信息添加到集合中*/
    	for(File mCurrentFile:mFiles){
    		mFileName.add(mCurrentFile.getName());
    		mFilePaths.add(mCurrentFile.getPath());
    	}
    	
    	/*适配数据*/
    	setListAdapter(new FileAdapter(MainActivity.this,mFileName,mFilePaths));
    }
    
    private boolean isAddBackUp = false;
    /**根据点击“手机”还是“SD卡”来加“返回根目录”和“返回上一级”*/
    private void initAddBackUp(String filePath,String phone_sdcard){
    	
    	if(!filePath.equals(phone_sdcard)){
    		/*列表项的第一项设置为返回根目录*/
    		mFileName.add("BacktoRoot");
    		mFilePaths.add(phone_sdcard);
    		/*列表项的第二项设置为返回上一级*/
    		mFileName.add("BacktoUp");
    		//回到当前目录的父目录即回到上级
    		mFilePaths.add(new File(filePath).getParent());
    		//将添加返回按键标识位置为true
    		isAddBackUp = true;
    	}
    	
    }
    
    private String mNewFolderName = "";
    private File mCreateFile;
    private RadioGroup mCreateRadioGroup;
    private static int mChecked;
    /**创建文件夹的方法:当用户点击软件下面的创建菜单的时候，是在当前目录下创建的一个文件夹
     * 静态变量mCurrentFilePath存储的就是当前路径
     * java.io.File.separator是JAVA给我们提供的一个File类中的静态成员，它会根据系统的不同来创建分隔符
     * mNewFolderName正是我们要创建的新文件的名称，从EditText组件上得到的*/
    private void createFolder(){
    	//用于标识当前选中的是文件或者文件夹
    	mChecked = 2;
    	LayoutInflater mLI = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//初始化对话框布局
    	final LinearLayout mLL = (LinearLayout)mLI.inflate(R.layout.create_dialog, null);
    	mCreateRadioGroup = (RadioGroup)mLL.findViewById(R.id.radiogroup_create);
    	final RadioButton mCreateFileButton = (RadioButton)mLL.findViewById(R.id.create_file);
    	final RadioButton mCreateFolderButton = (RadioButton)mLL.findViewById(R.id.create_folder);
    	//设置默认为创建文件夹
    	mCreateFolderButton.setChecked(true);
    	//为按钮设置监听器
    	mCreateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
    		//当选择改变时触发
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if(arg1 == mCreateFileButton.getId()){
					mChecked = 1;
				}else if(arg1 == mCreateFolderButton.getId()){
					mChecked = 2;
				}
			}   		
    	});
    	//显示对话框
    	Builder mBuilder = new AlertDialog.Builder(MainActivity.this)
    	.setTitle("新建")
    	.setView(mLL)
    	.setPositiveButton("创建", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				//或者用户输入的名称
				mNewFolderName = ((EditText)mLL.findViewById(R.id.new_filename)).getText().toString();
				if(mChecked == 1){
					try {
						mCreateFile = new File(mCurrentFilePath+java.io.File.separator+mNewFolderName+".txt");
						mCreateFile.createNewFile();
						//刷新当前目录文件列表
						initFileListInfo(mCurrentFilePath);
					} catch (IOException e) {
						Toast.makeText(MainActivity.this, "文件名拼接出错..!!", Toast.LENGTH_SHORT).show();
					}
				}else if(mChecked == 2){
					mCreateFile = new File(mCurrentFilePath+java.io.File.separator+mNewFolderName);
					if(!mCreateFile.exists()&&!mCreateFile.isDirectory()&&mNewFolderName.length() != 0){
						if(mCreateFile.mkdirs()){
							//刷新当前目录文件列表
							initFileListInfo(mCurrentFilePath);
						}else{
							Toast.makeText(MainActivity.this, "创建失败，可能是系统权限不够，root一下？！", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(MainActivity.this, "文件名为空，还是重名了呢？", Toast.LENGTH_SHORT).show();
					}
				}			
			}
    	}).setNeutralButton("取消", null);
    	mBuilder.show();
    }
    
    EditText mET;
    //显示重命名对话框
    private void initRenameDialog(final File file){
    	LayoutInflater mLI = LayoutInflater.from(MainActivity.this);
    	//初始化重命名对话框
    	LinearLayout mLL = (LinearLayout)mLI.inflate(R.layout.rename_dialog, null);
    	mET = (EditText)mLL.findViewById(R.id.new_filename);
    	//显示当前的文件名
    	mET.setText(file.getName());
    	//设置监听器
    	OnClickListener listener = new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog,int which){
    			String modifyName = mET.getText().toString();
    			final String modifyFilePath = file.getParentFile().getPath()+java.io.File.separator;
    			final String newFilePath = modifyFilePath+modifyName;
    			//判断该新的文件名是否已经在当前目录下存在
    			if(new File(newFilePath).exists()){
    				if(!modifyName.equals(file.getName())){//把“重命名”操作时没做任何修改的情况过滤掉
    					//弹出该新命名后的文件已经存在的提示，并提示接下来的操作
    					new AlertDialog.Builder(MainActivity.this)
						.setTitle("提示!")
						.setMessage("该文件名已存在，是否要覆盖?")
						.setPositiveButton("确定", new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog,int which){
								file.renameTo(new File(newFilePath));
								Toast.makeText(MainActivity.this,
							"the file path is "+new File(newFilePath), Toast.LENGTH_SHORT).show();
								//更新当前目录信息
								initFileListInfo(file.getParentFile().getPath());
							}
						})
						.setNegativeButton("取消", null).show();
    				}
    			}else{
    				//文件名不重复时直接修改文件名后再次刷新列表
    				file.renameTo(new File(newFilePath));
					initFileListInfo(file.getParentFile().getPath());
    			}
    		}
    		
    	};
    	//显示对话框
    	AlertDialog renameDialog = new AlertDialog.Builder(MainActivity.this).create();
    	renameDialog.setView(mLL);
    	renameDialog.setButton("确定", listener);
    	renameDialog.setButton2("取消", new DialogInterface.OnClickListener(){		
    		public void onClick(DialogInterface dialog,int which){
    		//什么都不做，关闭当前对话框
    		}		
    	});
    	renameDialog.show();
    }
    
    //弹出删除文件/文件夹的对话框
    private void initDeleteDialog(final File file){
    	new AlertDialog.Builder(MainActivity.this)
    								.setTitle("提示!")
    								.setMessage("您确定要删除该"+(file.isDirectory()?"文件夹":"文件")+"吗?")
    								.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    									public void onClick(DialogInterface dialog,int which){
    										if(file.isFile()){
    											//是文件则直接删除
    											file.delete();
    										}else{
    											//是文件夹则用这个方法删除
    											deleteFolder(file);
    										}
    										//重新遍历该文件的父目录
    						    			initFileListInfo(file.getParent());
    						    		}
    								})
    								.setNegativeButton("取消", null).show();
    }
    
    //删除文件夹的方法（递归删除该文件夹下的所有文件）
	public void deleteFolder(File folder){
		File[] fileArray = folder.listFiles();
		if(fileArray.length == 0){
			//空文件夹则直接删除
			folder.delete();
		}else{
			//遍历该目录
			for(File currentFile:fileArray){
				if(currentFile.exists()&&currentFile.isFile()){
					//文件则直接删除
					currentFile.delete();
				}else{
					//递归删除
					deleteFolder(currentFile);
				}
			}
			folder.delete();
		}
	} 
	
    /**调用系统的方法，来打开文件的方法*/
    private void openFile(File file){
    	if(file.isDirectory()){
    		initFileListInfo(file.getPath());
    	}else{
    		Intent intent = new Intent();
        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	intent.setAction(android.content.Intent.ACTION_VIEW);
        	//设置当前文件类型
        	intent.setDataAndType(Uri.fromFile(file), getMIMEType(file));
        	startActivity(intent);
    	}
    }
    /**获得MIME类型的方法*/
    private String getMIMEType(File file){
    	String type = "";
    	String fileName = file.getName();
    	//取出文件后缀名并转成小写
    	String  fileEnds = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();
    	if(fileEnds.equals("m4a")||fileEnds.equals("mp3")||fileEnds.equals("mid")||fileEnds.equals("xmf")||fileEnds.equals("ogg")||fileEnds.equals("wav")){
    		type = "audio/*";// 系统将列出所有可能打开音频文件的程序选择器
    	}else if(fileEnds.equals("3gp")||fileEnds.equals("mp4")){
    		type = "video/*";// 系统将列出所有可能打开视频文件的程序选择器
    	}else if(fileEnds.equals("jpg")||fileEnds.equals("gif")||fileEnds.equals("png")||fileEnds.equals("jpeg")||fileEnds.equals("bmp")){
    		type = "image/*";// 系统将列出所有可能打开图片文件的程序选择器
    	}else{
    		type = "*/*"; // 系统将列出所有可能打开该文件的程序选择器
    	}
    	return type;
    }
    
    //长按列表项的事件监听:对长按需要进行一个控制，当列表中包括”返回根目录“和”返回上一级“时，需要对这两列进行屏蔽
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
		if(isAddBackUp == true){//说明存在返回根目录和返回上一级两列，接下来要对这两列进行屏蔽
			if(position != 0 && position != 1){
				initItemLongClickListener(new File(mFilePaths.get(position)));
			}
		}
		if(mCurrentFilePath.equals(mRootPath)||mCurrentFilePath.equals(mSDCard)){
			initItemLongClickListener(new File(mFilePaths.get(position)));
		}
		return false;
	}

    /**列表项点击时的事件监听*/
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id){
    	final File mFile = new File(mFilePaths.get(position));
    	//如果该文件是可读的，我们进去查看文件
    	if(mFile.canRead()){
    		if(mFile.isDirectory()){
    			//如果是文件夹，则直接进入该文件夹，查看文件目录
    			initFileListInfo(mFilePaths.get(position));
    		}else{
    			//如果是文件，则用相应的打开方式打开
    			String fileName = mFile.getName();
    	    	String  fileEnds = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();
    			if(fileEnds.equals("txt")){
    				//显示进度条，表示正在读取
					initProgressDialog(ProgressDialog.STYLE_HORIZONTAL);
    				new Thread(new Runnable(){
    					public void run(){
    						//打开文本文件
    						openTxtFile(mFile.getPath());
    					}
    				}).start();
    				new Thread(new Runnable(){
    					public void run(){
    						while(true){
    							if(isTxtDataOk == true){
    								//关闭进度条
    								mProgressDialog.dismiss();
    								executeIntent(txtData.toString(),mFile.getPath());
    								break;
    							}
    							if(isCancleProgressDialog == true){
    								//关闭进度条
    								mProgressDialog.dismiss();
    								break;
    							}
    						}
    					}
    				}).start();
    				//如果是html文件则用自己写的工具打开
    			} else if(fileEnds.equals("html")||fileEnds.equals("mht")||fileEnds.equals("htm")){
    				Intent intent = new Intent(MainActivity.this,WebActivity.class);
    				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				intent.putExtra("filePath", mFile.getPath());
    				startActivity(intent);
    			} else {
    				openFile(mFile);
				}
    		}
    	}else{
    		//如果该文件不可读，我们给出提示不能访问，防止用户操作系统文件造成系统崩溃等
    		Toast.makeText(MainActivity.this, "对不起，您的访问权限不足!", Toast.LENGTH_SHORT).show();
    	}
    }
  //进度条  
    ProgressDialog mProgressDialog;
    boolean isCancleProgressDialog = false;
    /**弹出正在解析文本数据的ProgressDialog*/
    private void initProgressDialog(int style){
    	isCancleProgressDialog = false;
    	mProgressDialog = new ProgressDialog(this);
    	mProgressDialog.setTitle("提示");
    	mProgressDialog.setMessage("正在为你解析文本数据，请稍后...");
    	mProgressDialog.setCancelable(true);
    	mProgressDialog.setButton("取消", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1) {
				isCancleProgressDialog = true;
				mProgressDialog.dismiss();
			}   		
    	});
    	mProgressDialog.show();
    }
    
    private String mCopyFileName;
    private boolean isCopy = false;
	/**长按文件或文件夹时弹出的带ListView效果的功能菜单*/
	private void initItemLongClickListener(final File file){
		OnClickListener listener = new DialogInterface.OnClickListener(){
			//item的值就是从0开始的索引值(从列表的第一项开始)
			public void onClick(DialogInterface dialog, int item) {
				if(file.canRead()){//注意，所有对文件的操作必须是在该文件可读的情况下才可以，否则报错
					if(item == 0){//复制
						if(file.isFile()&&"txt".equals((file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length())).toLowerCase())){
							Toast.makeText(MainActivity.this, "已复制!", Toast.LENGTH_SHORT).show();
							//复制标志位，表明已复制文件
							isCopy = true;
							//取得复制文件的名字
							mCopyFileName = file.getName();
							//记录复制文件的路径
							mOldFilePath = mCurrentFilePath+java.io.File.separator+mCopyFileName;
						}else{
							Toast.makeText(MainActivity.this, "对不起,目前只支持复制文本文件!", Toast.LENGTH_SHORT).show();
						}
					}else if(item == 1){//重命名
						initRenameDialog(file);
					}else if(item == 2){//删除
						initDeleteDialog(file);
					}
				}else{
					Toast.makeText(MainActivity.this, "对不起，您的访问权限不足!", Toast.LENGTH_SHORT).show();
				}
			}	
    	};
    	//列表项名称
    	String[] mMenu = {"复制","重命名","删除"};
    	//显示操作选择对话框
    	new AlertDialog.Builder(MainActivity.this)
    								.setTitle("请选择操作!")
    								.setItems(mMenu, listener)
    								.setPositiveButton("取消",null).show();
	}

    //自定义Adapter内部类
    class FileAdapter extends BaseAdapter{
    	//返回键，各种格式的文件的图标
    	private Bitmap mBackRoot;
    	private Bitmap mBackUp;
    	private Bitmap mImage;
    	private Bitmap mAudio;
    	private Bitmap mRar;
    	private Bitmap mVideo;
    	private Bitmap mFolder;
    	private Bitmap mApk;
    	private Bitmap mOthers;
    	private Bitmap mTxt;
    	private Bitmap mWeb;
    	
    	private Context mContext;
    	//文件名列表
    	private List<String> mFileNameList;
    	//文件对应的路径列表
    	private List<String> mFilePathList;
    	
    	public FileAdapter(Context context,List<String> fileName,List<String> filePath){
    		mContext = context;
    		mFileNameList = fileName;
    		mFilePathList = filePath;
    		//初始化图片资源
    		//返回到根目录
    		mBackRoot = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.back_to_root);
    		//返回到上一级目录
    		mBackUp = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.back_to_up);
    		//图片文件对应的icon
    		mImage = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.image);
    		//音频文件对应的icon
    		mAudio = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.audio);
    		//视频文件对应的icon
    		mVideo = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.video);
    		//可执行文件对应的icon
    		mApk = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.apk);
    		//文本文档对应的icon
    		mTxt = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.txt);
    		//其他类型文件对应的icon
    		mOthers = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.others);
    		//文件夹对应的icon
    		mFolder = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.folder);
    		//zip文件对应的icon
    		mRar = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.zip_icon);
    		//网页文件对应的icon
    		mWeb = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.web_browser);
    	}
    	//获得文件的总数
		public int getCount() {
			return mFilePathList.size();
		}
		//获得当前位置对应的文件名
		public Object getItem(int position) {
			return mFileNameList.get(position);
		}
		//获得当前的位置
		public long getItemId(int position) {
			return position;
		}
		//获得视图
		public View getView(int position, View convertView, ViewGroup viewgroup) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater mLI = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//初始化列表元素界面
				convertView = mLI.inflate(R.layout.list_child, null);
				//获取列表布局界面元素
				viewHolder.mIV = (ImageView)convertView.findViewById(R.id.image_list_childs);
				viewHolder.mTV = (TextView)convertView.findViewById(R.id.text_list_childs);
				//将每一行的元素集合设置成标签
				convertView.setTag(viewHolder);
			} else {
				//获取视图标签
				viewHolder = (ViewHolder) convertView.getTag();
			}
			File mFile = new File(mFilePathList.get(position).toString());
			//如果
			if(mFileNameList.get(position).toString().equals("BacktoRoot")){
				//添加返回根目录的按钮
				viewHolder.mIV.setImageBitmap(mBackRoot);
				viewHolder.mTV.setText("返回根目录");
			}else if(mFileNameList.get(position).toString().equals("BacktoUp")){
				//添加返回上一级菜单的按钮
				viewHolder.mIV.setImageBitmap(mBackUp);
				viewHolder.mTV.setText("返回上一级");
			}else if(mFileNameList.get(position).toString().equals("BacktoSearchBefore")){
				//添加返回搜索之前目录的按钮
				viewHolder.mIV.setImageBitmap(mBackRoot);
				viewHolder.mTV.setText("返回搜索之前目录");
			}else{
				String fileName = mFile.getName();
				viewHolder.mTV.setText(fileName);
				if(mFile.isDirectory()){
					viewHolder.mIV.setImageBitmap(mFolder);
				}else{
			    	String fileEnds = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();//取出文件后缀名并转成小写
			    	if(fileEnds.equals("m4a")||fileEnds.equals("mp3")||fileEnds.equals("mid")||fileEnds.equals("xmf")||fileEnds.equals("ogg")||fileEnds.equals("wav")){
			    		viewHolder.mIV.setImageBitmap(mVideo);
			    	}else if(fileEnds.equals("3gp")||fileEnds.equals("mp4")){
			    		viewHolder.mIV.setImageBitmap(mAudio);
			    	}else if(fileEnds.equals("jpg")||fileEnds.equals("gif")||fileEnds.equals("png")||fileEnds.equals("jpeg")||fileEnds.equals("bmp")){
			    		viewHolder.mIV.setImageBitmap(mImage);
			    	}else if(fileEnds.equals("apk")){
			    		viewHolder.mIV.setImageBitmap(mApk);
			    	}else if(fileEnds.equals("txt")){
			    		viewHolder.mIV.setImageBitmap(mTxt);
			    	}else if(fileEnds.equals("zip")||fileEnds.equals("rar")){
			    		viewHolder.mIV.setImageBitmap(mRar);
			    	}else if(fileEnds.equals("html")||fileEnds.equals("htm")||fileEnds.equals("mht")){
			    		viewHolder.mIV.setImageBitmap(mWeb);
			    	}else {
			    		viewHolder.mIV.setImageBitmap(mOthers);
			    	}
				}				
			}
			return convertView;
		}
    	//用于存储列表每一行元素的图片和文本
		class ViewHolder {
			ImageView mIV;
			TextView mTV;
		}
    }
    
    Intent serviceIntent;
    ServiceConnection mSC;
    RadioGroup mRadioGroup;
    static int mRadioChecked;
    public static final String KEYWORD_BROADCAST = "com.supermario.file.KEYWORD_BROADCAST";
    //显示搜索对话框
    private void searchDilalog(){
    	//用于确定是在当前目录搜索或者是在整个目录搜索的标志
    	mRadioChecked = 1;
    	LayoutInflater mLI = LayoutInflater.from(MainActivity.this);
    	final View mLL = (View)mLI.inflate(R.layout.search_dialog, null);
    	mRadioGroup = (RadioGroup)mLL.findViewById(R.id.radiogroup_search);
    	final RadioButton mCurrentPathButton = (RadioButton)mLL.findViewById(R.id.radio_currentpath);
    	final RadioButton mWholePathButton = (RadioButton)mLL.findViewById(R.id.radio_wholepath);
    	//设置默认选择在当前路径搜索
    	mCurrentPathButton.setChecked(true);
    	mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
    		//当选择改变时触发
			public void onCheckedChanged(RadioGroup radiogroup, int checkId) {
				//当前路径的标志为1
				if(checkId == mCurrentPathButton.getId()){
					mRadioChecked = 1;
					//整个目录的标志为2
				}else if(checkId == mWholePathButton.getId()){
					mRadioChecked = 2;
				}
			}	
    	});
    	Builder mBuilder = new AlertDialog.Builder(MainActivity.this)
    								.setTitle("搜索").setView(mLL)
    								.setPositiveButton("确定", new OnClickListener(){
										public void onClick(DialogInterface arg0, int arg1) {
											keyWords = ((EditText)mLL.findViewById(R.id.edit_search)).getText().toString();
											if(keyWords.length() == 0){
												Toast.makeText(MainActivity.this, "关键字不能为空!", Toast.LENGTH_SHORT).show();
												searchDilalog();
											}else{
												if(menuPosition == 1){
													mPath.setText(mRootPath);
												}else{
													mPath.setText(mSDCard);
												}
												//获取用户输入的关键字并发送广播-开始
												Intent keywordIntent = new Intent();
												keywordIntent.setAction(KEYWORD_BROADCAST);
												//传递搜索的范围区间:1.当前路径下搜索 2.SD卡下搜索
												if(mRadioChecked == 1){
													keywordIntent.putExtra("searchpath", mCurrentFilePath);
												}else{
													keywordIntent.putExtra("searchpath", mSDCard);
												}
												//传递关键字
												keywordIntent.putExtra("keyword", keyWords);
												//到这里为止是携带关键字信息并发送了广播，会在Service服务当中接收该广播并提取关键字进行搜索
												getApplicationContext().sendBroadcast(keywordIntent);
												//获取用户输入的关键字并发送广播-结束
												serviceIntent = new Intent("com.android.service.FILE_SEARCH_START");
												MainActivity.this.startService(serviceIntent);//开启服务，启动搜索
												isComeBackFromNotification = false;
											}
										}
    								})
    								.setNegativeButton("取消", null);
    	mBuilder.create().show();
    }
    
    /**注册广播*/
    private IntentFilter mFilter;
    private FileBroadcast mFileBroadcast;
    private IntentFilter mIntentFilter;
	private SearchBroadCast mServiceBroadCast;
	@Override
    protected void onStart() {
    	super.onStart();
    	mFilter = new IntentFilter();
    	mFilter.addAction(FileService.FILE_SEARCH_COMPLETED);
    	mFilter.addAction(FileService.FILE_NOTIFICATION);
    	mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(KEYWORD_BROADCAST);
    	if(mFileBroadcast == null){
    		mFileBroadcast = new FileBroadcast();
    	}
    	if(mServiceBroadCast == null){
    		mServiceBroadCast = new SearchBroadCast();
    	}
    	this.registerReceiver(mFileBroadcast, mFilter);
    	this.registerReceiver(mServiceBroadCast, mIntentFilter);
    }
    
   
	/**注销广播*/
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("NullPointError", "onDestroy");
		mFileName.clear();
		mFilePaths.clear();
    	this.unregisterReceiver(mFileBroadcast);
    	this.unregisterReceiver(mServiceBroadCast);
	}
    
	private String mAction;
    public static boolean isComeBackFromNotification = false;
    /**内部广播类*/
    class FileBroadcast extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			mAction = intent.getAction();
			// 搜索完毕的广播
			if(FileService.FILE_SEARCH_COMPLETED.equals(mAction)){
				mFileName = intent.getStringArrayListExtra("mFileNameList");
				mFilePaths = intent.getStringArrayListExtra("mFilePathsList");
				Toast.makeText(MainActivity.this, "搜索完毕!", Toast.LENGTH_SHORT).show();
				//这里搜索完毕之后应该弹出一个弹出框提示用户要不要显示数据
				searchCompletedDialog("搜索完毕，是否马上显示结果?");
				getApplicationContext().stopService(serviceIntent);//当搜索完毕的时候停止服务，然后在服务中取消通知
			// 点击通知栏跳转过来的广播
			}else if(FileService.FILE_NOTIFICATION.equals(mAction)){//点击通知回到当前Activity，读取其中信息
				String mNotification = intent.getStringExtra("notification");
				Toast.makeText(MainActivity.this, mNotification, Toast.LENGTH_LONG).show();
				searchCompletedDialog("你确定要取消搜索吗?");
			}
		}
    }
    
    //搜索完毕和点击通知过来时的提示框
    private void searchCompletedDialog(String message){
		Builder searchDialog = new AlertDialog.Builder(MainActivity.this)
		.setTitle("提示")
		.setMessage(message)
		.setPositiveButton("确定", new OnClickListener(){
			public void onClick(DialogInterface dialog,int which) {
				//当弹出框时，需要对这个确定按钮进行一个判断，因为要对不同的情况做不同的处理（2种情况）
				// 1.搜索完毕
				// 2.取消搜索
				if(FileService.FILE_SEARCH_COMPLETED.equals(mAction)){
					if(mFileName.size() == 0){
			    		Toast.makeText(MainActivity.this, "无相关文件/文件夹!", Toast.LENGTH_SHORT).show();
			    		setListAdapter(new FileAdapter(MainActivity.this,mFileName,mFilePaths));//清空列表
			    	}else{ 
			    		//显示文件列表
			    		setListAdapter(new FileAdapter(MainActivity.this,mFileName,mFilePaths));
			    	}
				}else{
					//设置搜索标志为true，
					isComeBackFromNotification = true;
					//关闭服务，取消搜索
					getApplicationContext().stopService(serviceIntent);
				}
			}
		})
		.setNegativeButton("取消", null);
		searchDialog.create();
		searchDialog.show();
    }
    
    String txtData = "";
    boolean isTxtDataOk = false;
    //打开文本文件的方法之读取文件数据
    private void openTxtFile(String file){
    	isTxtDataOk = false;
    	try {
			FileInputStream fis = new FileInputStream(new File(file));
			StringBuilder mSb = new StringBuilder(); 
			int m;
			//读取文本文件内容
			while((m = fis.read()) != -1){
				mSb.append((char)m);
			}
			fis.close();
			//保存读取到的数据
			txtData = mSb.toString();
			//读取完毕
			isTxtDataOk = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //执行Intent跳转的方法
    private void executeIntent(String data,String file){   	
    	Intent intent = new Intent(MainActivity.this,EditTxtActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//传递文件的路径，标题和内容
		intent.putExtra("path", file);
		intent.putExtra("title", new File(file).getName());
		intent.putExtra("data", data.toString());
		//跳转到EditTxtActivity
		startActivity(intent);
    }
    
    private int i;
    FileInputStream fis;
	FileOutputStream fos;
    //复制文件
    private void copyFile(String oldFile,String newFile){
    	try {
			fis =  new FileInputStream(oldFile);
			fos = new FileOutputStream(newFile);
			do{
				//逐个byte读取文件，并写入另一个文件中
				if((i = fis.read()) != -1){
					fos.write(i);
				}
			}while(i != -1);
			//关闭输入文件流
			if(fis != null){
				fis.close();
			}
			//关闭输出文件流
			if(fos != null){
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
package com.guo.gongjiao;

import java.io.File;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private ArrayList<RowModel> directoryEntries = new ArrayList<RowModel>();
	private DataDownloader downloader = null;
	private File currentDirectory;
	private ProgressDialog mDialog;
	

	//因为zip lib不支持中文，因此我们要定义一个hashtable来对中英文进行一个映射
	final  Hashtable<String, String> mFileName = new Hashtable<String, String>();		
	private void initFileNameMap() {
		mFileName.put("shanghai", this.getResources().getString(R.string.shanghai));//上海
		mFileName.put("beijing", this.getResources().getString(R.string.beijing));//北京
		mFileName.put("guangzhou", this.getResources().getString(R.string.guangzhou));//广州
		mFileName.put("shenzhen", this.getResources().getString(R.string.shenzhen));//深圳
		mFileName.put("chengdu", this.getResources().getString(R.string.chengdu));//成都
		mFileName.put("fuzhou", this.getResources().getString(R.string.fuzhou));//福州
		mFileName.put("hefei", this.getResources().getString(R.string.hefei));//合肥
		mFileName.put("wuhan", this.getResources().getString(R.string.wuhan));//武汉
		mFileName.put("zhixiashi", this.getResources().getString(R.string.zhixiashi));//直辖市
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		initFileNameMap();
		unzip();
	}

	protected void OnPause() {
		super.onPause();
		if (downloader != null) {
			synchronized (downloader) {
				downloader.setStatusField(null);
			}
		}
	}

	protected void OnResume() {
		super.onResume();
		if (downloader != null) {
			synchronized (downloader) {
				downloader.setStatusField(mDialog);
			}
		}
	}
	//按键时响应
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	boolean result = true;
    	//判断如果按下的是back键
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		//如果当前目录是mybus则退出程序
    		if(currentDirectory.getName().equals("mybus")) {
    			finish();
    		} else {
    			//否则显示上级目录的内容
    			browseTo(currentDirectory.getParentFile(),0);
    		}
    	} 
        return result;
    }
	

	/**
	 * move the file in asset to directory /sdcard/mybus
	 */
	private void unzip() {
		Log.v(Globals.TAG, "start unzip file");
		class CallBack implements Runnable {
			public MainActivity mParent;

			public void run() {
				if (mParent.downloader == null)
					mParent.downloader = new DataDownloader(mParent, mDialog);
			}
		}
		CallBack cb = new CallBack();
		cb.mParent = this;
		mDialog = CreateDialog();
		mDialog.show();
		this.runOnUiThread(cb);	
	}
	
	protected ProgressDialog CreateDialog() {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog
				.setMessage(this.getResources().getString(
						R.string.pregress_diag));
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}
	//显示文件列表
	private void browseTo(final File aDirectory, final long id) {
		//如果传入的参数是个目录则显示目录下的所有内容
		if (aDirectory.isDirectory()) {
			this.currentDirectory = aDirectory;
			fill(aDirectory.listFiles());
		} else {
			//如果传入的是文件则为文件添加按钮
			DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
			   @Override
				public void onClick(DialogInterface arg0, int arg1) {
					try {
						try {//如果用户选择确定按钮，则进入查询界面
							Intent in = new Intent(MainActivity.this,
									Traffic.class);
							//将数据文件的路径作为参数传递过去
							in.putExtra(Globals.FILENAME, aDirectory.getPath());
							//将数据文件对应的中文城市名传递过去
							in.putExtra(Globals.Title, directoryEntries.get((int)id).mChineseName);
							//打开查询界面的Activity
							MainActivity.this.startActivity(in);
						} catch (Exception e) {
							Context context = getApplicationContext();
							//如果出错，则以Toast方式提示用户
							CharSequence text = MainActivity.this
									.getResources().getString(
											R.string.diag_err);
							int duration = Toast.LENGTH_SHORT;

							Toast toast = Toast.makeText(context, text,
									duration);
							toast.show();
						}
						;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			//添加取消按钮的功能
			DialogInterface.OnClickListener cancelButtonListener = new DialogInterface.OnClickListener() {
				 @Override
				 //如果选择取消，则将dialog隐藏，不作其他操作
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
			//创建一个AlertDialog，当用户点击城市的时候会弹出提示，供用户进一步选择
			AlertDialog ad = new AlertDialog.Builder(this).setMessage(
					R.string.diag_msg).setPositiveButton(android.R.string.ok,
					okButtonListener).setNegativeButton(
					android.R.string.cancel, cancelButtonListener).create();
			ad.show();
		}
	}

	private void fill(File[] files) {
		//清除数组中的所有元素
		this.directoryEntries.clear();	
		int type = 0;
		for (File file : files) {
			//不是数据文件也不是目录的跳过
			if (!file.getName().endsWith(".txt") && !file.isDirectory())
				continue;
			final String name;
			//如果是文件则将文件名去掉扩展名之后保存在数组中
			if (!file.isDirectory()) {
				name = file.getName().substring(0,
						file.getName().lastIndexOf('.'));
				type = 0;
			} else {
				//如果是目录直接保存目录名
				type = 1;
				name = file.getName();
			}
			this.directoryEntries.add(new RowModel(type, name));
		}
		//新建一个中文字符比较器
		Comparator<RowModel> cmp = new ChinsesCharComp();
		//对数组文件进行排序
		Collections.sort(directoryEntries, cmp);
		//新建一个IconAdapter，包含文件类表
		IconAdapter directoryList = new IconAdapter(directoryEntries);
		//设置ListAdapter
		this.setListAdapter(directoryList);
	}

	//为选项绑定监听器
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		File clickedFile = null;
		//如果是一个目录，就进一步显示目录的内容
		if(this.directoryEntries.get(position).mRowtype == 1) {
			
			Log.v(Globals.TAG, "is a directory");
			//通过directoryEntries数组的内容获取文件路径信息
			clickedFile = new File(this.currentDirectory.getAbsolutePath()
					+ File.separator + this.directoryEntries.get(position).mLabel);
			this.browseTo(clickedFile, id);
			return;
		} 
		//如果是个文件，要先补全文件完整路径，在打开相应数据文件
		clickedFile = new File(this.currentDirectory.getAbsolutePath()
				+ File.separator + this.directoryEntries.get(position).mLabel + ".txt");
		//再次确认文件是完整的
		try {
			if (clickedFile != null && clickedFile.isFile())
				this.browseTo(clickedFile, id);
				
		} catch (Exception e) {
			//don't throw
		}
		
	}
	//构建一个ListView的适配器
	class IconAdapter extends ArrayAdapter<RowModel> {
		//适配器初始化函数，设定数据来源和界面文件
		IconAdapter(List<RowModel> _items) {
		    super(MainActivity.this, R.layout.row, _items);  
	    }
		//对将数据添加到每一行，并设置图标
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			//设置文本
			TextView tv= (TextView)row.findViewById(R.id.label);
			tv.setText(directoryEntries.get(position).mChineseName);
			ImageView iv = (ImageView)row.findViewById(R.id.icon);
			//设置ICON
			iv.setImageResource(R.drawable.icon);
			return row;
		}
		
	}	
	class ChinsesCharComp implements Comparator<RowModel> {
		//构建一个比较方法，传入需要比较的2个值
		public int compare(RowModel o1, RowModel o2) {
			String c1 = (String) o1.mChineseName;
			String c2 = (String) o2.mChineseName;
			//初始化一个中文字符集合
			Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
			//根据首个汉子的字典顺序排序
			if (myCollator.compare(c1, c2) < 0)
				return -1;
			else if (myCollator.compare(c1, c2) > 0)
				return 1;
			else
				return 0;
		}
	}

	public void getFileList() {
		mDialog.dismiss();//消除进程对话框
		browseTo(new File(Globals.DataDir),0);//显示文件列表
	}
	
	class RowModel {
		int mRowtype; //0:file 1:directory
		String mLabel;//英文名称
		String mChineseName;//中文名称
		
		RowModel(int type, String label) {
			mRowtype = type;
			mChineseName = mLabel = label;
			String temp = mFileName.get(label);
			//若有中文名称则将中文名称存储到mChineseName
			if (temp != null) {
				mChineseName = temp;
			}
		}
		//返回中文名称
		public String toString() {
			return mChineseName;
		}	
	}



}

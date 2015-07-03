package com.guo.memorandum;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guo.memorandum.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SetAlarm extends Activity {
	//显示音乐文件的列表
	private ListView listV;
	//列表适配器
	private SimpleAdapter sa;
	//音乐文件搜索路径
	private static final String MUSIC_PATH = new String("/sdcard/");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicmain);
		listV = (ListView) findViewById(R.id.list);
		//显示音乐文件
		musicList();
		//设置按键监听器
		listV.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, String> map = (Map<String, String>)arg0.getItemAtPosition(arg2);
				//取得音乐文件名称
				final String name = map.get("musicName");
				//创建对话框
				AlertDialog.Builder adb = new Builder(SetAlarm.this);
				adb.setTitle("提示消息");
				adb.setMessage("确定要将 "+name+" 设置为默认闹铃声吗？");
				adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Uri uri = Uri.parse(MUSIC_PATH + name);
						//设置闹铃的路径
						ActivityManager.setUri(uri);
						Toast.makeText(SetAlarm.this, "设置成功",Toast.LENGTH_SHORT).show();
						//关闭当前页面
						finish();
					}
				});
				adb.setNegativeButton("取消",null);
				//显示对话框
				adb.show();
			}
		});
	}
	//显示音乐文件列表
	public void musicList() {
		//取得需要遍历的文件目录
		File home = new File(MUSIC_PATH);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//遍历文件目录
		if (home.listFiles(new MusicFilter()).length > 0) {
			for (File file : home.listFiles(new MusicFilter())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("musicName", file.getName());
				list.add(map);
			}
			sa = new SimpleAdapter(SetAlarm.this, list, R.layout.musicitems,
					new String[] { "musicName" }, new int[] { R.id.musicName });
			listV.setAdapter(sa);
		}
	}
}
//过滤所有不是以.mp3结尾的文件
class MusicFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".mp3"));
	}
}
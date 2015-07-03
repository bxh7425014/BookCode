package com.guo.memorandum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guo.memorandum.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	//用于显示备忘录文件
	private ListView lv;
	//数据库帮助类
	private SqliteDBConnect sd;
	//每页显示的数目
	private static int page_size = 8;
	//初始化页数
	private static int page_no = 1, page_count = 0, count = 0;
	//添加、首页、末页按钮
	private Button btnAdd, btnFirst, btnEnd;
	//图像按钮：前一页、后一页
	private ImageButton btnNext, btnPre;
	//适配器
	private SimpleAdapter sa;
	//进度条
	private ProgressBar m_ProgressBar;
	private ActivityManager am;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置显示进度条
		setProgressBarVisibility(true);
		setContentView(R.layout.home);
		//实例化ActivityManager
		am = ActivityManager.getInstance();
		am.addActivity(this);
		//初始化按钮
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnFirst = (Button) findViewById(R.id.btnFirst);
		btnPre = (ImageButton) findViewById(R.id.btnPre);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnEnd = (Button) findViewById(R.id.btnEnd);
		//初始化进度条
		m_ProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		lv = (ListView) findViewById(R.id.listview);
		//初始化数据库
		sd = new SqliteDBConnect(MainActivity.this);
		//获取数据库数据并分页显示
		fenye();
		//设置ListView按键监听器
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) arg0
						.getItemAtPosition(arg2);
				Intent intent = new Intent();
				//传递备忘录的noteId
				intent.putExtra("noteId", map.get("noteId").toString());
				intent.setClass(MainActivity.this, Lookover.class);
				//查看备忘录
				startActivity(intent);
			}
		});
		//设置ListView长按监听器
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> map = (Map<String, Object>) arg0
						.getItemAtPosition(arg2);
				AlertDialog.Builder adb = new Builder(MainActivity.this);
				adb.setTitle(map.get("noteName").toString());
				//设置弹出选项
				adb.setItems(new String[] { "删除", "修改"},
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								//删除
								case 0:
									SQLiteDatabase sdb = sd
											.getReadableDatabase();
									sdb.delete("note", "noteId=?",
											new String[] { map.get("noteId")
													.toString() });
									Toast.makeText(MainActivity.this, "删除成功",
											Toast.LENGTH_SHORT).show();
									sdb.close();
									//刷新页面
									fenye();
									break;
								//修改
								case 1:
									Intent intent = new Intent();
									intent.putExtra("noteId", map.get("noteId")
											.toString());
									intent.setClass(MainActivity.this, AddActivity.class);
									//进入编辑页面
									startActivity(intent);
									break;
								}
							}
						});
				//显示对话框
				adb.show();
				return true;
			}
		});
		//设置添加按钮监听器
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//显示进度条
				m_ProgressBar.setVisibility(View.VISIBLE);
				m_ProgressBar.setProgress(0);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AddActivity.class);
				//进入添加页面
				startActivity(intent);
			}
		});
		//进入首页
		btnFirst.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果是首页，提示用户当前已经是首页了
				if (page_no == 1) {
					Toast.makeText(MainActivity.this, "已经是首页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					//如果不是首页则将当前页码置为1
					page_no = 1;
				}
				//刷新页面
				fenye();
			}
		});
		//下一页按键监听器
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果当前是最后一页，则提示用户已经到最后一页了
				if (page_no == page_count) {
					Toast.makeText(MainActivity.this, "已经是末页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					//否则，当前的页码加1
					page_no += 1;
				}
				//刷新页面
				fenye();
			}
		});
		//上一页按键监听器
		btnPre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果当前是第一页，则提示用户当前已经是首页了
				if (page_no == 1) {
					Toast.makeText(MainActivity.this, "已经是首页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					//否则，当前页码减1
					page_no -= 1;
				}
				//刷新页面
				fenye();
			}
		});
		//设置末页按键监听器
		btnEnd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//如果当前是最后一页，提示用户当前已经是末页了
				if (page_no == page_count) {
					Toast.makeText(MainActivity.this, "已经是末页了", Toast.LENGTH_SHORT)
							.show();
				} else {
					//否则将当前页置为末页
					page_no = page_count;
				}
				//刷新页面
				fenye();
			}
		});
	}
	//获取数据库数据并分页显示
	public void fenye() {
		SQLiteDatabase sdb = sd.getReadableDatabase();
		count = 0;
		// 从数据库中查询数据，按升序排列
		Cursor c1 = sdb.query("note", new String[] { "noteId", "noteName",
				"noteTime" }, null, null, null, null, "noteId asc");
		while (c1.moveToNext()) {
			int noteid = c1.getInt(c1.getColumnIndex("noteId"));
			//保存数据的总数
			if (noteid > count)
				count = noteid;
		}
		c1.close();
		//取得总页数
		page_count = count % page_size == 0 ? count / page_size : count
				/ page_size + 1;
		//到达首页
		if (page_no < 1)
			page_no = 1;
		//到达末页
		if (page_no > page_count)
			page_no = page_count;
		//查询指定页的数据
		Cursor c=sdb.rawQuery("select noteId,noteName,noteTime from note limit ?,?", new String[] {
						(page_no - 1) * page_size + "", page_size + "" });
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//遍历循环，取得所有数据，并存储到list中
		while (c.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			//取得备忘录的名字
			String strName = c.getString(c.getColumnIndex("noteName"));
			//如果字数超过12个则去掉后面的字符用...代替
			if (strName.length() > 20) {
				map.put("noteName", strName.substring(0, 20) + "...");
			} else {
				map.put("noteName", strName);
			}
			//取得时间和id信息，存储到map中
			map.put("noteTime", c.getString(c.getColumnIndex("noteTime")));
			map.put("noteId", c.getInt(c.getColumnIndex("noteId")));
			//将map添加到list中
			list.add(map);
		}
		c.close();
		sdb.close();
		if (count > 0) {
			//新建适配器
			sa = new SimpleAdapter(MainActivity.this, list, R.layout.items,
					new String[] { "noteName", "noteTime" }, new int[] {
							R.id.noteName, R.id.noteTime });
			//设置适配器
			lv.setAdapter(sa);
		}

	}
	//菜单按钮
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加菜单项
		menu.add(0, 1, 1, "设置铃声");
		menu.add(0, 2, 2, "退出");
		return super.onCreateOptionsMenu(menu);
	}
	//为菜单按钮绑定按键监听器
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		//设置铃声
		case 1:
			Intent intent=new Intent();
			intent.setClass(MainActivity.this,SetAlarm.class);
			//跳转到设置铃声的界面
			startActivity(intent);
			break;
		//退出
		case 2:
			AlertDialog.Builder adb2 = new Builder(MainActivity.this);
			adb2.setTitle("消息");
			adb2.setMessage("真的要退出吗?");
			adb2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//关闭所有的Activity
					am.exitAllProgress();
				}
			});
			adb2.setNegativeButton("取消", null);
			//显示对话框，询问用户是否确定要退出
			adb2.show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	//当用户按键时触发
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//如果用户按下了back键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder adb = new Builder(MainActivity.this);
			adb.setTitle("消息");
			adb.setMessage("真的要退出？");
			adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					am.exitAllProgress();
				}
			});
			adb.setNegativeButton("取消", null);
			//显示对话框询问用户是否确定要退出
			adb.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}

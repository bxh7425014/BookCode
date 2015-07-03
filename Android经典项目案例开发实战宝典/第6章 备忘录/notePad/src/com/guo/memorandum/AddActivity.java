package com.guo.memorandum;

import java.util.Calendar;

import com.guo.memorandum.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddActivity extends Activity {
	//标题、内容和时间
	private EditText etName,etMain, etTime;
	//保存按钮、取消按钮
	private Button btnCommit,btnCancel;
	//数据库操作类
	private SQLiteDatabase sdb;
	private ActivityManager am;
	//年月日时分秒，用于保存日历详细信息
	private int year, month, day, hours, minute, second;
	private Calendar c;
	private PendingIntent pi;
	private AlarmManager alm;
	//编辑模式标志
	private boolean EDIT=false;
	private String noteId;
	//初始化函数
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		//将当前Activity添加到Activity列表中
		am = ActivityManager.getInstance();
		am.addActivity(this);
		//初始化各个元素
		etName = (EditText) findViewById(R.id.noteName);
		etMain = (EditText) findViewById(R.id.noteMain);
		btnCommit = (Button) findViewById(R.id.btnCommit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		etTime = (EditText) findViewById(R.id.noteTime);
		Intent intent=getIntent();
		noteId = intent.getStringExtra("noteId");
		//如果noteId值不为空，则进入编辑模式
		if(noteId != null)
			EDIT=true;
		else
			EDIT=false;
		//数据库连接类
		SqliteDBConnect sd = new SqliteDBConnect(AddActivity.this);
		//获得数据库操作类
		sdb = sd.getReadableDatabase();
		if(EDIT)
		{
			//通过noteId取得对应的信息
			Cursor c = sdb.query("note", new String[] { "noteId", "noteName",
					"noteContent", "noteTime" }, "noteId=?",
					new String[] { noteId }, null, null, null);
			//将获得的信息写入对应的EditText
			while (c.moveToNext()) {
				etName.setText(c.getString(c.getColumnIndex("noteName")));
				etMain.setText(c.getString(c.getColumnIndex("noteContent")));
				etTime.setText(c.getString(c.getColumnIndex("noteTime")));
			}
			c.close();
		}else{
			//设置默认闹钟为当前时间
			etTime.setText(am.returnTime());
		}

		//设置文本颜色为红色
		etTime.setTextColor(Color.RED);
		//为闹钟设置长按监听器，弹出日期选择界面
		etTime.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				//实例化日历
				c = Calendar.getInstance();
				//取得日历信息中的年月日时分秒
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				hours = c.get(Calendar.HOUR);
				minute = c.get(Calendar.MINUTE);
				second = c.get(Calendar.SECOND);
				//新建一个日期选择控件
				DatePickerDialog dpd = new DatePickerDialog(AddActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							//设置日期的时候触发
							@Override
							public void onDateSet(DatePicker view, int y,
									int monthOfYear, int dayOfMonth) {
								String[] time = { "",
										hours + ":" + minute + ":" + second };
								try {
									//将日期和时间分割
									String[] time2 = etTime.getText()
											.toString().trim().split(" ");
									//取得时间的信息保存到time[1]中
									if (time2.length == 2) {
										time[1] = time2[1];
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String mo = "", da = "";
								//将月份转换成两位数
								if (monthOfYear < 9) {
									mo = "0" + (monthOfYear + 1);
								} else {
									mo = monthOfYear+1 + "";
								}
								//将天数转换成两位数
								if (dayOfMonth < 10) {
									da = "0" + dayOfMonth;
								} else {
									da = dayOfMonth + "";
								}
								//将设置的结果保存到etTime中
								etTime.setText(y + "-" + mo + "-" + da + " "
										+ time[1]);
							}
						}, year, month, day);
				dpd.setTitle("设置日期");
				//显示日期控件
				dpd.show();
				return true;
			}
		});
		//设置单击监听器，弹出时间选择界面
		etTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//实例化日历
				c = Calendar.getInstance();
				//取得当前的年月日信息
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				//注意这里不是HOUR,HOUR返回的是12制的时间格式
				hours = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
				second = c.get(Calendar.SECOND);
				//新建时间选择器
				TimePickerDialog tpd = new TimePickerDialog(AddActivity.this,
						new OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								String[] time = {
										year + "-" + month + "-" + day, "" };
								try {
									//分割时间和日期
									time = etTime.getText().toString().trim()
											.split(" ");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String ho = "", mi = "";
								//设置小时
								if (hourOfDay < 10) {
									ho = "0" + hourOfDay;
								} else {
									ho = hourOfDay + "";
								}
								//设置分钟
								if (minute < 10) {
									mi = "0" + minute;
								} else {
									mi = minute + "";
								}
								//将设置的结果保存到etTime中
								etTime.setText(time[0] + " " + ho + ":" + mi);
							}
						}, hours, minute, true);
				tpd.setTitle("设置时间");
				//显示时间控件
				tpd.show();
			}
		});
		//保存按钮监听器
		btnCommit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new Builder(AddActivity.this);
				//设置标题和信息
				adb.setTitle("保存");
				adb.setMessage("确定要保存吗？");
				//设置按钮功能
				adb.setPositiveButton("保存",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//保存备忘录信息
								saveNote();
							}
						});
				adb.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(AddActivity.this, "不保存",
										Toast.LENGTH_SHORT).show();
							}
						});
				//显示对话框
				adb.show();
			}
		});
		//设置取消按钮监听器
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new Builder(AddActivity.this);
				//设置标题和消息
				adb.setTitle("提示");
				adb.setMessage("确定不保存吗？");
				//设置按键监听器
				adb.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//进入主界面
								Intent intent = new Intent();
								intent.setClass(AddActivity.this,
										MainActivity.class);
								startActivity(intent);
							}
						});
				adb.setNegativeButton("取消", null);
				//显示对话框
				adb.show();
			}
		});
	}
	//新建菜单选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "关于");
		menu.add(0, 2, 2, "设置闹铃");
		menu.add(0, 3, 3, "退出");
		return super.onCreateOptionsMenu(menu);
	}
	//为菜单选项绑定监听器
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		//关于
		case 1:
			AlertDialog.Builder adb = new Builder(AddActivity.this);
			adb.setTitle("关于");
			adb.setMessage("备忘录V1.0");
			adb.setPositiveButton("确定", null);
			adb.show();
			break;
		//设置闹铃
		case 2:
			Intent intent = new Intent();
			intent.setClass(AddActivity.this, SetAlarm.class);
			startActivity(intent);
			break;
		//退出
		case 3:
			AlertDialog.Builder adb2 = new Builder(AddActivity.this);
			adb2.setTitle("消息");
			adb2.setMessage("真的要退出吗？");
			adb2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//关闭列表中的所有Activity
					am.exitAllProgress();
				}
			});
			adb2.setNegativeButton("取消", null);
			//显示对话框
			adb2.show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	//按键判断
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//当按键是返回键时
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder adb = new Builder(AddActivity.this);
			adb.setTitle("消息");
			adb.setMessage("是否要保存？");
			adb.setPositiveButton("保存", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//保存备忘录
					saveNote();
				}
			});
			adb.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent2 = new Intent();
					intent2.setClass(AddActivity.this, MainActivity.class);
					//回到主页面
					startActivity(intent2);
				}
			});
			//显示对话框
			adb.show();
		}
		return super.onKeyDown(keyCode, event);
	}
	//保存备忘录
	public void saveNote() {
		//取得输入的内容
		String name = etName.getText().toString().trim();
		String content = etMain.getText().toString().trim();
		String time = etTime.getText().toString().trim();
		//内容和标题都不能为空
		if ("".equals(name) || "".equals(content)) {
			Toast.makeText(this, "名称和内容都不能为空", Toast.LENGTH_SHORT)
					.show();
		} else {
			if(EDIT)
			{
				am.saveNote(sdb, name, content, noteId, time);
				Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT)
				.show();
			}
			else
			{
				am.addNote(sdb, name, content, time);
				Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT)
					.show();
			}
			//分割日期和时间
			String[] t = etTime.getText().toString().trim().split(" ");
			//分割日期
			String[] t1 = t[0].split("-");
			//分割时间
			String[] t2 = t[1].split(":");
			//实例化日历
			Calendar c2 = Calendar.getInstance();
			//设置日历为闹钟的时间
			c2.set(Integer.parseInt(t1[0]), Integer.parseInt(t1[1])-1,
					Integer.parseInt(t1[2]), Integer.parseInt(t2[0]),
					Integer.parseInt(t2[1]));			
			c=Calendar.getInstance();
			//闹钟的时间应至少比现在多10s
			if (c.getTimeInMillis() + 1000 * 10 <= c2.getTimeInMillis()) {
				String messageContent;
				//当内容字数大于20个字时，切掉一部分以‘...’代替，并存储在messageContent中
				if (content.length() > 20) {
					messageContent = content.substring(0, 18) + "...";
				} else {
					messageContent = content;
				}
				Intent intent = new Intent();
				intent.setClass(this, AlarmNote.class);
				//传递标题和内容信息
				intent.putExtra("messageTitle", name);
				intent.putExtra("messageContent", messageContent);
				pi = PendingIntent.getBroadcast(this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				//获得闹钟服务
				alm = (AlarmManager) getSystemService(ALARM_SERVICE);
				//设置闹钟
				alm.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), pi);
			}
			Intent intent2 = new Intent();
			intent2.setClass(this, MainActivity.class);
			//回到主目录
			startActivity(intent2);
			AddActivity.this.finish();
		}
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//关闭数据库连接
		sdb.close();		
	}
}
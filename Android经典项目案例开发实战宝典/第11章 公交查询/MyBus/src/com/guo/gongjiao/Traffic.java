package com.guo.gongjiao;

import java.util.Enumeration;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;



public class Traffic extends TabActivity {
	/** Called when the activity is first created. */
	TabHost.TabSpec tab4;
	String mFile;
	ProgressDialog mDialog;
/*	static {
		AdManager.init("4ef94353630d245b", "1fc8b2d8877c7eee", 30, false, "1.0");
	}*/

	private static final int DIALOG_YES_NO_MESSAGE = 1;//显示错误提示
	private static final int DIALOG_LIST = 2;//显示相似路线类表
	private static final int DIALOG_LIST1 = 3;//显示转乘方案列表
	private static final int DIALOG_LIST_FOR_ROAD = 4;//显示经过站点的路线列表
	private static final int DIALOG_LIST_BUS = 5;//显示直达方案列表

	private TabHost tabHost;
	private TableLayout table;
	private ImageButton interSearchButton;
	private ImageButton lineSearchButton;
	private ImageButton stationSearchButton;
	private EditText start, end;
	private EditText line, station;
	Model m;
	String[] road_list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取得从上一级传递过来的参数
		mFile = this.getIntent().getStringExtra(Globals.FILENAME);//获得数据文件名
		setTitle(getIntent().getStringExtra(Globals.Title));//设置当前的标题为城市名称
		
		tabHost = getTabHost();//取得当前的tabHost，用于管理标签

		LayoutInflater.from(this).inflate(R.layout.tab,
				tabHost.getTabContentView(), true);//将布局文件tab的内容扩展到当前界面中
		//添加“换乘”标签
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(
				this.getString(R.string.interchage)).setContent(R.id.tab1));
		//添加“线路”标签
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(
				this.getString(R.string.line)).setContent(R.id.tab2));
		//添加“站点”标签
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(
				this.getString(R.string.station)).setContent(R.id.tab3));
		tab4 = tabHost.newTabSpec("tab4").setIndicator(
				this.getString(R.string.result)).setContent(R.id.tab4);
		//添加结果标签
		tabHost.addTab(tab4);
		//取得“换乘”标签的按钮，并绑定按键监听器
		interSearchButton = (ImageButton) findViewById(R.id.tab1_b1);
		interSearchButton.setOnClickListener(mGoListener);
		//取得“线路”标签的按钮，并绑定按键监听器
		lineSearchButton = (ImageButton) findViewById(R.id.tab2_b1);
		lineSearchButton.setOnClickListener(mGoListener);
		//取得“站点”标签的按钮，并绑定按键监听器
		stationSearchButton = (ImageButton) findViewById(R.id.tab3_b1);
		stationSearchButton.setOnClickListener(mGoListener);
		//初始化各个标签的界面元素
		start = (EditText) findViewById(R.id.tab1_et1);
		end = (EditText) findViewById(R.id.tab1_et2);
		line = (EditText) findViewById(R.id.tab2_et1);
		station = (EditText) findViewById(R.id.tab3_et1);
		//显示对话框，提示正在载入数据
		mDialog = CreateDialog();
		mDialog.show();
		//对数据文件进行处理
		m = new Model(this, mFile);
		Thread t = new Thread(m);
		t.start();
	}

	protected ProgressDialog CreateDialog() {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage(this.getResources().getString(
						R.string.pregress_diag));
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}
    

	//当用户点击按键的时候执行相应功能
	private OnClickListener mGoListener = new OnClickListener() {
		public void onClick(View v) {
			String s1, s2;
			if (v == (View) interSearchButton) {

				s1 = start.getText().toString();//用于存储用户输入起始站
				s2 = end.getText().toString();//用于存储用户输入终点站
				if(s1.trim().length() == 0 ||
						s2.trim().length() == 0)
					return;
				//验证输入信息的有效性
				if (!checkTextValid(s1) || !checkTextValid(s2)
						|| (s1.indexOf(s2) != -1) || (s2.indexOf(s1) != -1)) {
					//若无效则显示提示框
					showDialog1(DIALOG_YES_NO_MESSAGE);
					return;
				}
				//若输入合法则进行查询业务
				processinterSearch(s1, s2);

			} else if (v == (View) lineSearchButton) {

				s1 = line.getText().toString();//用于存储用于输入线路
				if(s1.trim().length() == 0)
					return;
				//验证输入数据的有效性
				if (!checkTextValid(s1)) {
					//如果输入无效则显示提示框
					showDialog1(DIALOG_YES_NO_MESSAGE);
					return;
				}
				//若输入合法进行查询业务
				processRoadSearch(s1);
			} else if (v == (View) stationSearchButton) {
				//存储用户输入站点信息
				s1 = station.getText().toString();
				if(s1.trim().length() == 0)
					return;
				//验证用户输入信息的有效性
				if (!checkTextValid(s1)) {
					//如果无效则显示提示框
					showDialog1(DIALOG_YES_NO_MESSAGE);
					return;
				}
				//若输入合法则进行查询业务
				processStationSearch(s1);
			} else {
				Log.e(Globals.TAG, "error");
			}
		}
	};

	void showDialog1(int id) {
		CreateDialog(id).show();
	}
	//对话框
	protected Dialog CreateDialog(int id) {
		switch (id) {
		case DIALOG_YES_NO_MESSAGE:
			return new AlertDialog.Builder(this).setIcon(
					//设置标题:对不起；内容：没有找到相关信息
					R.drawable.alert_dialog_icon).setTitle(R.string.sorry)  
					.setMessage(R.string.find_nothing).setPositiveButton(
							R.string.conform,
							new DialogInterface.OnClickListener() {
								//设置按钮不作任何操作，直接关掉对话框
								public void onClick(DialogInterface dialog,  
										int whichButton) {
								}
							}).create();
		case DIALOG_LIST:
			return new AlertDialog.Builder(Traffic.this).setTitle(//设置标题
					Html.fromHtml(list_title)).setItems(road_list,//显以列表形式显示路线名称
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//显示用户选定的路线的详细信息
							draw_road_table(road_list[which], false);
							//切换当前西那是的标签为结果标签
							tabHost.setCurrentTabByTag("tab4");
						}
					}).create();
		case DIALOG_LIST1:
			return new AlertDialog.Builder(Traffic.this).setTitle(
					R.string.suggst_road).setItems(road_list, 	//设置标题：建议线路
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//将信息显示到结果标签
							draw_road_table1(which);
							//切换当前显示的标签为结果标签
							tabHost.setCurrentTabByTag("tab4");
							dialog.cancel();
						}
					}).create();
		case DIALOG_LIST_FOR_ROAD:
			return new AlertDialog.Builder(Traffic.this).setTitle(
					Html.fromHtml(list_title)).setItems(station_list,//设置标题
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//显示查询结果到“结果”标签
							draw_road_table(station_list[which], false);
							//切换到“结果”标签
							tabHost.setCurrentTabByTag("tab4");
						}
					}).create();
		case DIALOG_LIST_BUS:
			return new AlertDialog.Builder(Traffic.this).setTitle(
					R.string.suggst_road).setItems(road_list,//设置标题：建议线路
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//将信息显示到结果标签
							draw_road_table(road_list[which], false);
							//切换当前显示的标签为结果标签
							tabHost.setCurrentTabByTag("tab4");
						}
					}).create();
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// call the parent to attach any system level menus
		super.onCreateOptionsMenu(menu);
		int base = Menu.FIRST; // value is 1
		// menu.add(base, base, base, this.getString(R.string.exit));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == Menu.FIRST) {
			this.finish();
		}
		// should return true if the menu item
		// is handled
		return true;
	}

	String list_title;
	//显示路线详细信息
	private void draw_road_table(String road, boolean append) {
		TextView line, upload, download;

		table = (TableLayout) findViewById(R.id.menu);
		if (table == null) {
			Log.e(Globals.TAG, "tab is null");
		}
		//设置第一类为可收缩
		table.setColumnShrinkable(1, true);

		((TextView) findViewById(R.id.tab4_h2))
				.setText(getString(R.string.line));//线路
		((TextView) findViewById(R.id.tab4_h3))
				.setText(getString(R.string.upload));//去程
		((TextView) findViewById(R.id.tab4_h4))
				.setText(getString(R.string.download));//回程

		line = (TextView) findViewById(R.id.tab4_et2);
		upload = (TextView) findViewById(R.id.tab4_et3);
		download = (TextView) findViewById(R.id.tab4_et4);

		line.setText(road);//显示线路名称
		final String[] ss = m.get_stations_for_one_road(road);//取得线路的完整路线
		if (ss != null) {
			upload.setText(ss[0]);//显示去程
			download.setText(ss[1]);//显示回程
		}
		//取得线路时间信息
		final String time = m.getTimeforOneRoad(road);
		if (time != null) {
			((TextView) findViewById(R.id.tab4_h5))
					.setText(getString(R.string.time));//时间
			((TextView) findViewById(R.id.tab4_et5)).setText(time);//显示时间信息
		}
	}

	private boolean checkTextValid(String s) {
		if (s.trim().length() == 0)
			return false;
		if (s.trim().equals("") || !validateString(s)) {
			return false;
		}
		return true;
	}

	private void draw_station_road_table(String road) {
		String st = "";
		Enumeration seq;

		Vector vv = m.get_stations_for_one_road_not_care_direction(road);
		if (vv == null)
			return;
		seq = vv.elements();
		// get station for forward
		while (seq.hasMoreElements()) {
			if (st.trim().equals("")) {
				st = (String) seq.nextElement();
			} else {
				st = st + "-" + (String) seq.nextElement();
			}
		}
		// #style roaditem
		/*
		 * StringItem rr = new StringItem(road, st); this.tabbedForm.append(3,
		 * rr);
		 */
	}
	//将线路以列表形式显示
	private void draw_filter_list(Vector v) {
		Enumeration seq = v.elements();
		int j = 0;
		road_list = new String[v.size()];
		//遍历查询集合的元素
		while (seq.hasMoreElements()) {
			road_list[j++] = (String) seq.nextElement();
		}
		//显示对话框
		showDialog1(DIALOG_LIST);
	}

	/*
	 * show a roads dialog which pass the station
	 */
	//用户存放站点列表
	String[] station_list;
	//将站点列表显示到对话框中
	private void draw_filter_list3(Vector v) {
		//新建一个集合用于存放站点
		Enumeration seq = v.elements();
		int j = 0;
		// ChoiceItem ci;
		station_list = new String[v.size()];
		//遍历集合，将所有站点存放到station_list中
		while (seq.hasMoreElements()) {
			station_list[j++] = (String) seq.nextElement();
		}
		//显示结果选择对话框
		showDialog1(DIALOG_LIST_FOR_ROAD);
	}
	//查询线路
	private void processRoadSearch(String s) {
		//用于存放建议路线
		Vector v;
		//取得建议路线
		v = m.getSuggestRoads(s);
		//如果查询结果大于一个，则显示一个列表供用户选择
		if (v.size() > 1) {
			this.list_title = this.getString(R.string.select_one_road);
			draw_filter_list(v);
		} else if (v.size() == 1) {//如果刚好是一个，则直接将查询就够显示到结果标签中
			draw_road_table((String) v.elementAt(0), false);
			this.tabHost.setCurrentTabByTag("tab4");
		} else {
			// 如果没有结果则提示用户没找到相关信息
			showDialog1(DIALOG_YES_NO_MESSAGE);
		}
	}

	/**
	 * @param s
	 *            the station name
	 */
	//根据站点的名称取得经过此站点的所有线路
	private void processStationSearch(String s) {
		//用于存放线路名称
		Vector mm;
		//去掉首尾的空格
		s = s.trim();
		//获得经过此站点的所有路线
		mm = m.get_road_from_station_key(s);
		//若没找到，则提示没找到
		if ((mm.size() == 0)) {
			this.showDialog1(DIALOG_YES_NO_MESSAGE);
			return;
		}
		//将线路信息包进字符串变量中station_from_line中
		String resultsTextFormat = getString(R.string.station_from_line);
		this.list_title = String.format(resultsTextFormat, s);
		//显示查询结果
		draw_filter_list3(mm);

		return;
	}

	private boolean validateString(String s) {
		if (s.trim().equals(""))
			return false;
		if (s.indexOf('?') != -1) {
			return false;
		}
		return true;
	}

	/**
	 * @param sa
	 *            the first station name
	 * @param sb
	 *            the second station name
	 */
	Vector[] vv;
	//根据起始站和终点站获得乘车路线
	private void processinterSearch(String sa, String sb) {
		//取得乘车方案的所有信息
		vv = m.get_road_for_inter_station(sa, sb);
		//如果没有乘车方案，则返回空，并提示用户没有找到
		if (vv == null) {
			this.showDialog1(DIALOG_YES_NO_MESSAGE);
			return;
		}
		//转乘站点信息，如果有直达的，则存储的是“same”
		Enumeration seq = vv[2].elements();
		//起始站点信息
		Enumeration seq1 = vv[0].elements(); 
		//转乘路线信息
		Enumeration seq2 = vv[1].elements(); 
		String road;
		String road2;
		String middleStation;
		//初始化road_list
		road_list = new String[vv[0].size()];
		//用于记录元素的个数
		int j = 0;
		int direct=0;
		// 取得路线的信息
		while (seq.hasMoreElements()) {
			middleStation = (String) seq.nextElement();
			if (middleStation.compareTo("same") == 0) {
				// 将标志位置为1，表明是直达
				direct=1;
				road = (String) seq1.nextElement();
				Log.e("guojs",road);
				road_list[j++]=road;
			}else{
				// 将标志位置为0，表明是转乘
				direct=0;
				road = (String) seq1.nextElement();
				road2 = (String) seq2.nextElement();
				road_list[j++] = road + " -> " + road2;
			}
		}
		//direct =0 表明 午直达方案，=1表示有直达方案
		if(direct == 0)
			this.showDialog1(DIALOG_LIST1);//显示转乘方案列表
		else
			this.showDialog1(DIALOG_LIST_BUS);//显示直达方案列表
	}
	//显示转乘方案的路线
	private void draw_road_table1(int which) {

		TextView line, upload, download;
		
		table = (TableLayout) findViewById(R.id.menu);
		if (table == null) {
			Log.e(Globals.TAG, "tab is null");
		}
		//设置第一列为可收缩
		table.setColumnShrinkable(1, true);
		((TextView) findViewById(R.id.tab4_h2))
				.setText(getString(R.string.start_end));//首末站
		((TextView) findViewById(R.id.tab4_h3))
				.setText(getString(R.string.chufa1));//先乘坐
		((TextView) findViewById(R.id.tab4_h4))
				.setText(getString(R.string.daoda1));//在乘坐
		((TextView) findViewById(R.id.tab4_h5)).setText("");//设置不需要显示内容为空
		((TextView) findViewById(R.id.tab4_et5)).setText("");//设置不需要显示内容为空
		line = (TextView) findViewById(R.id.tab4_et2);
		upload = (TextView) findViewById(R.id.tab4_et3);
		download = (TextView) findViewById(R.id.tab4_et4);

		String line0 = (String) vv[0].elementAt(which);	//取得当前的起始线路
		String line1 = (String) vv[1].elementAt(which);	//取得当前的转乘线路
		String start = (String) vv[3].elementAt(which);//取得当前的转乘站点名称
		String middle = (String) vv[2].elementAt(which);//取得实际起始站名称
		String end = (String) vv[4].elementAt(which);//取得实际终点站名称

		line.setText(start + " -> " + end);
		CharSequence styledResults = Html.fromHtml(line0 + "\n\n"
				+ getString(R.string.chufa) + ":  " + start + "\n"
				+ getString(R.string.daoda) + ":  " + middle);
		upload.setText(styledResults);//以html格式显示起始站到转乘站路线
		styledResults = Html.fromHtml(line1 + "\n\n"
				+ getString(R.string.chufa) + ":  " + middle + "\n"
				+ getString(R.string.daoda) + ":  " + end);
		download.setText(styledResults);//以html格式显示转乘站到终点站路线

	}
}
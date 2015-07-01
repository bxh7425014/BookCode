package com.eoeAndroid.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityList2 extends Activity {

	private List<Map<String, Object>> data;
	private ListView listView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PrepareData();
		listView = new ListView(this);
		// 利用系统的layout显示一项
		 SimpleAdapter adapter = new SimpleAdapter(this, data,
		 android.R.layout.simple_list_item_1, new String[] { "姓名" },
		 new int[] { android.R.id.text1 });
		// 利用系统的layout显示两项
//		 SimpleAdapter adapter = new SimpleAdapter(this, data,
//		 android.R.layout.simple_list_item_2, new String[] { "姓名","性别" },
//		 new int[] { android.R.id.text1 , android.R.id.text2});
		// 利用自己的layout来进行显示两项
//		SimpleAdapter adapter = new SimpleAdapter(this, data,
//				R.layout.list_item, new String[] { "姓名", "性别" }, new int[] {
//						R.id.mview1, R.id.mview2 });
		listView.setAdapter(adapter);
		setContentView(listView);	
		
		OnItemClickListener listener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				setTitle(parent.getItemAtPosition(position).toString());
			}
		};
		listView.setOnItemClickListener(listener);
	}

	private void PrepareData() {
		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;
		item = new HashMap<String, Object>();
		item.put("姓名", "张三小朋友");
		item.put("性别", "男");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("姓名", "王五同学");
		item.put("性别", "男");
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("姓名", "小李师傅");
		item.put("性别", "女");
		data.add(item);
	}
}
package net.blogjava.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity implements OnItemSelectedListener, OnItemClickListener
{
	private static String[] data = new String[]
		             	                   		{ "机器化身", "变形金刚（真人版）2", "第九区", "火星任务", "人工智能", "钢铁侠", "铁臂阿童木 ", "未来战士",
		             	                   				"星际传奇",
		             	                   				"侏罗纪公园2:失落的世界   简介：本片原名《失落的世界》，由史蒂文．斯皮尔伯格率领《侏罗纪公园》的高个子数学专家杰夫高布伦，重回培养过恐龙的桑纳岛。" };

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Log.d("itemclick", "click " + position + " item");
		
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id)
	{
		Log.d("itemselected", "select " + position + " item");				
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
		Log.d("nothingselected", "nothing selected");		
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView lvCommonListView = (ListView) findViewById(R.id.lvCommonListView);

		//将ArrayAdapter构造方法的最后一个参数改成dataList，系统就会加载List对象的数据
		//List<String> dataList = new ArrayList<String>();
		//dataList.add("机器化身");
		//dataList.add("火星任务");
		ArrayAdapter<String> aaData = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);
		
		lvCommonListView.setAdapter(aaData);		
		lvCommonListView.setSelection(6);		
		lvCommonListView.setOnItemClickListener(this);
		lvCommonListView.setOnItemSelectedListener(this);

		
	}

}

package guo.supermario.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
//显示收到的信息列表
public class MsgListActivity extends Activity {
	//新建信息按键
	private Button sendnmsg;
	//信息列表
	private ListView msglist;
	//初始化函数
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//初始化界面元素
		sendnmsg = (Button) findViewById(R.id.newsms);
		msglist = (ListView) findViewById(R.id.msg_list);
		sendnmsg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//启动新建信息界面
				Intent intent = new Intent(MsgListActivity.this,
						SendSmsActivity.class);
				MsgListActivity.this.startActivity(intent);
				MsgListActivity.this.finish();
			}
		});		
		//新建适配器
		SimpleAdapter adapter = new SimpleAdapter(MsgListActivity.this,
				getSmsInPhone(), R.layout.showlist, new String[] { "imag",
						"listnum", "listmsg", "listtime" ,"listtype"}, new int[] {
						R.id.imag, R.id.listnum, R.id.listmsg, R.id.listtime ,R.id.type});
		//通知数据更改
		adapter.notifyDataSetChanged();
		//设置适配器
		msglist.setAdapter(adapter);
		//设置项目按键监听器
		msglist.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				//启动回复信息界面
				Intent intent = new Intent(MsgListActivity.this, Replymsg.class);
				//取得对应类表项目的元素
				Map<String, Object> getlist = getSmsInPhone().get(position);
				//将数据绑定到intent中进行传递
				intent.putExtra("listnum", (String) getlist.get("listnum"));
				intent.putExtra("listtime", (String) getlist.get("listtime"));
				intent.putExtra("listmsg", (String) getlist.get("listmsg"));
				intent.putExtra("listtype", (String)getlist.get("listtype"));
				MsgListActivity.this.startActivity(intent);
				MsgListActivity.this.finish();
			}
		});

	}
	//获取手机中所有信息
	public List<Map<String, Object>> getSmsInPhone() {
		//所有信息对应的uri地址
		final String SMS_URI_ALL = "content://sms/";
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
		try {
			Uri uri = Uri.parse(SMS_URI_ALL);
			//所要查询的列
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			//查询
			Cursor cur = getContentResolver().query(uri, projection, null,
					null, "date desc"); // 获取手机内部短信
			//将查询结果保存到列表中
			while (cur.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				//取得列的索引
				int index_Address = cur.getColumnIndex("address");
				int index_Person = cur.getColumnIndex("person");
				int index_Body = cur.getColumnIndex("body");
				int index_Date = cur.getColumnIndex("date");
				int index_Type = cur.getColumnIndex("type");
				//取得短信发送目标的电话
				String strAddress = cur.getString(index_Address);
				//取得索引
				cur.getInt(index_Person);
				//取得信息的内容
				String strbody = cur.getString(index_Body);
				//取得日期
				long longDate = cur.getLong(index_Date);
				//取得短信的类型，1为已接收，2为已发送
				int type= cur.getInt(index_Type);
				//格式化日期
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date d = new Date(longDate);
				String strDate = dateFormat.format(d);
				//将数据存储到map中
				map.put("listnum", strAddress);
				map.put("listmsg", strbody);
				map.put("listtime", strDate);
				if(type == 1)
				{
					map.put("listtype", "收");
				}else{
					map.put("listtype", "发");
				}
				//将map添加到列表中
				contents.add(map);
			}
			if (!cur.isClosed()) {
				cur.close();
				cur = null;
			}
		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}
			//返回信息列表
			return contents;
		}
}
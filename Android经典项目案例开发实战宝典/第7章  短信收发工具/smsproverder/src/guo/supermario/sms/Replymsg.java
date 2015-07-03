package guo.supermario.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Replymsg extends Activity {
	//显示收件人号码界面
	private TextView phonnum;
	//显示对方短信发送时间
	private TextView phontime;
	//显示对方短信内容
	private TextView phonmsg;
	//用于编辑发送内容
	private EditText sendcontent;
	//回复按键
	private Button rereplybt;
	private Intent intent;
	//接收器
	BroadcastReceiver send_sms;
	BroadcastReceiver delivered_sms;
	String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	//初始化函数
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replysms);
		//界面元素的初始化
		phonnum = (TextView) findViewById(R.id.renum);
		phontime = (TextView) findViewById(R.id.retime);
		phonmsg = (TextView) findViewById(R.id.recontent);
		sendcontent = (EditText) findViewById(R.id.replybody);
		rereplybt = (Button) findViewById(R.id.reply);
		send_sms=new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(_context,
							"短信发送成功!", Toast.LENGTH_SHORT)
							.show();
					break;
					//通用错误
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(_context,
							"SMS generic failure actions", Toast.LENGTH_SHORT)
							.show();
					break;
					//无线电被关闭
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast
							.makeText(_context,
									"SMS radio off failure actions",
									Toast.LENGTH_SHORT).show();
					break;
					//未提供pdu(协议描述单元 protocol description unit)
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(_context,
							"SMS null PDU failure actions", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
		};
		delivered_sms=new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				Toast.makeText(_context, "短信已被接收！",
						Toast.LENGTH_SHORT).show();
			}
		};
		// 注册广播
		registerReceiver(send_sms, new IntentFilter(SENT_SMS_ACTION));
		registerReceiver(delivered_sms, new IntentFilter(DELIVERED_SMS_ACTION));
		//获取信息
		getdata();
		//回复
		send();
	}
	// 获取信息
	private void getdata() {
		intent = getIntent();
		//获得传递过来的信息
		String listnum = intent.getStringExtra("listnum");
		String listtime = intent.getStringExtra("listtime");
		String listmsg = intent.getStringExtra("listmsg");
		//初始化界面文本
		phonnum.setText(listnum);
		phontime.setText(listtime);
		phonmsg.setText(listmsg);
	}
	//按键监听器
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return super.onKeyDown(keyCode, event);
	}
	//返回信息显示列表
	private void back() {
		Intent intentb = new Intent(Replymsg.this, MsgListActivity.class);
		Replymsg.this.startActivity(intentb);
		Replymsg.this.finish();
	}
	//回复短信
	private void send() {
		rereplybt.setOnClickListener(new OnClickListener() {
			String getnum;
			String getmsg;
			public void onClick(View v) {
				//获得发送号码
				getnum = phonnum.getText().toString();
				//获得发送内容
				getmsg = sendcontent.getText().toString();
				//实例化发送信息类
				SendMassage sendmsg = new SendMassage(Replymsg.this,getmsg,getnum);
				//发送信息
				sendmsg.sendmsg(send_sms,delivered_sms);
				ContentValues values=new ContentValues();
				//发送时间
				values.put("date", System.currentTimeMillis());
				//阅读状态
				values.put("read", 0);
				//1为收2为发
				values.put("type", 2);
				//送达号码
				values.put("address", getnum);
				//发送内容
				values.put("body", getmsg);
				getContentResolver().insert(Uri.parse("content://sms/sent"), values);
				sendcontent.setText("");
				//返回信息显示界面
				back();
			}
		});
	}
	//程序销毁时调用
	@Override
	public void onDestroy()
	{
		//注销函数
		unregisterReceiver(send_sms);
		unregisterReceiver(delivered_sms);
		super.onDestroy();
	}
}
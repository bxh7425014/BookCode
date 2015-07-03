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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//发送信息界面
public class SendSmsActivity extends Activity {
	//收件人号码
	EditText ednum;
	//短信内容
	EditText edbody;
	//发送按钮
	Button send;
	//发送状态广播
	BroadcastReceiver send_sms;
	//对方接受状态广播
	BroadcastReceiver delivered_sms;
	String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	String getnum;
	String getmsg;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmsg);
		//界面初始化
		ednum = (EditText) findViewById(R.id.ednum);
		edbody = (EditText) findViewById(R.id.edbody);
		send = (Button) findViewById(R.id.send);
		//发送按钮监听器
		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//取得输入的号码
				getnum = ednum.getText().toString();
				//取得输入的内容
				getmsg = edbody.getText().toString();
				//实例化发送信息类
				SendMassage sendmsg = new SendMassage(SendSmsActivity.this,
						getmsg, getnum);
				//发送信息
				sendmsg.sendmsg(send_sms,delivered_sms);
				//将信息存储到数据库“已发送”中
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
				edbody.setText("");
				//返回信息列表界面
				back();

			}
		});
		send_sms=new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				//发送成功
				case Activity.RESULT_OK:
					Toast.makeText(_context,
							"短信发送成功！", Toast.LENGTH_SHORT)
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
		// 注册广播接收器
		registerReceiver(send_sms, new IntentFilter(SENT_SMS_ACTION));
		registerReceiver(delivered_sms, new IntentFilter(DELIVERED_SMS_ACTION));
	}
	//返回信息列表
	private void back() {
		Intent intent = new Intent(SendSmsActivity.this, MsgListActivity.class);
		SendSmsActivity.this.startActivity(intent);
		SendSmsActivity.this.finish();

	}
	@Override
	public void onDestroy()
	{
		//注销广播接收器
		unregisterReceiver(send_sms);
		unregisterReceiver(delivered_sms);
		super.onDestroy();
	}
}
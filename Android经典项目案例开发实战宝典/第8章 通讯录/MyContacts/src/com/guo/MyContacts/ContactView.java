package com.guo.MyContacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactView extends Activity
{
	//姓名
	private TextView mTextViewName;
	//手机
	private TextView mTextViewMobile;
	//座机
	private TextView mTextViewHome;
	//住址
	private TextView mTextViewAddress;
	//电子邮箱
	private TextView mTextViewEmail;
	
    private Cursor mCursor;
    private Uri mUri;
    //设置菜单的序号
    private static final int REVERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDITOR_ID = Menu.FIRST + 2;
    private static final int CALL_ID = Menu.FIRST + 3;
    private static final int SENDSMS_ID = Menu.FIRST + 4;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mUri = getIntent().getData();		
		this.setContentView(R.layout.viewuser);
		//初始化界面元素
		mTextViewName = (TextView) findViewById(R.id.TextView_Name);
		mTextViewMobile = (TextView) findViewById(R.id.TextView_Mobile);
		mTextViewHome = (TextView) findViewById(R.id.TextView_Home);
		mTextViewAddress = (TextView) findViewById(R.id.TextView_Address);
		mTextViewEmail = (TextView) findViewById(R.id.TextView_Email);
		
	    // 获得并保存原始联系人信息
        mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
        mCursor.moveToFirst();
        if (mCursor != null)
		{
			// 读取并显示联系人信息
			mCursor.moveToFirst();
			
			mTextViewName.setText(mCursor.getString(ContactColumn.NAME_COLUMN));
			mTextViewMobile.setText(mCursor.getString(ContactColumn.MOBILENUM_COLUMN));
			mTextViewHome.setText(mCursor.getString(ContactColumn.HOMENUM_COLUMN));
			mTextViewAddress.setText(mCursor.getString(ContactColumn.ADDRESS_COLUMN));
			mTextViewEmail.setText(mCursor.getString(ContactColumn.EMAIL_COLUMN));
		}
		else
		{
			setTitle("错误信息");
		}
	}
	//添加菜单
    public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		//返回
		menu.add(0, REVERT_ID, 0, R.string.revert).setShortcut('0', 'r').setIcon(R.drawable.listuser);
		//删除联系人
		menu.add(0, DELETE_ID, 0, R.string.delete_user).setShortcut('0', 'd').setIcon(R.drawable.remove);
		//编辑联系人
		menu.add(0, EDITOR_ID, 0, R.string.editor_user).setShortcut('0', 'd').setIcon(R.drawable.edituser);
		//呼叫用户
		menu.add(0, CALL_ID, 0, R.string.call_user).setShortcut('0', 'd').setIcon(R.drawable.calluser)
				.setTitle(this.getResources().getString(R.string.call_user)+mTextViewName.getText());
		//发送短信
		menu.add(0, SENDSMS_ID, 0, R.string.sendsms_user).setShortcut('0', 'd').setIcon(R.drawable.sendsms)
		.setTitle(this.getResources().getString(R.string.sendsms_user)+mTextViewName.getText());
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			//删除
			case DELETE_ID:
				deleteContact();
				finish();
				break;
			//返回列表
			case REVERT_ID:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case EDITOR_ID:
			//编辑联系人
				startActivity(new Intent(Intent.ACTION_EDIT, mUri)); 
				finish();
				break;
			case CALL_ID:
			//呼叫联系人
		        Intent call = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mTextViewMobile.getText()));
		        startActivity(call);
				break;
			case SENDSMS_ID:
			//发短信给联系人
		        Intent sms = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+mTextViewMobile.getText()));
		        startActivity(sms);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 删除联系人信息
	private void deleteContact()
	{
		if (mCursor != null)
		{
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);
			setResult(RESULT_CANCELED);
		}
	}
}
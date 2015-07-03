package com.guo.MyContacts;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactEditor extends Activity
{
	//标志位常量，用于标志当前是新建状态还是编辑状态
	private static final int STATE_EDIT = 0;
    private static final int STATE_INSERT = 1;
    //
    private static final int REVERT_ID = Menu.FIRST;
    private static final int DISCARD_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;
    
    private Cursor mCursor;
    private int mState;		//当前处于新建状态还是编辑状态的标志位变量
    private Uri mUri;
    //界面元素
    private EditText nameText;
    private EditText mobileText;
    private EditText homeText;
    private EditText addressText;
    private EditText emailText;
    //按键
    private Button okButton;
    private Button cancelButton;
    
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		final String action = intent.getAction();
		//根据action的不同进行不同的操作
		//编辑联系人
		if (Intent.ACTION_EDIT.equals(action))
		{
			mState = STATE_EDIT;
			mUri = intent.getData();
		}
		else if (Intent.ACTION_INSERT.equals(action))
		{
			//添加新联系人
			mState = STATE_INSERT;
			mUri = getContentResolver().insert(intent.getData(), null);
			if (mUri == null)
			{
				finish();
				return;
			}
			setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));
		}
		//其他情况，退出
		else
		{
			finish();
			return;
		}        
        setContentView(R.layout.editorcontacts);
        //初始化界面文本框
        nameText = (EditText) findViewById(R.id.EditText01);
        mobileText = (EditText) findViewById(R.id.EditText02);
        homeText = (EditText) findViewById(R.id.EditText03);
        addressText = (EditText) findViewById(R.id.EditText04);
        emailText = (EditText) findViewById(R.id.EditText05);
        //初始化按键
        okButton = (Button)findViewById(R.id.Button01);
        cancelButton = (Button)findViewById(R.id.Button02);
        //设置确定按键监听器
        okButton.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v) 
			{
				String text = nameText.getText().toString();
				if(text.length() == 0)
				{
					//如果没有输入东西，则将原来的记录删除
					setResult(RESULT_CANCELED);
					deleteContact();
					finish();
				}
				else
				{
					//更新数据
					updateContact();
				}
			}       	
        });
        //设置取消按钮监听器
        cancelButton.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v) 
			{
				//不添加记录，也不保存记录
				setResult(RESULT_CANCELED);
				deleteContact();
				finish();

			}
        });
        // 获得并保存原始联系人信息
        mCursor = managedQuery(mUri, ContactColumn.PROJECTION, null, null, null);
        mCursor.moveToFirst();
        if (mCursor != null)
		{
			// 读取并显示联系人信息
			mCursor.moveToFirst();
			if (mState == STATE_EDIT)
			{
				setTitle(getText(R.string.editor_user));
			}
			else if (mState == STATE_INSERT)
			{
				setTitle(getText(R.string.add_user));
			}
			String name = mCursor.getString(ContactColumn.NAME_COLUMN);
			String moblie = mCursor.getString(ContactColumn.MOBILENUM_COLUMN);
			String home = mCursor.getString(ContactColumn.HOMENUM_COLUMN);
			String address = mCursor.getString(ContactColumn.ADDRESS_COLUMN);
			String email = mCursor.getString(ContactColumn.EMAIL_COLUMN);
			//显示信息
			nameText.setText(name);
			mobileText.setText(moblie);
			homeText.setText(home);
			addressText.setText(address);
			emailText.setText(email);
		}
		else
		{
			setTitle("错误信息");
		}
	}		
    //菜单选项
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        super.onCreateOptionsMenu(menu);
        if (mState == STATE_EDIT) 
        {
        	//返回按钮
            menu.add(0, REVERT_ID, 0, R.string.revert)
                    .setShortcut('0', 'r')
                    .setIcon(R.drawable.listuser);
            //删除联系人按钮
            menu.add(0, DELETE_ID, 0, R.string.delete_user)
            .setShortcut('0', 'f')
            .setIcon(R.drawable.remove);
        } 
        else 
        {
        	//返回按钮
            menu.add(0, DISCARD_ID, 0, R.string.revert)
                    .setShortcut('0', 'd')
                    .setIcon(R.drawable.listuser);
        }
        return true;
    }
    //菜单处理
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
	{
        switch (item.getItemId()) 
        {
        //删除联系人
        case DELETE_ID:
        	deleteContact();
            finish();
            break;
         //删除刚创建的空联系人
        case DISCARD_ID:
        	cancelContact();
        	finish();
            break;
        //直接返回
        case REVERT_ID:
        	finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
	
	//删除联系人信息
	private void deleteContact() 
	{
		if (mCursor != null) 
		{
            mCursor.close();
            mCursor = null;
            getContentResolver().delete(mUri, null, null);
            nameText.setText("");
        }
	}
	//丢弃信息
	private void cancelContact() 
	{
		if (mCursor != null) 
		{
			deleteContact();
        }
        setResult(RESULT_CANCELED);
        finish();
	}
	//更新 变更的信息
	private void updateContact() 
	{
		if (mCursor != null) 
		{
			mCursor.close();
            mCursor = null;
            ContentValues values = new ContentValues();
			values.put(ContactColumn.NAME, nameText.getText().toString());
			values.put(ContactColumn.MOBILENUM, mobileText.getText().toString());
			values.put(ContactColumn.HOMENUM, homeText.getText().toString());
			values.put(ContactColumn.ADDRESS, addressText.getText().toString());
			values.put(ContactColumn.EMAIL, emailText.getText().toString());
			//更新数据
            getContentResolver().update(mUri, values, null, null);
        }
        setResult(RESULT_CANCELED);
        finish();
	}
}
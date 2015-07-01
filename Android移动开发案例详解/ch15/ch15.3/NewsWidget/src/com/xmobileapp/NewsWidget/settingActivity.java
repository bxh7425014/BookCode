package com.xmobileapp.NewsWidget;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class settingActivity extends Activity{
	private Button btn_order,btn_disorder;
	private Button btn_save,btn_exit;
	private EditText ed_url,ed_autotime;
	private CheckBox cb_auto;
	private static final String PREFERENCE_NAME = "Settings";
	private static final String PREFERENCE_STRING_URL = "URL Address";
	private static final String PREFERENCE_STRING_AUTOTIME = "Auto Time";
	private SharedPreferences sp ;
	private void initViews()
	{
		btn_order = (Button)findViewById(R.id.btn_order);
		btn_disorder = (Button)findViewById(R.id.btn_disorder);
		btn_save = (Button)findViewById(R.id.btn_save);
		btn_exit = (Button)findViewById(R.id.btn_exit);
		ed_url = (EditText)findViewById(R.id.ed_url);
		ed_autotime = (EditText)findViewById(R.id.ed_time);
		cb_auto = (CheckBox)findViewById(R.id.cb_auto);
		ed_autotime.setEnabled(false);
		
		sp = this.getSharedPreferences(PREFERENCE_NAME, 0);
		String beforeURL = sp.getString(PREFERENCE_STRING_URL, "");
		int beforeTime = sp.getInt(PREFERENCE_STRING_AUTOTIME, 0);
		if(beforeTime>0)
		{
			cb_auto.setEnabled(true);
			cb_auto.setChecked(true);
			ed_autotime.setEnabled(true);
		}
		ed_autotime.setText(String.valueOf(beforeTime));
		if(beforeURL.equals(""))
		{
			btn_order.setEnabled(true);
			btn_disorder.setEnabled(false);
		}
		else
		{
			ed_url.setText(beforeURL);
			btn_order.setEnabled(false);
			btn_disorder.setEnabled(true);
		}
		btn_order.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				SharedPreferences.Editor edit = sp.edit();
				edit.putString(PREFERENCE_STRING_URL, ed_url.getText().toString());
				edit.commit();
				btn_order.setEnabled(false);
				btn_disorder.setEnabled(true);
			}	
		});
		btn_disorder.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				SharedPreferences.Editor edit = sp.edit();
				edit.putString(PREFERENCE_STRING_URL, "");
				edit.commit();
				btn_order.setEnabled(true);
				btn_disorder.setEnabled(false);
			}
		});
		btn_save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt(PREFERENCE_STRING_AUTOTIME, Integer.parseInt(ed_autotime.getText().toString()));
				edit.commit();
				finish();
				settingActivity.this.startService(new Intent(settingActivity.this,ProcessService.class));
			}		
		});
		btn_exit.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				finish();
			}
		});
		cb_auto.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
				if(isChecked)
				{
					ed_autotime.setEnabled(true);
				}
				else
				{
					ed_autotime.setEnabled(false);
				}
			}
		});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initViews();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

package com.yarin.android.Examples_09_02;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Activity01 extends Activity
{
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 4321;
    
    private ListView mList;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mList = (ListView) findViewById(R.id.ListView01);
		
		Button button = (Button) findViewById(R.id.Button01); 
		button.setOnClickListener(new View.OnClickListener() 
		{ 	            
			@Override          
			public void onClick(View v)
			{
				try
				{
					//通过Intent传递语音识别的模式,开启语音
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					//语言模式和自由形式的语音识别
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					//提示语音开始
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"开始语音");
					//开始执行我们的Intent、语音识别
					startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
				}
				catch (ActivityNotFoundException e)
				{
					//找不到语音设备装置
					Toast.makeText(Activity01.this,"ActivityNotFoundException", Toast.LENGTH_LONG).show(); 
				}
			}      
		}); 
	}
	
	//当语音结束时的回调函数onActivityResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 判断是否是我们执行的语音识别
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK)
		{
			// 取得语音的字符
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//设置视图更新
			//mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
			String resultsString = ""; 
			for (int i = 0; i < results.size(); i++)
			{
				resultsString += results.get(i);
			} 
			Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show(); 
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

}

//package com.example.android.apis.app;
//
//import com.example.android.apis.R;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.os.Bundle;
//import android.speech.RecognizerIntent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VoiceRecognition extends Activity implements OnClickListener {
//    
//    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
//    
//    private ListView mList;
//
//    /**
//     * Called with the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) 
//    {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.voice_recognition);
//
//        Button speakButton = (Button) findViewById(R.id.btn_speak);
//        
//        mList = (ListView) findViewById(R.id.list);
//
//        // Check to see if a recognition activity is present
//        PackageManager pm = getPackageManager();
//        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
//        if (activities.size() != 0)
//		{
//			speakButton.setOnClickListener(this);
//		}
//		else
//		{
//			speakButton.setEnabled(false);
//			speakButton.setText("Recognizer not present");
//		}
//    }
//
//
//    public void onClick(View v)
//	{
//		if (v.getId() == R.id.btn_speak)
//		{
//			startVoiceRecognitionActivity();
//		}
//	}
//
//
//    private void startVoiceRecognitionActivity()
//	{
//    	//通过Intent传递语音识别的模式
//		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//		//语言模式和自由形式的语音识别
//		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//		//提示语音开始
//		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
//		//开始执行我们的Intent、语音识别
//		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
//	}
//
//
//    //当语音结束时的回调函数onActivityResult
//    @Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK)
//		{
//			// 取得语音的字符
//			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//			mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
//		}
//
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//}
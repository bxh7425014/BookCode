package org.crazyit.io;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Speech extends Activity
{
	TextToSpeech tts;
	EditText editText;
	Button speech;
	Button record;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 初始化TextToSpeech对象
		tts = new TextToSpeech(this, new OnInitListener()
		{
			@Override
			public void onInit(int status)
			{
				// 如果装载TTS引擎成功
				if (status == TextToSpeech.SUCCESS)
				{
					// 设置使用美式英语朗读
					int result = tts.setLanguage(Locale.US);
					// 如果不支持所设置的语言
					if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
						&& result != TextToSpeech.LANG_AVAILABLE)
					{
						Toast.makeText(Speech.this, "TTS暂时不支持这种语言的朗读。", 50000)
							.show();
					}
				}
			}

		});
		editText = (EditText) findViewById(R.id.txt);
		speech = (Button) findViewById(R.id.speech);
		record = (Button) findViewById(R.id.record);
		speech.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 执行朗读
				tts.speak(editText.getText().toString(),
					TextToSpeech.QUEUE_ADD, null);
			}
		});
		record.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 将朗读文本的音频记录到指定文件
				tts.synthesizeToFile(editText.getText().toString(), null,
					"/mnt/sdcard/sound.wav");
				Toast.makeText(Speech.this, "声音记录成功！", 50000).show();
			}
		});
	}
	@Override
	public void onDestroy()
	{
		// 关闭TextToSpeech对象
		if (tts != null)
		{
			tts.shutdown();
		}
	}
}
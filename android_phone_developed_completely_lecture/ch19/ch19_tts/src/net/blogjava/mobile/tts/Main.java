package net.blogjava.mobile.tts;

import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements TextToSpeech.OnInitListener,
		OnClickListener
{
	private TextToSpeech tts;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tts = new TextToSpeech(this, this);
		Button button = (Button) findViewById(R.id.button);
		textView = (TextView) findViewById(R.id.textview);
		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View view)
	{

		tts
				.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH,
						null);

	}

	@Override
	public void onInit(int status)
	{
		if (status == TextToSpeech.SUCCESS)
		{
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED)
			{

				Toast.makeText(this, "Language is not available.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
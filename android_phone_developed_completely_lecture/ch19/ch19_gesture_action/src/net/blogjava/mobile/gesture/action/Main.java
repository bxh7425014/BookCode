package net.blogjava.mobile.gesture.action;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity implements OnGesturePerformedListener
{
	private GestureLibrary gestureLibrary;

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture)
	{
		ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);

		if (predictions.size() > 0)
		{

			int n = 0;
			for (int i = 0; i < predictions.size(); i++)
			{
				Prediction prediction = predictions.get(i);
				if (prediction.score > 1.0)
				{
					Intent intent = null;
					Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
					if ("action_call".equals(prediction.name))
					{
						intent = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:12345678"));
					} 
					else if ("action_call_button".equals(prediction.name))
					{
						intent = new Intent(Intent.ACTION_CALL_BUTTON);

					}
					else if ("action_dial".equals(prediction.name))
					{
						intent = new Intent(Intent.ACTION_DIAL, Uri
								.parse("tel:12345678"));

					}
					if (intent != null)
						startActivity(intent);
					n++;
					break;
				}
			}

			if (n == 0)
				Toast.makeText(this, "没有符合要求的手势.", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		// gestureLibrary = GestureLibraries.fromFile("/sdcard/gestures");
		gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (gestureLibrary.load())
		{
			setTitle("手势文件装载成功（识别动作）.");
			GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);

			gestureOverlayView.addOnGesturePerformedListener(this);
		}
		else
		{
			setTitle("手势文件装载失败.");
		}
	}
}
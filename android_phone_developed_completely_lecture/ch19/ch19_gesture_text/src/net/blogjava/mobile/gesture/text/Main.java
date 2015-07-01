package net.blogjava.mobile.gesture.text;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
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

			StringBuilder sb = new StringBuilder();
			int n = 0;
			for (int i = 0; i < predictions.size(); i++)
			{
				Prediction prediction = predictions.get(i);
				if (prediction.score > 1.0)
				{
					sb.append("score:" + prediction.score + "  name:"
							+ prediction.name + "\n");
					n++;
				}
			}
			
			sb.insert(0,n + "个相匹配的手势.\n");
			Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		//gestureLibrary = GestureLibraries.fromFile("/sdcard/gestures");
		gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (gestureLibrary.load())
		{
			setTitle("手势文件装载成功（输出文本）.");
			GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures);
			gestureOverlayView.addOnGesturePerformedListener(this);
		}
		else
		{
			setTitle("手势文件装载失败.");
		}
	}
}
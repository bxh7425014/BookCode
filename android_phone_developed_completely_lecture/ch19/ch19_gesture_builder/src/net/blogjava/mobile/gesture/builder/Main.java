package net.blogjava.mobile.gesture.builder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity
{
	private EditText editText;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
		overlay.addOnGestureListener(new GesturesProcessor());
		editText = (EditText) findViewById(R.id.gesture_name);
	}

	private class GesturesProcessor implements
			GestureOverlayView.OnGestureListener
	{

		public void onGestureStarted(GestureOverlayView overlay,
				MotionEvent event)
		{
			
		}

		public void onGesture(GestureOverlayView overlay, MotionEvent event)
		{
		}

		public void onGestureEnded(final GestureOverlayView overlay,
				MotionEvent event)
		{
			final Gesture gesture = overlay.getGesture();
			View gestureView = getLayoutInflater().inflate(R.layout.gesture,
					null);
			final TextView textView = (TextView) gestureView
					.findViewById(R.id.textview);
			ImageView imageView = (ImageView) gestureView
					.findViewById(R.id.imageview);

			Bitmap bitmap = gesture.toBitmap(128, 128, 8, 0xFFFFFF00);
			imageView.setImageBitmap(bitmap);
			textView.setText("手势名：" + editText.getText());

			new AlertDialog.Builder(Main.this).setView(gestureView)
					.setPositiveButton("保存", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							GestureLibrary store = GestureLibraries
									.fromFile("/sdcard/mygestures");

							store.addGesture(textView.getText().toString(),
									gesture);
							store.save();
						}
					}).setNegativeButton("取消", null).show();
		}

		public void onGestureCancelled(GestureOverlayView overlay,
				MotionEvent event)
		{
		}
	}
}
package net.blogjava.mobile.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;


public class AlarmAlert extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String remindMsg = bundle.getString("remindMsg");
		if (bundle.getBoolean("ring"))
		{
			Main.mediaPlayer = MediaPlayer.create(this, R.raw.ring);
			try
			{
				Main.mediaPlayer.setLooping(true);
				Main.mediaPlayer.prepare();
			}
			catch (Exception e)
			{
				setTitle(e.getMessage());
			}
			Main.mediaPlayer.start();
		}
		if(bundle.getBoolean("shake"))
		{
			Main.vibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
			Main.vibrator.vibrate(new long[]{1000, 100, 100,1000}, -1);
		}
		new AlertDialog.Builder(AlarmAlert.this).setIcon(R.drawable.clock)
				.setTitle("Ã·–—").setMessage(remindMsg).setPositiveButton("πÿµÙÀ˚",
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int whichButton)
							{
								AlarmAlert.this.finish();
								if (Main.mediaPlayer != null)
									Main.mediaPlayer.stop();
								if(Main.vibrator != null)
									Main.vibrator.cancel();
							}
						}).show();

	}
}

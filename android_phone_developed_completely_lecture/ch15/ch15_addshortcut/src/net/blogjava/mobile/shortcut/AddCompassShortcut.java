package net.blogjava.mobile.shortcut;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

public class AddCompassShortcut extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction()))
		{
			Intent addShortcutIntent = new Intent();
			addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "µç×ÓÂÞÅÌ");
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
					R.drawable.compass_shortcut);
			addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
					icon);
			Intent callCompass = new Intent("net.blogjava.mobile.compass.COMPASS", Uri
					.parse("compass://host"));
			addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
					callCompass);

			setResult(RESULT_OK, addShortcutIntent);
		}
		else
		{
			setResult(RESULT_CANCELED);

		}
		finish();
	}
}

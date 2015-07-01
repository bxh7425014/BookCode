package net.blogjava.mobile.livefolder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.LiveFolders;
import android.provider.Contacts.Phones;

public class AddLiveFolder extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(getIntent()
				.getAction()))
		{ 
			Intent intent = new Intent();
			intent.setData(Contacts.People.CONTENT_URI);
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT,
					new Intent(Intent.ACTION_CALL, Contacts.People.CONTENT_URI));
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, "µç»°±¾");
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,
					Intent.ShortcutIconResource.fromContext(this,
							R.drawable.phone));
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE,
					LiveFolders.DISPLAY_MODE_LIST);
			
			setResult(RESULT_OK, intent);
		}
		else
		{
			setResult(RESULT_CANCELED);
		}
		finish();
	}
}

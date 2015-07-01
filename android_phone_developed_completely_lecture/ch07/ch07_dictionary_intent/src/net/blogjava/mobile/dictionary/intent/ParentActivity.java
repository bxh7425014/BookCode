package net.blogjava.mobile.dictionary.intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ParentActivity extends Activity
{
	protected final String DATABASE_PATH = android.os.Environment
	.getExternalStorageDirectory().getAbsolutePath()
	+ "/dictionary";
	protected final String DATABASE_FILENAME = "dictionary.db";
	protected SQLiteDatabase database;
	protected SQLiteDatabase openDatabase()
	{
		try
		{
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			if (!dir.exists())
				dir.mkdir();
			if (!(new File(databaseFilename)).exists())
			{
				Log.d("aa", "bbb");
				InputStream is = getResources().openRawResource(
						R.raw.dictionary);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		}
		catch (Exception e)
		{
		}
		return null;
	}

}

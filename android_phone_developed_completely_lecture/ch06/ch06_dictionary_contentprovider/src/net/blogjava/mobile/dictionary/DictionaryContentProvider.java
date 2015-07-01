package net.blogjava.mobile.dictionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DictionaryContentProvider extends ContentProvider
{
	private static UriMatcher uriMatcher;
	private static final String AUTHORITY = "net.blogjava.mobile.dictionarycontentprovider";
	private static final int SINGLE_WORD = 1;
	private static final int PREFIX_WORDS = 2;
	public static final String DATABASE_PATH = android.os.Environment
	.getExternalStorageDirectory().getAbsolutePath()
	+ "/dictionary";
	public static final String DATABASE_FILENAME = "dictionary.db";
	private SQLiteDatabase database;
	static
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "single", SINGLE_WORD);
		uriMatcher.addURI(AUTHORITY, "prefix/*", PREFIX_WORDS);
	}
	private SQLiteDatabase openDatabase()
	{
		try
		{
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			if (!dir.exists())
				dir.mkdir();
			
			if (!(new File(databaseFilename)).exists())
			{
				InputStream is = getContext().getResources().openRawResource(
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
			Log.d("error", e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		return 0;
	}

	@Override
	public String getType(Uri uri)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate()
	{

		database = openDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder)
	{
		Cursor cursor = null;
		

		switch (uriMatcher.match(uri))
		{
			case SINGLE_WORD:
				cursor = database.query("t_words", projection, selection,
						selectionArgs, null, null, sortOrder);
				break;

			case PREFIX_WORDS:
				String word = uri.getPathSegments().get(1);
				cursor = database
						.rawQuery(
								"select english as _id, chinese from t_words where english like ?",
								new String[]
								{ word + "%" });
				break;

			default:
				throw new IllegalArgumentException("<" + uri + ">格式不正确.");
		}

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

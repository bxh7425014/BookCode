package net.blogjava.mobile.contacts;

import net.blogjava.mobile.db.DBService;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ContactContentProvider extends ContentProvider
{
	private static UriMatcher uriMatcher;
	private static final String AUTHORITY = "net.blogjava.mobile.contactcontentprovider";
	private static final int ALL_CONTACTS = 1;
	private DBService dbService;

	static
	{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, null, ALL_CONTACTS);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{

		return dbService.getWritableDatabase().delete("t_contacts", selection,
				selectionArgs);
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

		dbService = new DBService(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder)
	{
		String sql = "select id as _id, name,telephone, photo from t_contacts order by name";
		Cursor cursor = dbService.query(sql, null);
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

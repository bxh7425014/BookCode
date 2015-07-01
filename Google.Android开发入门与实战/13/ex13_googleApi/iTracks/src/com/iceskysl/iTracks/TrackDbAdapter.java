package com.iceskysl.iTracks;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TrackDbAdapter extends DbAdapter{
	private static final String TAG = "TrackDbAdapter";

	public static final String TABLE_NAME = "tracks";

	public static final String ID = "_id";
	public static final String KEY_ROWID = "_id";
	public static final String NAME = "name";
	public static final String DESC = "desc";
	public static final String DIST = "distance";
	public static final String TRACKEDTIME = "tracked_time";
	public static final String LOCATE_COUNT = "locats_count";
	public static final String CREATED = "created_at";
	public static final String UPDATED = "updated_at";
	public static final String AVGSPEED = "avg_speed";
	public static final String MAXSPEED = "max_speed";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	public TrackDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}
	
	public TrackDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public Cursor getTrack(long rowId) throws SQLException {
		Cursor mCursor =
		mDb.query(true, TABLE_NAME, new String[] { KEY_ROWID, NAME,
				DESC, CREATED }, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public long createTrack(String name, String desc) {
		Log.d(TAG, "createTrack.");
		ContentValues initialValues = new ContentValues();
		initialValues.put(NAME, name);
		initialValues.put(DESC, desc);
		Calendar calendar = Calendar.getInstance();
		String created = calendar.get(Calendar.YEAR) + "-" +calendar.get(Calendar.MONTH) + "-" +  calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"  + calendar.get(Calendar.SECOND);
		initialValues.put(CREATED, created);
		initialValues.put(UPDATED, created);
		return mDb.insert(TABLE_NAME, null, initialValues);
	}
	
	//
	public boolean deleteTrack(long rowId) {
		return mDb.delete(TABLE_NAME, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getAllTracks() {
		return mDb.query(TABLE_NAME, new String[] { ID, NAME,
				DESC, CREATED }, null, null, null, null, "updated_at desc");
	}
	
	public boolean updateTrack(long rowId, String name, String desc) {
		ContentValues args = new ContentValues();
		args.put(NAME, name);
		args.put(DESC, desc);
		Calendar calendar = Calendar.getInstance();
		String updated = calendar.get(Calendar.YEAR) + "-" +calendar.get(Calendar.MONTH) + "-" +  calendar.get(Calendar.DAY_OF_MONTH) + " "
		+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
		+ calendar.get(Calendar.MINUTE) + ":"  + calendar.get(Calendar.SECOND);
		args.put(UPDATED, updated);

		return mDb.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	
}

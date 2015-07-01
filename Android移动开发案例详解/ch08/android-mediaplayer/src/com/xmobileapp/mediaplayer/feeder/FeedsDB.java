
package com.xmobileapp.mediaplayer.feeder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xmobileapp.mediaplayer.synd.Article;
import com.xmobileapp.mediaplayer.synd.Feed;

public class FeedsDB  {

	private static final String CREATE_TABLE_FEEDS = "create table feeds (feed_id integer primary key autoincrement, "
			+ "title text not null, url text not null, imageurl text not null);";

	private static final String CREATE_TABLE_ARTICLES = "create table articles (article_id integer primary key autoincrement, "
			+ "feed_id int not null, title text not null, url text not null, imageurl text not null);";

	private static final String FEEDS_TABLE = "feeds";
	private static final String ARTICLES_TABLE = "articles";
	private static final String DATABASE_NAME = "feedsdb2";
	private static final int DATABASE_VERSION = 2;


    /**
     * This class helps open, create, and upgrade the database file.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	try
        	{
        		db.execSQL(CREATE_TABLE_FEEDS);
        		db.execSQL(CREATE_TABLE_ARTICLES);
        	}
        	catch (Exception e)
        	{
        		Log.i(DATABASE_NAME,"tables already exist");
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DATABASE_NAME, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS feeds");
            db.execSQL("DROP TABLE IF EXISTS articles");
            
            onCreate(db);
        }
    }

    private DatabaseHelper mOpenHelper;
    
    private FeedsDB (Context ctx)
    {
    	mOpenHelper = new DatabaseHelper(ctx);
    }

	public boolean insertFeed(String title, URL url, URL imageUrl) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("url", url.toString());
		
		if (imageUrl == null)
			values.put("imageurl", "");
		else 
			values.put("imageurl", imageUrl.toString());
		
		boolean resp = (db.insert(FEEDS_TABLE, null, values) > 0);
		
		return resp;
	}

	public boolean deleteFeed(Long feedId) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		boolean resp = (db.delete(FEEDS_TABLE, "feed_id=" + feedId.toString(), null) > 0);
		
		db.close();
		
		return resp;
	}

	public boolean insertArticle(Long feedId, String title, URL url, URL imageUrl) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("feed_id", feedId);
		values.put("title", title);
		values.put("url", url.toString());
		
		if (imageUrl == null)
			values.put("imageurl", "");
		else 
			values.put("imageurl", imageUrl.toString());
		
		boolean resp =  (db.insert(ARTICLES_TABLE, null, values) > 0);
		
		db.close();
		
		return resp;
	}

	public boolean deleteArticles(Long feedId) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		boolean resp = (db.delete(ARTICLES_TABLE, "feed_id=" + feedId.toString(), null) > 0);
		
		db.close();
		
		return resp;
	}

	public List<Feed> getFeeds() {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		try {
			
			
			Cursor c = db.query(FEEDS_TABLE, new String[] { "feed_id", "title",
					"url", "imageurl" }, null, null, null, null, null);

			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				Feed feed = new Feed();
				feed.feedId = c.getLong(0);
				feed.title = c.getString(1);
				feed.url = new URL(c.getString(2));
				
				if (c.getString(3)!=null && c.getString(3).length()>0)
					feed.imageUrl = new URL(c.getString(3));
				
				feeds.add(feed);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("NewsDroid", e.toString());
		} catch (MalformedURLException e) {
			Log.e("NewsDroid", e.toString());
		}
		finally
		{
			db.close();
		}
		return feeds;
	}

	public List<Article> getArticles(Long feedId) {
		ArrayList<Article> articles = new ArrayList<Article>();
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		try {
			
			
			Cursor c = db.query(ARTICLES_TABLE, new String[] { "article_id",
					"feed_id", "title", "url", "imageurl" },
					"feed_id=" + feedId.toString(), null, null, null, null);

			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				Article article = new Article();
				article.articleId = c.getLong(0);
				article.feedId = c.getLong(1);
				article.title = c.getString(2);
				
				if (c.getString(3)!=null && c.getString(3).length()>0)
					article.url = new URL(c.getString(3));
				
				if (c.getString(4)!=null && c.getString(4).length()>0)
					article.imageUrl = new URL(c.getString(4));
				
				
				articles.add(article);
				c.moveToNext();
			}
		} catch (SQLException e) {
			Log.e("NewsDroid", e.toString());
		} catch (MalformedURLException e) {
			Log.e("NewsDroid", e.toString());
		}
		finally
		{
			db.close();
		}
		return articles;
	}
	
	private static FeedsDB feedDB = null;
	
	public static FeedsDB getInstance (Context ctx)
	{
		if (feedDB == null)
		{
			feedDB = new FeedsDB(ctx);
		}
		
		return feedDB;
	}

}

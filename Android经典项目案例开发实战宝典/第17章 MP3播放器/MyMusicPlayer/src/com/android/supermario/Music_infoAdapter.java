package com.android.supermario;

import java.util.ArrayList;
import java.util.List;

import com.android.supermario.R;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Music_infoAdapter extends BaseAdapter {
	// 用来获得ContentProvider(共享数据库)
	public ContentResolver cr;
	// 用来装查询到得音乐文件数据
	public Cursor mCursor;
	// 歌曲列表信息
	public static List<MusicInfomation> musicList;
	public MusicInfomation mi;

	public Context context;

	// 音乐信息：1、歌曲名，2、歌手，3、歌曲时间，4、专辑(专辑图片，专辑名称，专辑ID[用来获取图片])，5、歌曲大小
	public Music_infoAdapter(Context context) {
		this.context = context;
		// 取得数据库对象
		cr = context.getContentResolver();
		//初始化音乐列表数组
		musicList = new ArrayList<MusicInfomation>();
		String[] s1 = new String[] {
				// 歌曲名
				MediaStore.Audio.Media.DISPLAY_NAME,
				// 专辑名
				MediaStore.Audio.Media.ALBUM,
				// 歌手名
				MediaStore.Audio.Media.ARTIST,
				// 歌曲时间
				MediaStore.Audio.Media.DURATION,
				// 歌曲大小
				MediaStore.Audio.Media.SIZE,
				// 歌曲ID
				MediaStore.Audio.Media._ID,
				// 歌曲路径
				MediaStore.Audio.Media.DATA };

		// 查询所有音乐信息
		mCursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, s1,
				null, null, null);

		if (mCursor != null) {
			// 移动到第一个
			mCursor.moveToFirst();
			// 获得歌曲的各种属性
			for (int i = 0; i < mCursor.getCount(); i++) {
				// 过滤mp3文件
				if (mCursor.getString(0).endsWith(".mp3")) {

					mi = new MusicInfomation();
					mi.setMusicName(mCursor.getString(0));
					mi.setMusicAlbum(mCursor.getString(1));
					mi.setMusicSinger(mCursor.getString(2));
					mi.setMusicTime(mCursor.getInt(3));
					mi.setMusicSize(mCursor.getInt(4));
					mi.set_id(mCursor.getInt(5));
					mi.setMusicPath(mCursor.getString(6));
					// 装载到列表中
					musicList.add(mi);
				}
				// 移动到下一个
				mCursor.moveToNext();
			}
		}
	}
	//获得数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicList.size();
	}
	//获得指定元素
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return musicList.get(arg0);
	}
	//获得指定元素
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//获得视图
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//膨胀出视图
		LayoutInflater li = LayoutInflater.from(context);
		View v = li.inflate(R.layout.musiclist_item, null);

		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		//初始化界面元素
		tv1 = (TextView) v.findViewById(R.id.tv1);
		tv2 = (TextView) v.findViewById(R.id.tv2);
		tv3 = (TextView) v.findViewById(R.id.tv3);
		tv4 = (TextView) v.findViewById(R.id.tv4);
		//为界面元素设置内容
		tv1.setText(musicList.get(arg0).getMusicName());
		tv2.setText(toTime(musicList.get(arg0).getMusicTime()));
		tv3.setText(musicList.get(arg0).getMusicPath());
		tv4.setText(toMB(musicList.get(arg0).getMusicSize()) + "MB");
		return v;
	}

	/**
	 * 时间转化处理
	 */
	public static String toTime(int time) {
		time /= 1000;
		int minute = time / 60;
		int second = time % 60;
		minute %= 60;
		//格式化时间
		return String.format(" %02d:%02d ", minute, second);
	}

	/**
	 * 文件大小转换，将B转换为MB
	 */
	public String toMB(int size) {
		float a = (float) size / (float) (1024 * 1024);
		String b = Float.toString(a);
		int c = b.indexOf(".");
		String fileSize = "";
		fileSize += b.substring(0, c + 2);
		return fileSize;
	}

	/**
	 * 歌曲专辑图片显示,如果有歌曲图片，才会返回，否则为null，要注意判断
	 * 
	 * @param trackId是音乐的id
	 * @return 返回类型是String 类型的图片地址，也就是uri
	 */
	public String getAlbumArt(int trackId) {
		//根据音乐的id获得专辑的id
		String mUriTrack = "content://media/external/audio/media/#";
		String[] projection = new String[] { "album_id" };
		String selection = "_id = ?";
		String[] selectionArgs = new String[] { Integer.toString(trackId) };
		Cursor mcCursor = context.getContentResolver().query(
				Uri.parse(mUriTrack), projection, selection, selectionArgs,
				null);
		int album_id = 0;
		if (mcCursor.getCount() > 0 && mcCursor.getColumnCount() > 0) {
			mcCursor.moveToNext();
			album_id = mcCursor.getInt(0);
		}
		mcCursor.close();
		mcCursor = null;

		if (album_id < 0) {
			return null;
		}
		//根据专辑的id获得专辑图片的uri地址
		String mUriAlbums = "content://media/external/audio/albums";
		projection = new String[] { "album_art" };
		mcCursor = context.getContentResolver().query(
				Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
				projection, null, null, null);
		String album_art = null;
		if (mcCursor.getCount() > 0 && mcCursor.getColumnCount() > 0) {
			mcCursor.moveToNext();
			album_art = mcCursor.getString(0);
		}
		mcCursor.close();
		mcCursor = null;

		return album_art;
	}

	/**
	 * 1、歌曲名 2、歌手 3、歌曲时间 4、专辑(专辑图片，专辑名称，专辑ID[用来获取图片]) 5、歌曲大小
	 */
	public class MusicInfomation {

		private int _id;
		private String musicName;
		private String musicSinger;
		private int musicTime;
		private String musicAlbum;
		private int musicSize;
		private String musicPath;
		//取得id
		public int get_id() {
			return _id;
		}
		//设置id
		public void set_id(int _id) {
			this._id = _id;
		}
		//取得歌曲名
		public String getMusicName() {
			return musicName;
		}
		//设置歌曲名
		public void setMusicName(String musicName) {
			this.musicName = musicName;
		}
		//取得歌手名
		public String getMusicSinger() {
			return musicSinger;
		}
		//设置歌手名
		public void setMusicSinger(String musicSinger) {
			this.musicSinger = musicSinger;
		}
		//取得音乐播放总时间
		public int getMusicTime() {
			return musicTime;
		}
		//设置音乐播放总时间
		public void setMusicTime(int musicTime) {
			this.musicTime = musicTime;
		}
		//取得音乐相册
		public String getMusicAlbum() {
			return musicAlbum;
		}
		//设置音乐相册
		public void setMusicAlbum(String musicAlbum) {
			this.musicAlbum = musicAlbum;
		}
		//取得音乐大小
		public int getMusicSize() {
			return musicSize;
		}
		//设置音乐大小
		public void setMusicSize(int musicSize) {
			this.musicSize = musicSize;
		}
		//取得音乐路径
		public String getMusicPath() {
			return musicPath;
		}
		//设置音乐路径
		public void setMusicPath(String musicPath) {
			this.musicPath = musicPath;
		}
	}
}
package com.android.supermario;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicSpecialView extends BaseAdapter {
	public Context context;
	GridView gv_1;
	public static MusicSpecialView msv;

	public void disPlaySpecial(GridView gv, Context context) {
		this.context = context;
		//初始化视图
		msv = new MusicSpecialView();
		this.gv_1 = gv;
		this.gv_1.setAdapter(msv);

	}
	//获得音乐文件总数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Music_infoAdapter.musicList.size();
	}
	//取得指定的文件
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Music_infoAdapter.musicList.get(arg0);
	}
	//取得指定的文件
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//获得每一个元素的视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//膨胀出视图
		LayoutInflater lif = LayoutInflater.from(MusicPlayerActivity.context);
		View v = lif.inflate(R.layout.gridspecial_item, null);
		//每一行元素包含一个图片视图和两个文本视图
		ImageView iv;
		TextView tv1;
		TextView tv2;
		//初始化界面元素
		iv = (ImageView) v.findViewById(R.id.gridspecial_view1);
		tv1 = (TextView) v.findViewById(R.id.album_xxx);
		tv2 = (TextView) v.findViewById(R.id.artist_xxx);

		// 获取专辑图片路径
		String url = MusicPlayerActivity.music_info
				.getAlbumArt(Music_infoAdapter.musicList.get(position).get_id());
		if (url != null) {
			//设置专辑对应的图片
			iv.setImageURI(Uri.parse(url));
		} else {
			//设置默认图片
			iv.setImageResource(R.drawable.default_album);
		}
		iv.setAnimation(AnimationUtils.loadAnimation(
				MusicPlayerActivity.context, R.anim.alpha_z));

		// 带动画显示专辑名
		tv1.setText(Music_infoAdapter.musicList.get(position).getMusicAlbum());
		tv1.setAnimation(AnimationUtils.loadAnimation(
				MusicPlayerActivity.context, R.anim.alpha_y));

		// 带动画显示歌手名
		tv2.setText(Music_infoAdapter.musicList.get(position).getMusicSinger());
		tv2.setAnimation(AnimationUtils.loadAnimation(
				MusicPlayerActivity.context, R.anim.alpha_y));
		return v;
	}
}
package com.android.supermario;
import android.content.Context;
import android.widget.ListView;
//音乐播放列表
public class MusicListView {
	ListView lv_1;
	public static Music_infoAdapter m_info_1;
	public void disPlayList(ListView lv, Context context) {
		//取得音乐文件数据适配器
		m_info_1 = new Music_infoAdapter(context);
		this.lv_1 = lv;
		//为音乐列表设置适配器
		this.lv_1.setAdapter(m_info_1);
	}
}
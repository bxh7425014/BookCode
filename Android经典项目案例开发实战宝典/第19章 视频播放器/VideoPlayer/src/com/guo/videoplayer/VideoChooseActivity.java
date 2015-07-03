package com.guo.videoplayer;

import java.util.LinkedList;
import com.guo.videoplayer.VideoPlayerActivity.MovieInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
//视频选择界面
public class VideoChooseActivity extends Activity{
	//视频播放列表
	private LinkedList<MovieInfo> mLinkedList;
	private LayoutInflater mInflater;
	View root;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);
		//取得视频播放列表
		mLinkedList = VideoPlayerActivity.playList;
		mInflater = getLayoutInflater();
		//返回按键
		ImageButton iButton = (ImageButton) findViewById(R.id.cancel);
		iButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//关闭当前界面
				VideoChooseActivity.this.finish();
			}			
		});
		//取得播放列表界面
		ListView myListView = (ListView) findViewById(R.id.list);
		//设置适配器
		myListView.setAdapter(new BaseAdapter(){
			//取得数量
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mLinkedList.size();
			}
			//取得元素
			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}
			//取得元素id
			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}
			//取得视图
			@Override
			public View getView(int arg0, View convertView, ViewGroup arg2) {
				// TODO Auto-generated method stub
				if(convertView==null){
					convertView = mInflater.inflate(R.layout.list, null);
				}
				//显示视频文件名称
				TextView text = (TextView) convertView.findViewById(R.id.text);
				text.setText(mLinkedList.get(arg0).displayName);				
				return convertView;   
			}			
		});
		//设置按键监听器
		myListView.setOnItemClickListener(new OnItemClickListener(){
			//按键被按下
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//发送intent给主界面，告知欲播放的视频文件信息
				Intent intent = new Intent();
				intent.putExtra("CHOOSE", arg2);
				VideoChooseActivity.this.setResult(Activity.RESULT_OK, intent);
				VideoChooseActivity.this.finish();
				}
		});
	}
}
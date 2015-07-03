package com.rss.activity;

import java.util.ArrayList;
import java.util.List;
import com.rss.data.ChannelDataHelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//RSS源选择界面
public class SelectChannel extends ListActivity{
	//menu菜单的id
	private static final int MENU_ADD = Menu.FIRST;
	private static final int MENU_QUIT = MENU_ADD + 1;
	//RSS源适配器
	ChannelAdapter mAdapter;
	//RSS源数据库类
	ChannelDataHelper mChannel;
	//RSS源数组
	List<String> channelList=new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mChannel=new ChannelDataHelper(this);
		//取得数据库所有内容
		channelList=mChannel.GetChannelList();
		mAdapter=new ChannelAdapter(this);
		//数据库无内容
		if(mAdapter.getCount() == 0)
			Toast.makeText(this, "尚未添加任何频道", Toast.LENGTH_SHORT).show();
		setTitle("频道选择");
		//设置适配器
		setListAdapter(mAdapter);
		
	}
	//ListView是适配器
	class ChannelAdapter extends BaseAdapter  {
		private LayoutInflater mInflator;
		//构造函数
		public ChannelAdapter(Context context) {
			this.mInflator = LayoutInflater.from(context);
		}
		//取得数组大小
		public int getCount() {
			return channelList.size();
		}
		//取得当前位置的元素
		public String getItem(int position) {
			return channelList.get(position);
		}
		//取得当前位置的id
		public long getItemId(int position) {
			return 0;
		}
		//取得当前位置的视图
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vRss = null;
			//记录当前的位置
			final int row = position;
			//初始化界面元素类
			vRss = new ViewHolder();
			//膨胀出界面
			convertView = mInflator.inflate(R.layout.item, null);
			//取得界面元素
			vRss.textLayout=(LinearLayout)convertView.findViewById(R.id.textLayout);	
			vRss.channel = (TextView)convertView.findViewById(R.id.title);	
			//设置标题字体大小
			vRss.channel.setTextSize(25);
			vRss.delBtn = (Button)convertView.findViewById(R.id.del_btn);				
			//取得标题的文本内容
			String title = channelList.get(position);
			vRss.channel.setText(title);			
			Log.e("guojs",channelList.get(row));
			//设置按键检监听器
			vRss.textLayout.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String channelName=channelList.get(row);
					Intent it=new Intent();
					Log.e("channel-url",mChannel.getUrlByChannel(channelName));
					//传递当前点击RSS源对应的URL地址
					it.putExtra("channel", mChannel.getUrlByChannel(channelName));
					it.setClass(SelectChannel.this, ActivityMain.class);
					//启动显示当前RSS源对应信息的界面
					startActivity(it);
				}				
			});
			//删除当前RSS源
			vRss.delBtn.setOnClickListener(new OnClickListener() {				
				public void onClick(View v) {
					delRssInfo();					
				}
				private void delRssInfo() {
					//删除成功
					if(-1 != mChannel.DelChannelInfo(channelList.get(row)))
					{
						Toast.makeText(SelectChannel.this, "删除成功！", Toast.LENGTH_SHORT).show();
						//移除数组元素
						channelList.remove(row);
						//通知改变界面
						mAdapter.notifyDataSetChanged();
						//删除失败
					}else{
						Toast.makeText(SelectChannel.this, "删除失败！", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			return convertView;
		}		
	}
	//每一行元素的界面元素组成
	final class ViewHolder {
		public LinearLayout textLayout;
		public TextView channel;
		public Button delBtn;
	}
    
	//创建menu菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//添加RSS源
		menu.add(0, MENU_ADD, 0, R.string.add_rss);
		//退出程序
		menu.add(0,MENU_QUIT,1,R.string.rss_quit);
		return super.onCreateOptionsMenu(menu);
	}		
	//为menu菜单按键绑定监听器
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//添加RSS源
		case MENU_ADD:
			Intent intent = new Intent();
			intent.setClass(SelectChannel.this, AddRss.class);
			startActivity(intent);
			return true;
			//退出程序
		case MENU_QUIT:
			SelectChannel.this.finish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
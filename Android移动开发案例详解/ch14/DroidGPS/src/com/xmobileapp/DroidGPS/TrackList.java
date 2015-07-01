package com.xmobileapp.DroidGPS;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import com.xmobileapp.utils.*;
public class TrackList extends ListActivity{
	private ListView myListView = null;
	private MyAdapter myAdapter;
	private List<trackinfo> myTrackList;
	private DroidGPSDB wisDB;
	
	private final static int CONTEXTMENU_VIEW_BY_EDITOR = 0;
	private final static int CONTEXTMENU_VIEW_BY_ANIMATION = 1;
	private final static int CONTEXTMENU_VIEW_DELETE = 2;
	private final static int CONTEXTMENU_NEW_TRACK = 3;	
	
	private final static String STRING_DATE="date";
	private final static String STRING_TIME="time";
	private final static String STRING_DISTANCE="distance";
	private final static String STRING_POINTS="points";
	private final static String STRING_AVG_SPEED="avgspeed";
	private final static String STRING_MAX_SPEED="maxspeed";
	private final static String STRING_ID = "ID";
	
	private final static int MENU_BACKTO_MAIN = 0;
	
	private static final String TIME_INTER = "TimeInter";
	
	private long timeinmillidate = 0;
	private int timeinmillitime = 0;
	private Cursor cursor = null;
	private int mIntPoints = 0;
	private int mCurrentSelectedPos = 0;
	/*创建一个OptionsMenu*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mi = menu.add(0, MENU_BACKTO_MAIN, 0, "Back to main");
		mi.setIcon(R.drawable.ic_menu_back);
		return super.onCreateOptionsMenu(menu);
	}
	/*响应OPtionsMenu*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case MENU_BACKTO_MAIN:
			backToMain();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void backToMain()
	{
		Intent intent = new Intent(this,DroidGPS.class);
		startActivity(intent);
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tracklist);
			this.setTitle("Track List");
			myTrackList = new ArrayList<trackinfo>();
		    wisDB = new DroidGPSDB(this);
		    /*读取数据库中所有的记录信息*/
		    cursor = wisDB.select();
		    cursor.moveToPosition(0);
		    if(cursor.getCount()==0)
		    {
		    	myTrackList = null;
		    }
		    else
		    {
		    	do
		    	{
		    		trackinfo ttt = new trackinfo();
		    		ttt.ID = cursor.getInt(0);
		    		ttt.mBmpIndex = cursor.getInt(1);
		    		timeinmillidate = Long.parseLong(cursor.getString(2));
		    		timeinmillitime = Integer.parseInt(cursor.getString(4));
		    		ttt.mStrDate = calDate(timeinmillidate);
		    		ttt.mStrDay = calDay(timeinmillidate);
		    		ttt.mStrTime = calTime(timeinmillitime);
		    		ttt.mStrDistance = cursor.getString(5);
		    		ttt.mStrGroup = cursor.getString(6);
		    		ttt.mStrMarks = cursor.getString(7);
		    		mIntPoints = Integer.parseInt(cursor.getString(8));
		    		ttt.mStrPoints = String.valueOf(mIntPoints);
		    		ttt.mStrAvgSpeed = cursor.getString(9);
		    		ttt.mStrMaxSpeed = cursor.getString(10);
		    		/*将记录添加到列表中*/
		    		myTrackList.add(ttt);
		    	}while(cursor.moveToNext());
		    }
		    cursor.close();
			myAdapter = new MyAdapter(this,myTrackList);
			myListView = this.getListView();
			myListView.setOnCreateContextMenuListener(this);
			/*设置自定义的适配器*/
			myListView.setAdapter(myAdapter);
		}
		catch(Exception e)
		{
			e.toString();
		}
	}
	/*格式化日期显示格式*/
	private String calDate(long recorddate)
	{
		String str = "";
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(recorddate);
		str = String.valueOf(cl.get(Calendar.YEAR))+"-"+
			  String.valueOf(cl.get(Calendar.MONTH)+1)+"-"+
			  String.valueOf(cl.get(Calendar.DAY_OF_MONTH))+" ("+
			  String.valueOf(cl.get(Calendar.HOUR_OF_DAY)+8)+":"+
			  String.valueOf(cl.get(Calendar.MINUTE))+":"+
			  String.valueOf(cl.get(Calendar.SECOND))+")";
		return str;
	}
	private String calDay(long recordday)
	{
		String str = "";
		Calendar clrecord = Calendar.getInstance();
		clrecord.setTimeInMillis(recordday);
		Calendar clcurrent = Calendar.getInstance();
		if(clcurrent.getTimeInMillis()<recordday)
		{
			str = "Future day";
		}
		else if((clcurrent.get(Calendar.YEAR)==clrecord.get(Calendar.YEAR))&&
		   (clcurrent.get(Calendar.MONTH)==clrecord.get(Calendar.MONTH))&&
		   (clcurrent.get(Calendar.DAY_OF_MONTH)==clrecord.get(Calendar.DAY_OF_MONTH)))
		{
			str = "Today";
		}
		else if((clcurrent.get(Calendar.YEAR)==clrecord.get(Calendar.YEAR))&&
				   (clcurrent.get(Calendar.MONTH)==clrecord.get(Calendar.MONTH))&&
				   (clcurrent.get(Calendar.DAY_OF_MONTH)==(clrecord.get(Calendar.DAY_OF_MONTH)+1)))
		{
			str = "Yestoday";
		}
		else if((clcurrent.get(Calendar.YEAR)==clrecord.get(Calendar.YEAR))&&
				(clcurrent.get(Calendar.WEEK_OF_YEAR)==(clrecord.get(Calendar.WEEK_OF_YEAR)+1)))
		{
			str = "Last week";
		}
		else if((clcurrent.get(Calendar.YEAR)==clrecord.get(Calendar.YEAR))&&
				(clcurrent.get(Calendar.MONTH)==(clrecord.get(Calendar.MONDAY)+1)))
		{
			str = "Last month";
		}
		else if((clcurrent.get(Calendar.YEAR)==(clrecord.get(Calendar.YEAR)+1)))
		{
			str = "Last year";
		}
		else 
		{
			str = "Passed day";
		}
		return str;
	}
	private String calTime(int time) 
	{
		String str = "";
		int iHour = time/3600;
		int iMinute = (time%3600)/60;
		int iSecond = time%60;
		str = iHour +this.getResources().getString(R.string.juhao)+iMinute+"'"+iSecond+"''";
		return str ;
	}
	/*监听ListActivity的选择事件*/
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
        try {
             info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
             mCurrentSelectedPos = info.position;
        } catch (ClassCastException e) {
        	e.toString();
        	return false;
        }
        switch(item.getItemId())
        {
        case CONTEXTMENU_VIEW_BY_EDITOR:
        	ViewByEditor(info.position);
        	break;
        case CONTEXTMENU_VIEW_BY_ANIMATION:
        	ViewByAnimation(info.position);
        	break;
        case CONTEXTMENU_VIEW_DELETE:
        	ShowDialog(1);
        	break;
        case CONTEXTMENU_NEW_TRACK:
        	NewTrack();
        	break;
        }
		return super.onContextItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		if(wisDB!=null)
		{
			/*关闭数据库*/
			wisDB.close();
		}
		super.onDestroy();
	}
	/*创建ContextMenu*/
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("View");
		menu.add(Menu.NONE,CONTEXTMENU_VIEW_BY_EDITOR,0,
				TrackList.this.getResources().getString(R.string.str_view_editor));
		menu.add(Menu.NONE,CONTEXTMENU_VIEW_BY_ANIMATION,0,
				TrackList.this.getResources().getString(R.string.str_view_animation));
		menu.add(Menu.NONE,CONTEXTMENU_VIEW_DELETE,0,
				TrackList.this.getResources().getString(R.string.str_view_delete));		
		menu.add(Menu.NONE,CONTEXTMENU_NEW_TRACK,0,
				TrackList.this.getResources().getString(R.string.str_view_new_track));
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	/*查看记录的详细资料*/
	private void ViewByEditor(int iPos)
	{
		Intent intent = new Intent(this,ViewTrackEdition.class);
		intent.putExtra(STRING_DATE,String.valueOf(timeinmillidate));
		intent.putExtra(STRING_TIME,String.valueOf(timeinmillitime));
		intent.putExtra(STRING_DISTANCE,myTrackList.get(iPos).mStrDistance);
		intent.putExtra(STRING_POINTS,myTrackList.get(iPos).mStrPoints);
		intent.putExtra(STRING_AVG_SPEED,myTrackList.get(iPos).mStrAvgSpeed);
		intent.putExtra(STRING_MAX_SPEED,myTrackList.get(iPos).mStrMaxSpeed);
		startActivity(intent);
		finish();
	}
	/*以动画方式查看行程记录*/
	private void ViewByAnimation(int iPos)
	{
		Intent intent = new Intent(this,MapViewByAnimation.class);
		intent.putExtra(STRING_DATE,String.valueOf(timeinmillidate));
		intent.putExtra(STRING_TIME,String.valueOf(timeinmillitime));
		intent.putExtra(STRING_DISTANCE,myTrackList.get(iPos).mStrDistance);
		intent.putExtra(STRING_POINTS,myTrackList.get(iPos).mStrPoints);
		intent.putExtra(STRING_ID,myTrackList.get(iPos).ID);
		startActivity(intent);
		finish();
	}
	/*删除此条记录*/
	private void DeleteListItem(int iPos)
	{
		wisDB.delete(myTrackList.get(iPos).ID);
		myTrackList.remove(iPos);
		myAdapter = new MyAdapter(this,myTrackList);
		myListView.setAdapter(myAdapter);
	}
	private void NewTrack()
	{
		ShowDialog(2);
	}
	private void ShowDialog(int iIndex)
	{
		if(iIndex==2)
		{
			new AlertDialog.Builder(this)
	    	.setIcon(R.drawable.ic_menu_info_details)
	    	.setTitle(getResources().getString(R.string.str_dialog_title))
	    	.setItems(getResources().getStringArray(R.array.category), new DialogInterface.OnClickListener()
	    	{
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(TrackList.this,mapactivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt(TIME_INTER, which);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}  		
	    	})
	    	.setNegativeButton(getResources().getString(R.string.str_cancel),new DialogInterface.OnClickListener()
	    	{
				public void onClick(DialogInterface dialog, int which) {
				}
	    	})
	    	.setCancelable(true)
	    	.create()
	    	.show()
	    	;
		}
		if(iIndex==1)
		{
			new AlertDialog.Builder(this)
	    	.setIcon(R.drawable.ic_menu_notifications)
	    	.setTitle(getResources().getString(R.string.str_dialog_warning))
	    	.setMessage(getResources().getString(R.string.str_dialog_message_ask))
	    	.setPositiveButton("OK", new DialogInterface.OnClickListener()
	    	{

				public void onClick(DialogInterface dialog, int which) {
					DeleteListItem(mCurrentSelectedPos);
					String filePath = String.valueOf(mCurrentSelectedPos+1)+".dml";
					deletefile(filePath);
				}
	    		
	    	})
	    	.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
	    	{

				public void onClick(DialogInterface dialog, int which) {
					
				}
	    		
	    	})
	    	.create()
	    	.show();
		}
	}
	/*删除指定文件*/
	private void deletefile(String filePath)
	{
		File file = new File("/data/data/com.DragonDroid.DroidGPS/files/"+filePath); 
		file.delete();
	}
}

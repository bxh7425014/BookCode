package org.crazyit.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class ExpandableListViewTest extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//创建一个BaseExpandableListAdapter对象
		ExpandableListAdapter adapter = new BaseExpandableListAdapter()
		{
			int[] logos = new int[]
			{
				R.drawable.p,
				R.drawable.z,
				R.drawable.t
			};
 			private String[] armTypes = new String[]
				{ "神族兵种", "虫族兵种", "人族兵种"};
			private String[][] arms = new String[][]
			{
				{ "狂战士", "龙骑士", "黑暗圣堂", "电兵" },
				{ "小狗", "刺蛇", "飞龙", "自爆飞机" },
				{ "机枪兵", "护士MM" , "幽灵" }
			};
			//获取指定组位置、指定子列表项处的子列表项数据
			@Override
			public Object getChild(int groupPosition, int childPosition)
			{
				return arms[groupPosition][childPosition];
			}
			@Override
			public long getChildId(int groupPosition, int childPosition)
			{
				return childPosition;
			}
			@Override
			public int getChildrenCount(int groupPosition)
			{
				return arms[groupPosition].length;
			}
			private TextView getTextView()
			{
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, 64);
				TextView textView = new TextView(ExpandableListViewTest.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				return textView;
			}
			//该方法决定每个子选项的外观
			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent)
			{
				TextView textView = getTextView();			
				textView.setText(getChild(groupPosition, childPosition).toString());
				return textView;
			}
			//获取指定组位置处的组数据
			@Override
			public Object getGroup(int groupPosition)
			{
				return armTypes[groupPosition];
			}
			@Override
			public int getGroupCount()
			{
				return armTypes.length;
			}
			@Override
			public long getGroupId(int groupPosition)
			{
				return groupPosition;
			}
			//该方法决定每个组选项的外观
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent)
			{
				LinearLayout ll = new LinearLayout(ExpandableListViewTest.this);
				ll.setOrientation(0);
				ImageView logo = new ImageView(ExpandableListViewTest.this);
				logo.setImageResource(logos[groupPosition]);
				ll.addView(logo);
				TextView textView = getTextView();
				textView.setText(getGroup(groupPosition).toString());				
				ll.addView(textView);			
				return ll;
			}
			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition)
			{
				return true;
			}
			@Override
			public boolean hasStableIds()
			{
				return true;
			}
		};
		ExpandableListView expandListView = (ExpandableListView)
			findViewById(R.id.list);
		expandListView.setAdapter(adapter);
	}
}
package com.yarin.android.Examples_04_18;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher.ViewFactory;

public class Activity01 extends Activity implements OnClickListener,ViewFactory
{
	/* 所有要显示的图片资源索引 */
	private static final Integer[] imagelist = 
	{ 
		R.drawable.img1, 
		R.drawable.img2, 
		R.drawable.img3,
		R.drawable.img4, 
		R.drawable.img5, 
		R.drawable.img6, 
		R.drawable.img7, 
		R.drawable.img8, 
    }; 
	
	//创建ImageSwitcher对象
	private ImageSwitcher			m_Switcher;
	//索引
	private static int				index			= 0;

	//“下一页”按钮ID
	private static final int		BUTTON_DWON_ID	= 0x123456;
	//“上一页”按钮ID
	private static final int		BUTTON_UP_ID	= 0x123457;
	//ImageSwitcher对象的ID
	private static final int		SWITCHER_ID		= 0x123458;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//创建一个线性布局LinearLayout
		LinearLayout main_view = new LinearLayout(this);
		//创建ImageSwitcher对象
		m_Switcher = new ImageSwitcher(this);
		//在线性布局中添加ImageSwitcher视图
		main_view.addView(m_Switcher);
		//设置ImageSwitcher对象的ID
		m_Switcher.setId(SWITCHER_ID);
		//设置ImageSwitcher对象的数据源
		m_Switcher.setFactory(this);
		m_Switcher.setImageResource(imagelist[index]);
		
		//设置显示上面创建的线性布局
		setContentView(main_view);

		//创建“下一张”按钮
		Button next = new Button(this);
		next.setId(BUTTON_DWON_ID);
		next.setText("下一张");
		next.setOnClickListener(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(100, 100);
		main_view.addView(next, param);

		//创建“上一张”按钮
		Button pre = new Button(this);
		pre.setId(BUTTON_UP_ID);
		pre.setText("上一张");
		pre.setOnClickListener(this);
		main_view.addView(pre, param);

	}

	//事件监听、处理
	public void onClick(View v)
	{
		switch (v.getId())
		{
			//下一页
			case BUTTON_DWON_ID:
				index++;
				if (index >= imagelist.length)
				{
					index = 0;
				}
				//ImageSwitcher对象资源索引
				m_Switcher.setImageResource(imagelist[index]);
				break;
			//上一页
			case BUTTON_UP_ID:
				index--;
				if (index < 0)
				{
					index = imagelist.length - 1;
				}
				//ImageSwitcher对象资源索引
				m_Switcher.setImageResource(imagelist[index]);
				break;
			default:
				break;
		}
	}

	public View makeView()
	{
		//将所有图片通过ImageView来显示
		return new ImageView(this);
	}
}
